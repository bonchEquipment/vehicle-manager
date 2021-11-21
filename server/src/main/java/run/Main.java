package run;


import collection.FuelType;
import collection.Vehicle;
import collection.VehicleType;
import utility.*;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;


public class Main {


    public static void main(String[] args)  {
        Server server = new Server(getServerPort(), 3455);
        server.run();
    }

        private static int getServerPort () {
            System.out.println("write server port");
            while (true) {
                try {

                    return new Scanner(System.in).nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("wrong number format");
                }
            }
        }

//        private static int getDataBasePort () {
//            System.out.println("write Data Base port");
//            while (true) {
//                try {
//                    return new Scanner(System.in).nextInt(); //I'd recommend 3455
//                } catch (InputMismatchException e) {
//                    System.out.println("wrong number format");
//                }
//            }
//        }



}




