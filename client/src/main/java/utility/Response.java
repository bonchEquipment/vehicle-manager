package utility;

import java.io.*;

public class Response implements Serializable {

    private String message;

    public Response(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public static Response fromByteArray(byte[] byteArray){
        try{
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Response) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public byte[] toByteArray(){
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

}