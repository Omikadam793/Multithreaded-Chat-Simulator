import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebServer {
    // Keep track of connected browsers and their usernames
    private static final Map<WsContext, String> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // Corrected line 12: Fixed environment port retrieval syntax
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        Javalin app = Javalin.create(config -> {
            // Enable static file serving so it can serve our HTML frontend
            config.staticFiles.add("/public");
        }).start(port);

        // Handle WebSocket Connections
        app.ws("/chat", ws -> {
            ws.onConnect(ctx -> {
                clients.put(ctx, "Anonymous");
                ctx.send("SERVER: Welcome to the web chat! Please type /nick <name> to set your username.");
            });

            ws.onClose(ctx -> {
                String username = clients.remove(ctx);
                broadcast(username + " has left the chat.");
            });

            ws.onMessage(ctx -> {
                String message = ctx.message();

                if (message.startsWith("/nick ")) {
                    String oldName = clients.get(ctx);
                    String newName = message.substring(6).trim();
                    clients.put(ctx, newName);
                    broadcast(oldName + " changed their name to " + newName);
                } else {
                    String sender = clients.get(ctx);
                    broadcast(sender + ": " + message);
                }
            });
        });

        System.out.println("Web Chat Server running on port " + port);
    }

    private static void broadcast(String message) {
        clients.keySet().forEach(client -> {
            if (client.session.isOpen()) {
                client.send(message);
            }
        });
    }
}