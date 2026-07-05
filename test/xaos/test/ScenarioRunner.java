package xaos.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import xaos.main.Game;
import xaos.main.World;
import xaos.utils.AStarQueue;
import xaos.utils.Utils;

/**
 * Drives a scripted-directive scenario headlessly and deterministically. It is
 * TownsHeadless's tick loop plus a directive schedule: boot a seeded world,
 * then for each tick apply any directives due at that tick (in list order,
 * before the turn) and advance the simulation one turn with synchronous
 * pathfinding, recording state hashes at checkpoints.
 *
 * Same determinism contract as the passive golden pins: single seeded RNG
 * (Utils.random), synchronous A* (AStarQueue.drainSynchronously each tick).
 * Issuing an order draws no RNG (see {@link Orders}), so a scenario's hashes
 * are a stable regression pin over active play.
 *
 * One game per JVM: like every game-booting test, a scenario class must run in
 * its own forked JVM (build.gradle sets forkEvery = 1).
 */
final class ScenarioRunner {

    private final World world;

    private ScenarioRunner(World world) {
        this.world = world;
    }

    /**
     * Boots a seeded headless game for the given map into the given sandbox
     * user folder. Same init order as TownsHeadless and the other in-JVM
     * tests: init, then synchronous A* + seed, then worldgen.
     */
    static ScenarioRunner boot(Path userFolder, long seed, String map) {
        if (!Files.exists(Path.of("towns.ini"))) {
            throw new IllegalStateException("Working directory must be src/ (run via gradlew test)");
        }
        Game.initHeadless(userFolder.toString());
        AStarQueue.setSynchronousMode(true);
        Utils.setRandomSeed(seed);
        Game.startGame("c1", map);
        return new ScenarioRunner(Game.getWorld());
    }

    /** The live world, for computing directive targets and asserting outcomes. */
    World world() {
        return world;
    }

    /**
     * Runs the scenario: for tick 0..totalTicks-1 apply the directives due at
     * that tick (before the turn), advance one turn, drain pathfinding, and
     * record a checkpoint every checkpointEvery ticks (0 to disable). Directives
     * whose tick is >= totalTicks never fire.
     */
    ScenarioResult run(List<Directive> directives, long totalTicks, long checkpointEvery) {
        // Group by tick, preserving list order within a tick.
        List<Directive> pending = new ArrayList<>(directives);
        ScenarioResult result = new ScenarioResult();

        for (long tick = 0; tick < totalTicks; tick++) {
            for (int i = 0; i < pending.size(); i++) {
                Directive d = pending.get(i);
                if (d.tick == tick) {
                    d.apply();
                }
            }

            world.nextTurn();
            AStarQueue.drainSynchronously();

            if (checkpointEvery > 0 && (tick + 1) % checkpointEvery == 0) {
                result.checkpoint(tick + 1,
                        xaos.TownsHeadless.computeTerrainHash(),
                        xaos.TownsHeadless.computeStateHash());
            }
        }

        result.finish(xaos.TownsHeadless.computeTerrainHash(), xaos.TownsHeadless.computeStateHash());
        return result;
    }
}
