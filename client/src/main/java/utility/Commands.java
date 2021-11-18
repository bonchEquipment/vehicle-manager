package utility;

import collection.VehicleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {

    private static ArrayList<String> arrayList = new ArrayList<>();

    public static List<String> getAllCommands(){
        return Arrays.asList("help", "print_field_descending_fuel_type",
                    "info", "show", "average_of_engine_power", "remove_by_id",
                    "remove_grater", "remove_last", "count_by_type", "remove_lower",
                    "add", "update", "execute_script");

    }

    public static List<String> getCommandsWithArguments(){
        return Arrays.asList("remove_by_id", "remove_grater",
                "count_by_type", "remove_lower", "execute_script", "update");
    }


    public static List<String> getCommandsWithNumberArgument(){
        return Arrays.asList("remove_by_id", "remove_grater", "remove_lower", "update");
    }



    public static boolean isAValidCommand(String usersString) {
        String[] words = usersString.trim().toLowerCase().split(" ");
        String validatedString = words[0];
        for (String command : Commands.getAllCommands()) {
            if (command.equals(validatedString)) {
                if (Commands.getCommandsWithArguments().contains(validatedString)) {
                    if (words.length == 2) {
                        return isCommandHaveValidArgument(words[0], words[1]);
                    } else {
                        return false;
                    }
                }
                if (words.length != 1) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    public static boolean isCommandHaveValidArgument(String command, String argument) {
        if (Commands.getCommandsWithNumberArgument().contains(command)) {
            try {
                Integer.parseInt(argument);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (command.equals("count_by_type")) {
            try {
                VehicleType.valueOf(argument.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(argument))) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
//    help - display help for the available commands
//    print_field_descending_fuel_type - print the fuel Type field values of all elements in descending order
//    clear - deleting all elements from the collection
//    help - display help for the available commands
//    info - gives print information about the collection (type, date of initialization, number of elements, etc.)
//    save - saving collection in a file
//    show - print all elements of the collection to stdout in string representation
//    average_of_engine_power - count an average engine power of all vehicle in a collection
//    remove_by_id - remove elements from the collection by id
//    exit - ends the program
//    remove_grater - remove all elements with grater id
//    remove_last - remove the last element of the collection
//    count_by_type - show the number of elements with the same vehicle type
//    remove_lower - remove all elements with lower id
//    add - creating element and adding it to the collection
//    update - updating element with the specified id
//    execute_script - reads and executes a script from the specified file




}
