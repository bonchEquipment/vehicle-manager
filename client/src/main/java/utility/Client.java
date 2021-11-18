package utility;

import collection.Vehicle;

import java.net.*;
import java.util.Scanner;

public class Client {
    private final AbstractOutputSystem outputSystem;
    private final Scanner scanner;
    private final Transfer transfer;
    private String login;
    private String password;
    private InetAddress inetAddress;


    public Client(int serverPort, InetAddress inetAddress) throws SocketException, UnknownHostException {
        outputSystem = new ConsoleOutputSystem();
        scanner = new Scanner(System.in);
        transfer = new Transfer(serverPort);
        this.inetAddress = inetAddress;
    }

    public void run() {
        authorise();
        while (scanner.hasNext()) {
            String userCommand = scanner.nextLine();
            if (userCommand.equals("exit")) System.exit(0);
            handleUserCommand(userCommand);
        }
    }

    private void authorise() {
        while (login == null || password == null) {
            outputSystem.showMessage("Type \"login\" or \"create new account\"");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("login")) {
                login();
            } else if (input.equals("create new account")) {
                register();
            }
        }
    }

    private void register() {
        Request request = new Request("register", inetAddress);
        outputSystem.showMessage("enter unique username:");
        request.setLogin(scanner.nextLine());
        outputSystem.showMessage("enter your password:");
        request.setPassword(scanner.nextLine());
        String answer = sendUntilGetAnswer(request.toByteArray());
        outputSystem.showMessage(answer);
        if (answer.equals("confirmed")) {
            this.login = request.getLogin();
            this.password = request.getPassword();
        }
    }

    private void login() {
        Request request = new Request("login", inetAddress);
        outputSystem.showMessage("enter your username:");
        request.setLogin(scanner.nextLine());
        outputSystem.showMessage("enter your password:");
        request.setPassword(scanner.nextLine());
        String answer = sendUntilGetAnswer(request.toByteArray());
        outputSystem.showMessage(answer);
        if (answer.equals("confirmed")) {
            this.login = request.getLogin();
            this.password = request.getPassword();
        }
    }

    private void handleUserCommand(String userCommand) {
        if (!Commands.isAValidCommand(userCommand)) {
            outputSystem.showMessage("no such command \"" + userCommand +
                    "\" type \"help\" to see commands available");
            return;
        }
        Request request = createRequest(userCommand);
        request.setLogin(login);
        request.setPassword(password);
        outputSystem.showMessage(sendUntilGetAnswer(request.toByteArray()));
    }


    private String sendUntilGetAnswer(byte[] bytes) {
        while (true) {
            try {
                transfer.send(bytes);
                String answer = transfer.receive();
                return answer;
            } catch (SocketTimeoutException e) {
                outputSystem.showMessage("server is temporarily unavailable, sending request again...");
                sendUntilGetAnswer(bytes);
            }
        }
    }


    public Request createRequest(String stringCommand) {
        String[] words = stringCommand.trim().toLowerCase().split(" ");
        if (words[0].equals("add")) {
            Vehicle vehicle = new Vehicle(0, System.out, new Scanner(System.in));
            return new Request("add", vehicle, inetAddress);
        } else if (words[0].equals("update")) {
            Vehicle vehicle = new Vehicle(0, System.out, new Scanner(System.in));
            return new Request("update", words[1], vehicle, inetAddress);
        } else if (Commands.getCommandsWithArguments().contains(words[0])) {
            return new Request(words[0], words[1], inetAddress);
        }
        return new Request(words[0], inetAddress);

    }
}



