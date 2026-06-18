import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        ExecutorService pool = Executors.newFixedThreadPool(10);
        
        // Create the ONE central chat room instance
        ChatRoom chatRoom = new ChatRoom();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Chat Server with Broadcasting active on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                
                // Pass the socket AND the shared chatRoom to the handler
                ClientHandler handler = new ClientHandler(socket, chatRoom);
                
                pool.execute(handler); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}