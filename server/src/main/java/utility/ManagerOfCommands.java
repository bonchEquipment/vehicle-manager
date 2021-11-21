package utility;

import collection.Vehicle;
import commands.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * организует взаимодействие команд и базы данных
 */
public class ManagerOfCommands {

    protected ArrayList<Command> commandsList;
    private DataBaseManager dataBaseManager;

    public ManagerOfCommands(DataBaseManager dataBaseManager, ArrayList<Command> commandsList) {
        this.dataBaseManager = dataBaseManager;
        this.commandsList = commandsList;
    }

    private Command stringToCommand(String futureCommand) throws RuntimeException {
        String[] words = futureCommand.trim().toLowerCase().split(" ");
        futureCommand = words[0];
        for (Command command : commandsList) {
            if (command.getName().equals(futureCommand)) {
                return command;
            }
        }
        throw new RuntimeException("unexpected command " + futureCommand + " had been reviewed");
    }


    public String executeCommand(Request request) {
        try {
            try{
                if (!isUserAllowedToExecuteCommand(request)){
                    return "you can't execute this command because you are not the owner of the element";
                }
            } catch (SQLException e){
                return "unable to execute command, because data base is broken, fix it...";
            }
            if (request.getName().equals("add")) {
                return executeCommandAdd(request);
            } else if (request.getName().equals("update")) {
                return executeCommandUpdate(request);
            } else if (request.getName().equals("remove_by_id")) {
                return executeCommandRemoveById(request);
            } else if (request.getName().equals("remove_last")) {
                return executeCommandRemoveLast();
            } else if (request.getName().equals("remove_lower")) {
                return executeCommandRemoveLower(request);
            } else if (request.getName().equals("remove_grater")) {
                return executeCommandRemoveGrater(request);
            } else if (request.getName().equals("show")) {
                return executeCommandShow(request);
            }

            Command commandForExecution = stringToCommand(request.getName());

            if (commandForExecution instanceof CommandWithArgument) {
                ((CommandWithArgument) commandForExecution).setArgument(request.getArgument());
            }
            return (commandForExecution.execute());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "unable to execute command, because some exception has occurred " + e.toString();

        }
    }


    public String executeCommandAdd(Request request) {
        CommandAdd commandAdd = (CommandAdd) stringToCommand("add");
        String login = request.getLogin();
        String password = request.getPassword();
        request.getVehicle().setOwnerLogin(login);
        request.getVehicle().setOwnerPassword(password);
        try {
            dataBaseManager.addVehicle(request.getVehicle());
        } catch (SQLException e) {
            return "unable to add element, because data base is broken, fix it...";
        }
        return commandAdd.execute(request.getVehicle());
    }

    public String executeCommandShow(Request request) {
        CommandShow commandShow = (CommandShow) stringToCommand("show");
        return commandShow.execute(request.getLogin());
    }

    public String executeCommandUpdate(Request request) {
        CommandUpdate commandUpdate = (CommandUpdate) stringToCommand("update");
        String login = request.getLogin();
        String password = request.getPassword();
        request.getVehicle().setOwnerLogin(login);
        request.getVehicle().setOwnerPassword(password);
        commandUpdate.setArgument(request.getArgument());
        try {
            if (dataBaseManager.isVehicleExist(Integer.parseInt(request.getArgument()))) {
                dataBaseManager.updateElement(Integer.parseInt(request.getArgument()), request.getVehicle());
                commandUpdate.execute(request.getVehicle());
                return "the values of the element was successfully update";
            } else {
                return "unable to edit element with id \"" + request.getArgument() + "\" because it's not exist";
            }
        } catch (SQLException e) {
            return "unable to update element, because data base is broken, fix it...";
        }
    }

    public String executeCommandRemoveById(Request request) {
        CommandRemoveById commandRemoveById = (CommandRemoveById) stringToCommand("remove_by_id");
        commandRemoveById.setArgument(request.getArgument());
        try {
            if (dataBaseManager.removeVehicle(Integer.parseInt(request.getArgument()))) {
                commandRemoveById.execute();
                return "element was successfully removed";
            } else {
                return "unable to delete element with id \"" + request.getArgument() +  "\" because it's not exist";
            }
        } catch (SQLException e) {
            return "unable to delete an element, because data base is broken, fix it...";
        }
    }


    public String executeCommandRemoveLast() {
        CommandRemoveLast commandRemoveLast = (CommandRemoveLast) stringToCommand("remove_last");
        try {
            if (dataBaseManager.removeVehicle(dataBaseManager.getMaxIdOfTheCollection())) {
                commandRemoveLast.execute();
                return "element was successfully removed";
            } else {
                return "unable to delete last element";
            }
        } catch (SQLException e) {
            return "unable to delete an element, because data base is broken, fix it...";
        }
    }


    public String executeCommandRemoveLower(Request request) {
        CommandRemoveLower commandRemoveLower = (CommandRemoveLower) stringToCommand("remove_lower");
        commandRemoveLower.setArgument(request.getArgument());
        try {
            for (Integer id: getIdOfVehiclesOwnedBy(request.getLogin())){
                if (id < Integer.parseInt(request.getArgument())){
                    dataBaseManager.removeVehicle(id);
                }
            }
            commandRemoveLower.execute(request.getLogin());
            return "all your elements with id lower than " + request.getArgument() + " was successfully removed";
        } catch (SQLException e) {
            return "unable to delete elements, because data base is broken, fix it...";
        }
    }


    public String executeCommandRemoveGrater(Request request) {
        CommandRemoveGrater commandRemoveGrater = (CommandRemoveGrater) stringToCommand("remove_grater");
        commandRemoveGrater.setArgument(request.getArgument());
        try {
            for (Integer id: getIdOfVehiclesOwnedBy(request.getLogin())){
                if (id > Integer.parseInt(request.getArgument())){
                    dataBaseManager.removeVehicle(id);
                }
            }
            commandRemoveGrater.execute(request.getLogin());
            return "all your elements with id greater than " + request.getArgument() + " was successfully removed";
        } catch (SQLException e) {
            return "unable to delete elements, because data base is broken, fix it...";
        }
    }


    private ArrayList<Integer> getIdOfVehiclesOwnedBy(String ownerLogin) throws SQLException {
        ArrayList<Integer> bill = new ArrayList<>();
        for (Vehicle element: dataBaseManager.getVehicleCollection()){
            if (element.getOwnerLogin().equals(ownerLogin)){
                bill.add(element.getId());
            }
        }
        return bill;
    }

    private boolean isUserAllowedToExecuteCommand(Request request) throws SQLException {
        if (request.getName().equals("update")){
            if (!dataBaseManager.isVehicleOwnedBy(Integer.parseInt(request.getArgument()), request.getLogin())){
                return false;
            }
        } else if (request.getName().equals("remove_by_id")){
            if (    !dataBaseManager.isVehicleOwnedBy(Integer.parseInt(request.getArgument()), request.getLogin() )){
                return false;
            }
        } else if(request.getName().equals("remove_last")){
            if (!dataBaseManager.isVehicleOwnedBy(dataBaseManager.getMaxIdOfTheCollection(), request.getLogin())){
                return false;
            }
        }
        return true;
    }
}