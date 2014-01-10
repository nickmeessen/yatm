package nl.enterprisecoding.android.sufficient.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jaspe_000
 * Date: 08/01/14
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class TaskList {

    /**
     * Returns a task with the given task id.
     *
     * @param id The id of the requested task.
     * @return Task object with the correct id.
     */
    public Task getTask(long id) {
        return null;
    }

    /**
     * Returns a task with the given Title.
     *
     * @param title the title of the requested task.
     * @return Task object with the correct title.
     */
    public Task getTask(String title) {
        return null;
    }

    /**
     * Returns all the tasks marked important.
     *
     * @return Task list of all the tasks that are marked important.
     */
    public List<Task> getImportantTasks() {
        return null;
    }

    /**
     * returns the important tasks in a given category.
     *
     * @param categoryId all the tasks must be related to this category.
     */
    public List<Task> getImportantTasks(long categoryId) {
        return null;
    }

    /**
     * returns all the tasks marked completed.
     *
     * @return list of tasks that are completed
     */
    public List<Task> getCompletedTasks() {
        return null;
    }

    /**
     * returns all the tasks marked completed in the given category.
     *
     * @param categoryId the related category witch the completed tasks is related to.
     * @return list of tasks that are completed and related to given category.
     */
    public List<Task> getCompletedTasks(long categoryId) {
        return null;
    }

    /**
     * adds the tasks the to the list of tasks
     *
     * @param task       the task to add to the list of tasks
     * @param categoryId the related category of the task
     */
    public void addTask(Task task, long categoryId) {

    }

    /**
     * adds multiple task to the list of tasks
     *
     * @param Tasks the task that should be added
     */
    public void addTasks(List<Task> Tasks) {

    }
}
