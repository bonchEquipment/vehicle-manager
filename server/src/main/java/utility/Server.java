package utility;


import MultiThreading.CallableRequestProceed;
import MultiThreading.RunnableRequestReceive;
import MultiThreading.RunnableRequestSender;
import MultiThreading.ServiceClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;


public class Server {


    private Transfer transfer;
    private DataBaseManager dataBaseManager;
    private ManagerOfCommands managerOfCommands;
    private ExecutorService receivePoll;
    private ExecutorService processingPool;
    private ExecutorService sendingPool;
    private BlockingQueue<Request> requestQueue;
    private BlockingQueue<ServiceClass> resultQueue;


    public Server(int serverPort, int dataBasePort) {
        connectToDataBase(dataBasePort);
        try {
            ClassCollection.updateCollection(dataBaseManager.getVehicleCollection());
        } catch (SQLException e) {
            System.out.println("some SQLException just happened, but it should not " + e.toString());
        }

        this.managerOfCommands = new ManagerOfCommands(dataBaseManager, (new FactoryOfCommands(ClassCollection.getList())).getCommandList());
        transfer = new Transfer(serverPort);
        receivePoll = new ThreadPoolExecutor(1, 4, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        //receivePoll = Executors.newFixedThreadPool(4);
        processingPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        sendingPool = ForkJoinPool.commonPool();
        resultQueue = new LinkedBlockingQueue<>();
        requestQueue = new LinkedBlockingQueue<>();
    }


    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(this::receiveRequest);
        executorService.submit(this::processRequest);
        executorService.submit(this::sendResponse);
        executorService.submit(this::readFromKeyboard);
    }

    private void receiveRequest() {
        while (1 == 1) {
            receivePoll.submit(new RunnableRequestReceive(transfer, requestQueue));
            sleep(10);
        }
    }


    private void processRequest() {
        while (2 == 2) {
            sleep(10);
            if (requestQueue.isEmpty()) continue;
            Request request = requestQueue.poll();
            Future<String> future = processingPool.submit(new CallableRequestProceed(request, managerOfCommands, dataBaseManager));
            resultQueue.offer(new ServiceClass(request.getClientInetAddress(), request.getPort(), future, transfer));
        }
    }

    private void sendResponse() {
        while (3 == 3) {
            sleep(10);
            if (resultQueue.isEmpty()) continue;
            sendingPool.submit(new RunnableRequestSender(resultQueue.poll()));
        }
    }

    private void connectToDataBase(int DataBasePort) {
        while (true) {
            try {
                dataBaseManager = new DataBaseManager(DataBasePort);
                System.out.println("the server has successfully connected to the database");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("server stop working, cos data base is not available at the moment");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("some problems with driver had appear");
            }
            System.out.println("trying to connect");
            sleep(700);
            System.out.print(".");
            sleep(700);
            System.out.print(".");
            sleep(700);
            System.out.print(".");
            sleep(700);
            System.out.print(".");

        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            if (scanner.nextLine().equals("exit"))
                System.exit(0);
        }
    }
}
