import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private final List<ClientHandler> clients = new ArrayList<>();
    private final List<String> messageHistory = new ArrayList<>();
    private final int MAX_HISTORY_SIZE = 20;
    
    // PHASE 10 UPDATE: Define the log file path
    private final String LOG_FILE = "logs/chat.log";

    public synchronized void addClient(ClientHandler client) {
        sendHistoryToClient(client);
        clients.add(client);
        broadcastMessage("[SERVER]", client.getClientName() + " joined");
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastMessage("[SERVER]", client.getClientName() + " left");
    }

    public synchronized void broadcastMessage(String sender, String message) {
        String formattedMessage = sender + ": " + message;
        System.out.println("[Broadcast] " + formattedMessage);

        // Manage rolling in-memory cache
        if (messageHistory.size() >= MAX_HISTORY_SIZE) {
            messageHistory.remove(0);
        }
        messageHistory.add(formattedMessage);

        // ==========================================
        // PHASE 10 UPDATE: Log message permanently to disk
        // ==========================================
        logToFile(sender, message);

        // Send live message to all active clients
        for (ClientHandler client : clients) {
            client.sendMessage(formattedMessage);
        }
    }

    // PHASE 10 UPDATE: Synchronized helper to handle file writing safely
    private synchronized void logToFile(String sender, String message) {
        // Create a professional timestamp: YYYY-MM-DD HH:MM:SS
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        // Format the log line perfectly
        String logLine = String.format("[%s] %s: %s", timestamp, sender, message);

        // Open file in 'append' mode (true) so it doesn't overwrite old chats
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logLine);
            writer.newLine(); // Add a line break for the next message
            // writer.flush(); // Optional: Forces the buffer to dump to disk instantly
        } catch (IOException e) {
            System.err.println("Error writing to chat.log: " + e.getMessage());
        }
    }

    private synchronized void sendHistoryToClient(ClientHandler client) {
        if (!messageHistory.isEmpty()) {
            client.sendMessage("\n--- RECENT MESSAGE HISTORY ---");
            for (String historicalMsg : messageHistory) {
                client.sendMessage(historicalMsg);
            }
            client.sendMessage("-------------------------------\n");
        }
    }
}