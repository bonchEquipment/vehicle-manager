package MultiThreading;

import utility.Response;
import utility.Transfer;

import java.net.InetAddress;
import java.util.concurrent.Future;

public class ServiceClass {

    private InetAddress inetAddress;
    private int port;
    private Future<String> futureMessage;
    private Transfer transfer;

    public ServiceClass(InetAddress inetAddress, int port, Future<String> futureMessage, Transfer transfer) {
        this.inetAddress = inetAddress;
        this.port = port;
        this.futureMessage = futureMessage;
        this.transfer = transfer;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Future<String> getMessage() {
        return futureMessage;
    }


    public void setMessage(Future<String> message) {
        this.futureMessage = message;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
}
