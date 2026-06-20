import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.javalin.Javalin;
import io.javalin.websocket.WsContext;

public class WebServer {

    // Connected clients
    private static final Map<WsContext, String> clients =
            new ConcurrentHashMap<>();

    public static void main(String[] args) {

        String portEnv = System.getenv("PORT");

        int port = (portEnv != null)
                ? Integer.parseInt(portEnv)
                : 8080;

        try {

            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/public");
            }).start(port);

            // Railway health check
            app.get("/health", ctx -> {
                ctx.result("OK");
            });

            // Redirect root to frontend
            app.get("/", ctx -> {
                ctx.redirect("/index.html");
            });

            // WebSocket endpoint
            app.ws("/chat", ws -> {

                ws.onConnect(ctx -> {

                    clients.put(ctx, "Anonymous");

                    ctx.send(
                        "SERVER: Welcome! Use /nick <name> to change your username."
                    );

                    System.out.println(
                        "[CONNECTED] Total Clients: " + clients.size()
                    );
                });

                ws.onMessage(ctx -> {

                    String message = ctx.message();

                    if (message.startsWith("/nick ")) {

                        String oldName = clients.get(ctx);

                        String newName = message.substring(6).trim();

                        if (!newName.isEmpty()) {

                            clients.put(ctx, newName);

                            broadcast(
                                "SERVER: " +
                                oldName +
                                " changed name to " +
                                newName
                            );
                        }

                    } else {

                        String sender = clients.get(ctx);

                        broadcast(sender + ": " + message);
                    }
                });

                ws.onClose(ctx -> {

                    String username = clients.remove(ctx);

                    if (username != null) {

                        broadcast(
                            "SERVER: " +
                            username +
                            " left the chat."
                        );
                    }

                    System.out.println(
                        "[DISCONNECTED] Total Clients: " +
                        clients.size()
                    );
                });

                ws.onError(ctx -> {

                    clients.remove(ctx);

                    System.out.println(
                        "[ERROR] Client connection error"
                    );
                });
            });

            System.out.println();
            System.out.println("==================================");
            System.out.println("Web Chat Server Started");
            System.out.println("==================================");
            System.out.println(
                "Frontend : http://localhost:" + port
            );
            System.out.println(
                "Health   : http://localhost:" + port + "/health"
            );
            System.out.println(
                "WebSocket: ws://localhost:" +
                port +
                "/chat"
            );
            System.out.println("==================================");
            System.out.println();

        } catch (Exception e) {

            System.out.println();
            System.out.println("SERVER FAILED TO START");
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    private static void broadcast(String message) {

        clients.keySet().removeIf(client -> {

            try {

                client.send(message);

                return false;

            } catch (Exception e) {

                System.out.println(
                    "[WARNING] Removing disconnected client"
                );

                return true;
            }
        });
    }
}