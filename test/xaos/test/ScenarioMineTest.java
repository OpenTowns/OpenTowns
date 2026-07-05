package xaos.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import xaos.main.World;
import xaos.tiles.Cell;
import xaos.utils.Point3D;

/**
 * L1 scripted-directive scenario: a single mine order, driven headlessly and
 * deterministically. Where the passive golden pins prove worldgen and free-run
 * simulation never drift, this proves the same for <em>active play</em>: issue
 * an order, let the citizens carry it out, and pin the result.
 *
 * Two kinds of assertion, both needed. Acceptance/correctness assertions
 * ({@link #orderIsAccepted}, {@link #targetCellGetsMined}) say the behavior was
 * right the first time, not just stable, so a green golden pin cannot silently
 * freeze a no-op. The golden pins ({@link #matchesGoldenPins}) then guard that
 * exact behavior against future drift.
 *
 * The target is the ground cell just east of and below the spawn column
 * (citizens spawn stacked at 67,181,11 for seed 42 / normal); mining it digs a
 * hole a citizen reaches almost immediately. One game per JVM, like every
 * game-booting test (build.gradle forkEvery = 1).
 */
class ScenarioMineTest {

    private static final long SEED = 42;
    private static final String MAP = "normal";
    private static final long TICKS = 1000;
    private static final long CHECKPOINT = 250;

    // A solid ground cell adjacent to the spawn column, chosen from a probe run
    // (see git history) as reliably minable and reachable for this seed/map.
    private static final int TX = 68;
    private static final int TY = 181;
    private static final int TZ = 12;

    private static Path userFolder;
    private static ScenarioResult result;

    private static boolean targetSolidBefore;
    private static int taskItemsBefore;
    private static int taskItemsAfterIssue;
    private static boolean orderAccepted;
    private static boolean targetMinedAfter;

    @BeforeAll
    static void bootIssueAndRun() throws Exception {
        userFolder = HeadlessRunner.newUserFolder();
        ScenarioRunner runner = ScenarioRunner.boot(userFolder, SEED, MAP);
        World world = runner.world();

        Cell target = World.getCell(TX, TY, TZ);
        targetSolidBefore = !target.isMined() && !target.getTerrain().hasFluids();
        taskItemsBefore = pendingTaskCount();

        // The scenario script: one mine directive. apply() drives the same
        // model seam a mouse click does (Orders), minus presentation.
        Directive mineOrder = Directive.mine(0, TX, TY, TZ);
        mineOrder.apply();

        orderAccepted = World.getCell(TX, TY, TZ).isFlagOrders();
        taskItemsAfterIssue = pendingTaskCount();

        // Run the simulation out; the order is already placed, so no further
        // directives are scheduled. (Directives can also fire at any tick via
        // runner.run(List.of(Directive.mine(t, ...)), ...).)
        result = runner.run(List.of(), TICKS, CHECKPOINT);

        targetMinedAfter = World.getCell(TX, TY, TZ).isMined();
    }

    @AfterAll
    static void cleanup() {
        HeadlessRunner.deleteRecursively(userFolder);
    }

    /**
     * Count of registered tasks across both lists: a freshly issued order goes
     * into the temp list first and is merged into the main list on the next
     * turn, so summing both catches it whenever it is read.
     */
    private static int pendingTaskCount() {
        World world = xaos.main.Game.getWorld();
        return world.getTaskManager().getTaskItems().size()
                + world.getTaskManager().getTaskItemsTemp().size();
    }

    @Test
    void targetWasSolidBeforeMining() {
        assertTrue(targetSolidBefore, "probe target " + TX + "," + TY + "," + TZ + " was not a solid, minable cell");
    }

    @Test
    void orderIsAccepted() {
        // A dropped order (bad coordinate, unreachable cell) would leave the
        // cell unflagged and add no task: the check that keeps a green pin
        // from meaning "nothing happened".
        assertTrue(orderAccepted, "mine order was not accepted (target cell not flagged with orders)");
        assertTrue(taskItemsAfterIssue > taskItemsBefore,
                "mine order registered no task (before=" + taskItemsBefore + " after=" + taskItemsAfterIssue + ")");
    }

    @Test
    void targetCellGetsMined() {
        // Correctness: a citizen actually carried the order out within TICKS.
        assertTrue(targetMinedAfter, "target cell was not mined after " + TICKS + " ticks");
    }

    @Test
    void scenarioIsNotANoOp() {
        // The active run must diverge from doing nothing: its terrain differs
        // from the passive worldgen pin for the same seed and map.
        assertNotEquals(Golden.get("worldgen." + MAP + ".terrain"), result.finalTerrainHash(),
                "mining left terrain identical to untouched worldgen");
    }

    @Test
    void livingsSurvivedMining() {
        assertFalse(World.getCitizenIDs().isEmpty(), "no citizens left after mining run");
    }

    @Test
    void matchesGoldenPins() {
        // Regression: freeze this active-play behavior. A mismatch means the
        // order pipeline, pathfinding, or mining behavior changed (see Golden
        // for the deliberate update workflow).
        assertEquals(Golden.get("scenario.mine.42.normal.t250.state"), result.stateHashAt(250),
                "scenario state at tick 250 diverged from golden pin\n" + result.describe());
        assertEquals(Golden.get("scenario.mine.42.normal.terrain"), result.finalTerrainHash(),
                "scenario final terrain diverged from golden pin\n" + result.describe());
        assertEquals(Golden.get("scenario.mine.42.normal.state"), result.finalStateHash(),
                "scenario final state diverged from golden pin\n" + result.describe());
    }
}
