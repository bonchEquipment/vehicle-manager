package MultiThreading;

import utility.Request;
import utility.Response;
import utility.Transfer;

import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RunnableRequestSender implements Runnable {


    private InetAddress inetAddress;
    private int port;
    private Future<String> futureMessage;
    private Transfer transfer;

    public RunnableRequestSender(ServiceClass serviceClass) {
        this.transfer = serviceClass.getTransfer();
        this.port = serviceClass.getPort();
        this.inetAddress = serviceClass.getInetAddress();
        this.futureMessage = serviceClass.getMessage();
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String message;
        try{
            message = futureMessage.get();
        } catch(InterruptedException e) {
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return;
        }
        transfer.send(new Response(message), inetAddress, port);


    }
}
