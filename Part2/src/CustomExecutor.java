import java.util.Comparator;
import java.util.concurrent.*;

public class CustomExecutor {
    private PriorityBlockingQueue pdg;
    private ExecutorService executor;
    private int procesors; // Number of processors that available for JVM

    /**
     * Constructor of the class
     */
    public CustomExecutor() {
        procesors = Runtime.getRuntime().availableProcessors();
        pdg = new PriorityBlockingQueue<>(procesors, Comparator.comparingInt(o -> ((Task<?>) o).getTaskType().getPriorityValue()));
        executor = new ThreadPoolExecutor(procesors/2, procesors-1,300,TimeUnit.MILLISECONDS, pdg);
    }

    /**
     * Perform the task and put it in the queue
     * @param task
     * @return the task itself
     * @param <T>
     */
    public <T> Task<T> submit(Task<T> task) {
        final Future future = executor.submit(task);
        task.setFuture(future);
        return task;
    }

    /**
     * Perform the task and put it in the queue
     * @param task
     * @param taskType
     * @return the task itself
     * @param <T>
     */
    public <T> Task<T> submit(Callable<T> task, TaskType taskType) {
        final Task<T> t = new Task<>(task, taskType);
        final Future future = executor.submit(t);
        t.setFuture(future);
        return t;
    }

    /**
     * Checks the highest priority task in the queue
     * @return the type of the highest priority task
     */
    public <T> String getCurrentMax() {
       Task<T> task = (Task) pdg.peek();
       if(task != null) {
           String p = task.getTaskType().toString();
           return p;
       }
       return "The queue is empty";
    }

    /**
     * Shutdown the ThreadPool
     */
    public void gracefullyTerminate() {
        executor.shutdown();
        if(!executor.isShutdown()){
            executor.shutdownNow();
        }
    }


}
