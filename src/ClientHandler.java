import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ChatRoom chatRoom;
    private PrintWriter output;
    private String clientName;

    public ClientHandler(Socket socket, ChatRoom chatRoom) {
        this.clientSocket = socket;
        this.chatRoom = chatRoom;
    }

    public void sendMessage(String message) {
        if (output != null) {
            output.println(message);
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Handle username handshake
            output.println("SUBMIT_USERNAME_REQUEST"); 
            this.clientName = input.readLine();       
            
            if (this.clientName == null || this.clientName.trim().isEmpty()) {
                this.clientName = "User-" + clientSocket.getPort();
            }

            // Register with central room (This automatically triggers history playback!)
            chatRoom.addClient(this);

            // Main loop handling live user typing
            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                if (clientMessage.equalsIgnoreCase("exit")) {
                    output.println("Goodbye!");
                    break;
                }
                chatRoom.broadcastMessage(clientName, clientMessage);
            }

        } catch (Exception e) {
            System.out.println("Error with " + clientName + ": " + e.getMessage());
        } finally {
            chatRoom.removeClient(this);
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}