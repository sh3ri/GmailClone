package Model;

import Model.Request.GeneralRequest;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Connection(String serverIP) throws IOException {
        client = new Socket(serverIP, 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
    }

    public void request(GeneralRequest request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


    public Object getResponse() {
        Object response = null;
        try {
            response = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return response;
    }
}

