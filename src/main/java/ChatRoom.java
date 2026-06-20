import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

    private final List<ClientHandler> clients = new ArrayList<>();
    private final List<String> messageHistory = new ArrayList<>();

    private static final int MAX_HISTORY_SIZE = 20;

    // Log file location
    private static final String LOG_FILE = "logs/chat.log";

    public ChatRoom() {
        createLogDirectory();
    }

    public synchronized void addClient(ClientHandler client) {
        sendHistoryToClient(client);

        clients.add(client);

        broadcastMessage(
                "[SERVER]",
                client.getClientName() + " joined"
        );
    }

    public synchronized void removeClient(ClientHandler client) {

        clients.remove(client);

        broadcastMessage(
                "[SERVER]",
                client.getClientName() + " left"
        );
    }

    public synchronized void broadcastMessage(
            String sender,
            String message
    ) {

        String formattedMessage =
                sender + ": " + message;

        System.out.println(
                "[Broadcast] " + formattedMessage
        );

        // Rolling in-memory history
        if (messageHistory.size() >= MAX_HISTORY_SIZE) {
            messageHistory.remove(0);
        }

        messageHistory.add(formattedMessage);

        // Persist to disk
        logToFile(sender, message);

        // Broadcast to all clients
        for (ClientHandler client : clients) {
            client.sendMessage(formattedMessage);
        }
    }

    private synchronized void logToFile(
            String sender,
            String message
    ) {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        String timestamp = now.format(formatter);

        String logLine =
                "[" + timestamp + "] "
                        + sender
                        + ": "
                        + message;

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        LOG_FILE,
                                        true
                                )
                        )
        ) {

            writer.write(logLine);
            writer.newLine();

        } catch (IOException e) {

            System.err.println(
                    "Error writing to chat.log: "
                            + e.getMessage()
            );
        }
    }

    private synchronized void sendHistoryToClient(
            ClientHandler client
    ) {

        if (!messageHistory.isEmpty()) {

            client.sendMessage(
                    "\n--- RECENT MESSAGE HISTORY ---"
            );

            for (String historicalMsg : messageHistory) {
                client.sendMessage(historicalMsg);
            }

            client.sendMessage(
                    "-------------------------------\n"
            );
        }
    }

    private void createLogDirectory() {

        File logDir = new File("logs");

        if (!logDir.exists()) {

            boolean created = logDir.mkdirs();

            if (created) {
                System.out.println(
                        "[INFO] Created logs directory"
                );
            }
        }
    }
}