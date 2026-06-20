import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        // Corrected the environment variable extraction logic for Railway compatibility
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;
        
        ExecutorService pool = Executors.newFixedThreadPool(10);
        
        // Create the ONE central chat room instance                 
        ChatRoom chatRoom = new ChatRoom();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Chat Server with Broadcasting active on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connection accepted from: " + socket.getRemoteSocketAddress());
                
                // Pass the socket AND the shared chatRoom to the handler
                ClientHandler handler = new ClientHandler(socket, chatRoom);
                
                pool.execute(handler); 
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}