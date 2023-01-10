import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>, Callable<T> {

    private TaskType type;

    private final Callable<T> task;
    private Future<T> future; // Holds the task result

    public Future<T> getFuture() {
        return future;
    }

    /**
     * Gives the result of the task
     *
     * @param num
     * @param tu
     * @return result
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public T get(int num, TimeUnit tu) throws ExecutionException, InterruptedException, TimeoutException {
        return future.get(num, tu);
    }

    /**
     * Gives the result of the task
     *
     * @return result
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public T get() throws ExecutionException, InterruptedException {
        return future.get();
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    /**
     * Constructor of the class
     *
     * @param type
     * @param task
     */
    public Task(Callable<T> task, TaskType type) {
        this.type = type;
        this.task = task;
    }

    /**
     * Constructor of the class
     *
     * @param task
     */
    public Task(Callable<T> task) {
        this.task = task;
        this.type = TaskType.COMPUTATIONAL;
    }


    /**
     * Create a new task object
     *
     * @param task
     * @param type
     * @param <T>
     * @return task object
     */
    public static <T> Task<T> createTask(Callable<T> task, TaskType type) {
        return new Task<T>(task, type);
    }

    public TaskType getTaskType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Callable<T> getTask() {
        return task;
    }


    /**
     * Do the task
     *
     * @return the result of the task
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        return this.task.call();
    }

    /**
     * Compare between the task types
     *
     * @param t
     * @return which has the highest priority
     */
    @Override
    public int compareTo(Task t) {
        return t.type.compareTo(this.type);
    }
}
