package xaos.test;

import xaos.main.Game;
import xaos.tasks.Task;
import xaos.utils.Point3D;

/**
 * Issues player orders headlessly through the same model seam the UI uses:
 * {@link Game#createTask(int)} to start a task, {@link Task#setPoint(Point3D)}
 * to place its target(s), which commits it via {@link Game#taskCreated()}.
 * This is exactly what CommandPanel.executeCommand drives from a mouse click,
 * minus the presentation (no cursor Tile, no selection state).
 *
 * Deliberately never calls {@link Task#setTile}: constructing a Tile draws one
 * Utils.random number (Tile.java animation phase), so leaving the cursor tile
 * off keeps an issued order from perturbing the RNG stream. The task's tile is
 * transient and render-only, so behavior is unaffected.
 *
 * The number of points a task needs is decided by the task type itself (see
 * Task.setTask): INIZONE tasks take a start and an end cell (a single cell is
 * the same point twice, as CommandPanel does for mine), SINGLEPOINT tasks take
 * one, and already-CREATED tasks are added directly.
 */
final class Orders {

    private Orders() {
    }

    /**
     * Issues one order and returns the Task that was committed, or null if the
     * seam rejected it (e.g. setParameter failed and deleted the current task).
     * For area tasks pass both cells; for single-cell tasks pass end == ini (or
     * null, treated as ini).
     */
    static Task issue(int taskType, String parameter, Point3D ini, Point3D end) {
        Game.createTask(taskType);
        Task task = Game.getCurrentTask();
        if (task == null) {
            return null;
        }
        if (parameter != null) {
            task.setParameter(parameter);
            // setParameter can delete the current task on a bad parameter.
            task = Game.getCurrentTask();
            if (task == null) {
                return null;
            }
        }

        int state = task.getState();
        if (state == Task.STATE_CREATED) {
            // Point-less orders (equip, toggles, ...) commit straight away.
            Game.getWorld().getTaskManager().addTask(task);
            Game.setCurrentState(Game.STATE_NO_STATE);
            return task;
        }

        if (state == Task.STATE_CREATING_SINGLEPOINT) {
            task.setPoint(new Point3D(ini));
            return task;
        }

        // STATE_CREATING_INIZONE: start cell, then end cell (2nd commits).
        task.setPoint(new Point3D(ini));
        Task current = Game.getCurrentTask();
        if (current == null) {
            return null;
        }
        current.setPoint(new Point3D(end != null ? end : ini));
        return current;
    }

    /** Order a single solid cell mined. */
    static Task mine(int x, int y, int z) {
        return issue(Task.TASK_MINE, null, new Point3D(x, y, z), new Point3D(x, y, z));
    }
}
