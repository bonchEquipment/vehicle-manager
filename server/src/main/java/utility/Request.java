package utility;

import collection.Vehicle;

import java.io.*;
import java.net.InetAddress;

public class Request implements Serializable {

    private Vehicle vehicle;
    private String argument;
    private String name;
    private String login;
    private String password;
    private InetAddress clientInetAddress;
    /**
     * the OS controls the setting of ports, so the port is not known
     * until the request arrives at the server
     */
    private int port;

    /**
     * for commands "update",
     */
    public Request(String name, String argument, Vehicle vehicle, InetAddress senderAddress) {
        this.vehicle = vehicle;
        this.argument = argument;
        this.name = name;
        this.clientInetAddress = senderAddress;
    }


    /**
     * for command "add"
     */
    public Request(String name, Vehicle vehicle, InetAddress senderAddress) {
        this.vehicle = vehicle;
        this.name = name;
        this.clientInetAddress = senderAddress;
    }

    public Request(String name, String argument, InetAddress senderAddress) {
        this.argument = argument;
        this.name = name;
        this.clientInetAddress = senderAddress;
    }

    public Request(String name, InetAddress senderAddress) {
        this.name = name;
        this.clientInetAddress = senderAddress;
    }

    /**
     * for commands "register", "login"
     */
    public Request(String name, String login, String password, InetAddress senderAddress) {
        this.name = name;
        this.login = login;
        this.clientInetAddress = senderAddress;
    }


    public byte[] toByteArray() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(this);
            oos.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getArgument() {
        return argument;
    }

    public String getName() {
        return name;
    }

    public InetAddress getClientInetAddress() {
        return clientInetAddress;
    }

    public void setClientInetAddress(InetAddress clientInetAddress) {
        this.clientInetAddress = clientInetAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static Request fromByteArray(byte[] byteArray){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Request) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }
}
