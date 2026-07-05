package xaos.test;

import xaos.tasks.Task;
import xaos.utils.Point3D;

/**
 * One scheduled player order in a scenario script: apply it at {@link #tick},
 * then let the simulation run. A scenario is just an ordered list of these.
 *
 * Directives are plain data (task type, target cells, optional parameter) so a
 * scenario reads as a simple directive list. {@link ScenarioRunner} applies
 * each one at its tick through {@link Orders}, deterministically and in list
 * order, before that tick's World.nextTurn.
 */
final class Directive {

    final long tick;
    final int taskType;
    final String parameter;
    final Point3D ini;
    final Point3D end;
    final String label;

    private Directive(long tick, int taskType, String parameter, Point3D ini, Point3D end, String label) {
        this.tick = tick;
        this.taskType = taskType;
        this.parameter = parameter;
        this.ini = ini;
        this.end = end;
        this.label = label;
    }

    void apply() {
        Orders.issue(taskType, parameter, ini, end);
    }

    /** Order a single solid cell mined at the given tick. */
    static Directive mine(long tick, int x, int y, int z) {
        Point3D p = new Point3D(x, y, z);
        return new Directive(tick, Task.TASK_MINE, null, p, p, "mine " + x + "," + y + "," + z);
    }

    /** Order a rectangular block of cells mined (start and end corners). */
    static Directive mineArea(long tick, Point3D from, Point3D to) {
        return new Directive(tick, Task.TASK_MINE, null, from, to, "mineArea " + from + "->" + to);
    }

    /** Order the cell one level below (x,y,z) mined (dig down). */
    static Directive digDown(long tick, int x, int y, int z) {
        Point3D p = new Point3D(x, y, z);
        return new Directive(tick, Task.TASK_DIG, null, p, p, "dig " + x + "," + y + "," + z);
    }

    /** Designate a stockpile of the given kind over a rectangle of cells. */
    static Directive stockpile(long tick, String kind, Point3D from, Point3D to) {
        return new Directive(tick, Task.TASK_STOCKPILE, kind, from, to, "stockpile " + kind + " " + from + "->" + to);
    }

    /** Queue a building of the given kind with its NW corner at (x,y,z). */
    static Directive build(long tick, String buildingId, int x, int y, int z) {
        Point3D p = new Point3D(x, y, z);
        return new Directive(tick, Task.TASK_BUILD, buildingId, p, p, "build " + buildingId + " " + x + "," + y + "," + z);
    }
}
