import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

    Server(ServerSocket s){
        this.serverSocket = s;
    }

    void startServer()
    {
        try{
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
                
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8001);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}
