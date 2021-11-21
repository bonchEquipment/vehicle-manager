package MultiThreading;

import utility.DataBaseManager;
import utility.MD5Hasher;
import utility.ManagerOfCommands;
import utility.Request;

import java.sql.SQLException;
import java.util.concurrent.Callable;

public class CallableRequestProceed implements Callable<String> {

    private ManagerOfCommands managerOfCommands;
    private Request request;
    private DataBaseManager dataBaseManager;

    public CallableRequestProceed(Request request, ManagerOfCommands managerOfCommands,  DataBaseManager dataBaseManager) {
        this.request = request;
        this.managerOfCommands = managerOfCommands;
        this.dataBaseManager = dataBaseManager;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
            request.setPassword(MD5Hasher.hash(request.getPassword() + "salt" + request.getLogin()));
            if (request.getName().equals("login")) {
                return loginUser(request.getLogin(), request.getPassword());
            } else if (request.getName().equals("register")) {
                return registerNewUser(request.getLogin(), request.getPassword());
            }
            return managerOfCommands.executeCommand(request);
    }


        private String loginUser(String login, String password){
            try {
                if (dataBaseManager.isUserExist(login)) {
                    if (!(dataBaseManager.isUserExist(login, password)))
                        return "wrong password, try again";
                    return "confirmed";
                } else {
                    return "there is no user with such login";
                }
            } catch (SQLException e) {
                return "unable to login user because database is enable";
            }
        }

        private String registerNewUser (String login, String password){
            try {
                if (dataBaseManager.isUserExist(login)) {
                    return "user with such username already exist";
                } else {
                    dataBaseManager.addUser(login, password);
                    return "confirmed";
                }
            } catch (SQLException e) {
                return "unable to register user because database is enable";
            }
        }


    }
