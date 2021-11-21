package MultiThreading;

import utility.Request;
import utility.Transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RunnableRequestReceive implements Runnable {


    private Transfer transfer;
    private Queue<Request> requestQueue;

    public RunnableRequestReceive(Transfer transfer, Queue<Request> requestQueue) {
        this.requestQueue = requestQueue;
        this.transfer = transfer;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public void run()  {
        requestQueue.offer(transfer.receive());

    }
}
