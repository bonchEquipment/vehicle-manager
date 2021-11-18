package utility;

import java.io.*;
import java.net.*;
import java.rmi.RemoteException;

public class Transfer {

    private DatagramSocket socket;
    private InetAddress address;
    private int serverPort;

    public Transfer(int serverPort) throws SocketException, UnknownHostException {
        this.serverPort = serverPort;
        socket = new DatagramSocket();
        socket.setSoTimeout(1999);
        address = InetAddress.getByName("localhost");
    }


    public void send(byte[] byteArray) {
        try {
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, address, serverPort);
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * return message from server or information about unavailability of the sever
     *
     * @return message from server or information about unavailability of the sever
     */
    public String receive() throws  SocketTimeoutException {
        return Response.fromByteArray(receiveByteArray()).getMessage();
    }

    public byte[] receiveByteArray() throws SocketTimeoutException {
        try {
            byte[] byteBuffer = new byte[19999];
            DatagramPacket packet = new DatagramPacket(byteBuffer, byteBuffer.length);
            socket.receive(packet);
            return byteBuffer;
        }catch (SocketTimeoutException e){
            throw new SocketTimeoutException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    private DatagramPacket createPacket(String userCommand) {
//        Request request = createRequest(userCommand);
//        byte[] byteArray = request.toByteArray();
//        return new DatagramPacket(byteArray, byteArray.length, getLocalhost(), 4445);
//    }

//    // TODO: Make a factory like DatagramPacketFactory. Maybe another one?
//    private DatagramPacket createPacket(String userCommand) {
//        Request request = createRequest(userCommand);
//        byte[] byteArray = request.toByteArray();
//        return new DatagramPacket(byteArray, byteArray.length, getLocalhost(), 4445);
//    }

}