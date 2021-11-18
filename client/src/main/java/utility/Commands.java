package utility;

import annotations.NotNull;
import collection.VehicleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {


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


    public static boolean isAValidCommand(@NotNull String usersString) {
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
                return words.length == 1;
            }
        }
        return false;
    }


    public static boolean isCommandHaveValidArgument(@NotNull String command,@NotNull String argument) {
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

}