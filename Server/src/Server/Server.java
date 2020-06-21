package Server;

import Model.Request.ClientClosed;
import Model.Request.GeneralRequest;
import Model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//import Server.Server.GeneralRequest.GeneralRequest;

public class Server implements Runnable {
    public static final int requestPort = 8888;
    public static final String serverIP = "localhost";
    private static ServerSocket requestServerSocket;

    public static void main(String[] args) {
        Server.start();
    }

    public static void start() {
        try {
            loadFiles();
            requestServerSocket = new ServerSocket(requestPort);
            Thread serverThread = new Thread(new Server(), "Server.Server Thread");
            serverThread.start();
            handleConsole();
        } catch (IOException e) {
            // ignore it
        }
    }

    private static void handleConsole() {
        Scanner scanner = new Scanner(System.in);
        String input = "hi";
        while(!input.equals("exit"))
            input = scanner.next();
        saveFiles();
        java.lang.System.exit(0);
    }

    private static void loadFiles() {
        ServerHandler.users = new ArrayList<>();
        File databasePath = new File("Database");
        if (!databasePath.exists())
            databasePath.mkdirs();
        try {
            File usersFile = new File("Database/users");
            if (!usersFile.exists())
                usersFile.createNewFile();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile));
            Object obj = null;
            while ((obj = ois.readObject()) != null) {
                ServerHandler.users.add((User) obj);
                System.out.println(((User) obj).getUsername() + "'s inbox is " + ((User) obj).getInbox());
            }
            ois.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        checkUsers();
    }

    static void saveFiles() {
//        new Thread(() -> {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("Database/users")));
//            oos.writeObject(users);
                for (User u : ServerHandler.users) {
                    oos.writeObject(u);
                    System.out.println("saved " + u.getUsername() + " with inbox " + u.getInbox());
                }
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }).start();

    }

    static void checkUsers() {
        for (User u : ServerHandler.users)
            System.out.println(u);
    }

    @Override
    public void run() {
        while (!requestServerSocket.isClosed()) {
            try {
                new Thread(new ServerRunner(requestServerSocket.accept()), "Server.Server Runner").start();
                System.out.println("new client connected");
            } catch (IOException e) {
                // ignore it
            }
        }
    }
}

class ServerRunner implements Runnable {
    private Socket serverSocket;
    private ServerHandler serverHandler;
    public ServerRunner(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        GeneralRequest clientRequest = null;
        try {
            serverHandler = new ServerHandler(serverSocket,
                    new ObjectInputStream(serverSocket.getInputStream()),
                    new ObjectOutputStream(serverSocket.getOutputStream()));
            Object obj = null;
            while (!(obj instanceof ClientClosed)) {
//                clientRequest = (GeneralRequest) serverHandler.getInputStream().readObject();
                obj = serverHandler.getInputStream().readObject();
//                System.out.println("trying to handle " + obj);
                serverHandler.handle((GeneralRequest)obj);
            }
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        } finally {
            userDisconnect();
        }
    }

    private void userDisconnect() {
        try {
            serverHandler.getOutputStream().close();
            serverHandler.getInputStream().close();
        } catch (IOException e) {
            // ignore it
        }
    }
}

