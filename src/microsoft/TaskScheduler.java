package microsoft;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler {
    PriorityQueue<ScheduledTask> queue ;
    private final Lock lock = new ReentrantLock();
    private final Condition newTaskAdded = lock.newCondition();
    private final ThreadPoolExecutor workerExecutor ;


    public TaskScheduler(int workerThreadSize){
        queue = new PriorityQueue<ScheduledTask>();
        workerExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(workerThreadSize);
    }

    void startJob(Runnable task, long delay, TimeUnit unit){
        lock.lock();
        long startTime =  System.currentTimeMillis() + unit.toMillis(delay);
        ScheduledTask scheduledTask = new ScheduledTask(startTime,task);
        queue.add(scheduledTask);

        lock.unlock();
    }


}


class ScheduledTask implements Comparable{
    Long startTime ;
    long expirationTime;
    long duration;
    Runnable task;

    public ScheduledTask(long startTime, Runnable task){
        this.startTime = startTime;
        this.task = task;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        ScheduledTask t1 = (ScheduledTask)o;
        return this.getStartTime().compareTo(t1.getStartTime());
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }
}
