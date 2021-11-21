package utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Transfer {

    private DatagramSocket socket;


    public Transfer(int serverPort) {
        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    public void send(Response response, InetAddress clientInetAddress, int clientPort){
        try {
            byte[] byteArray = response.toByteArray();
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, clientInetAddress, clientPort);
            socket.send(packet);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public Request receive(){
        try {
            byte[] buf = new byte[19999];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            Request request = Request.fromByteArray(buf);
            request.setPort(packet.getPort());
            return request;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


//    @Deprecated
//    public String receiveString(){
//        try {
//            byte[] buf = new byte[19999];
//            DatagramPacket packet = new DatagramPacket(buf, buf.length);
//            socket.receive(packet);
//            clientInetAddress = packet.getAddress();
//            clientPort = packet.getPort();
//            String s = new String(buf);
//            return s;
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }

//    @Deprecated
//    public void sendString(String answer){
//        try {
//            byte[] byteArray = answer.getBytes();
//            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, clientInetAddress, clientPort);
//            socket.send(packet);
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }




}
