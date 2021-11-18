package run;

import utility.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {




    public static void main(String[] args) throws IOException {
        Client client = new Client(getPort(), InetAddress.getByName("localhost"));
        client.run();

    }



    private static int getPort() {
        System.out.println("write server port");
        while (true) {
            try {
                return new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("wrong number format");
            }
        }
    }


}
