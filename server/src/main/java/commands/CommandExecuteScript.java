package commands;

import utility.CollectionEditor;
import utility.Console;
import utility.ConsoleOutputSystem;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * reads and executes a script from the specified file
 */
public class CommandExecuteScript implements CommandWithArgument {
    private CollectionEditor collectionEditor;
    private String userEnteredPath;
    private ConsoleOutputSystem outputSystem;
    private Collection<String> paths;


    public CommandExecuteScript(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
        this.outputSystem = new ConsoleOutputSystem();
        this.paths = new ArrayList<>();
    }

    /**
     * using the same method that running whole program (method run() of Console)
     * but instead of reading from user input reads from a file
     * <p>
     * without creating an anon class method would looke like
     * <p>
     * if (!isFileExits(userEnteredPath)) { return "Invalid file path specified"; }
     * Console console = new Console();
     * console.run();
     * return "";
     * <p>
     * but to defend myself from a recursion I need to add 4 lines of code
     * to the method run(), so I'm overriding the hole method and creating anon class
     */
    @Override
    public String execute() {
        if (!isFileExits(userEnteredPath)) {
            return "Invalid file path specified";
        }
        final String[] res = {""};

        try {
            Console console = new Console(new Scanner(Paths.get(userEnteredPath))) {
                @Override
                public void run() {
                    while (scanner.hasNext()) {
                        userCommand = scanner.nextLine();
                        if (!isACommand(userCommand)) {
                            res[0] += "no such command \"" + userCommand +
                                    "\" type \"help\" to see commands available\n";
                            continue;
                        }
                        if (isLineAScriptWithRecursion(userCommand)) {
                            res[0] += ("the script was not executed due to recursion\n");
                            continue;
                        }
                        if (isACommand(userCommand)) {
                            res[0] += executeCommand(userCommand) + "\n";
                        }
                    }

                }
            };
            console.run();

            return res[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setArgument(String argument) {
        userEnteredPath = argument;
    }

    private boolean isFileExits(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            reader.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * this is got to wrong algorithm, but it works perfectly (I have no idea why)
     *
     * @param path
     * @return
     */
    private boolean isThePathHaveRecursion(String path) {
        ArrayList<String> linesCollection = getLinesCollectionFromFile(path);
        if (isStringCollectionHaveSameElements(paths)) {
            return true;
        }

        for (String line : linesCollection) {//хотим проанализировать все строчки в файле
            if (isLineIsACommandExecuteScript(line)) {
                String[] words = line.split(" ");
                paths.add(words[1]);
                if (isThePathHaveRecursion(words[1])) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isLineAScriptWithRecursion(String line) {
        String[] words = line.split(" ");
        if (words.length == 2) {
            boolean condition1 = words[0].equals("execute_script");
            boolean condition2 = isThePathHaveRecursion(words[1]);
            return condition1 & condition2;
        }
        return false;
    }

    private boolean isStringCollectionHaveSameElements(Collection<String> collection) {
        String[] mass = new String[1];
        String[] array = collection.toArray(mass);
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].equals(array[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<String> getLinesCollectionFromFile(String path) {
        ArrayList<String> linesCollection = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                linesCollection.add(line);
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            //this will never happen because I'm using this method only after checking is path valid
        }
        return linesCollection;
    }


    private boolean isLineIsACommandExecuteScript(String line) {
        String[] words = line.split(" ");
        boolean b = words.length == 2 &&
                words[0].toLowerCase().equals("execute_script") &&  //если строчка - execute_script PATH
                isFileExits(words[1]);

        return b;
    }

    @Override
    public String getDescription() {
        return "reads and executes a script from the specified file";
    }

    @Override
    public String getSyntacticsExample() {
        return getName() + " ScriptFile.txt";
    }

}