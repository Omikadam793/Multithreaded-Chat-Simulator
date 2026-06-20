import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Updated to connect directly across the internet to your cloud server instance
            String serverDomain = "peaceful-fascination-production.up.railway.app"; 
            int serverPort = 5355;
            
            System.out.println("Connecting to chat server at " + serverDomain + ":" + serverPort + "...");
            try (Socket socket = new Socket(serverDomain, serverPort);
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 Scanner consoleInput = new Scanner(System.in)) {
                System.out.println("Connected successfully!");

                // 1. Handle the initial username handshake
                String serverSignal = input.readLine();
                if ("SUBMIT_USERNAME_REQUEST".equals(serverSignal)) {
                    System.out.print("Enter your Username for the chat: ");
                    String username = consoleInput.nextLine();
                    output.println(username); 
                }

            // Print the welcome message from the server
            System.out.println(input.readLine());

            // ==========================================
            // BACKGROUND READER THREAD
            // ==========================================
            Thread readerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = input.readLine()) != null) {
                        System.out.println("\n" + serverMessage);
                        System.out.print("Type message: "); // Reprint the prompt cleanly
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server closed.");
                }
            });
            readerThread.start(); // Start listening in the background!

            // ==========================================
            // MAIN THREAD: THE WRITER
            // ==========================================
            String userMessage;
            while (true) {
                System.out.print("Type message: ");
                userMessage = consoleInput.nextLine();
                
                output.println(userMessage);

                if (userMessage.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
            consoleInput.close();
            System.out.println("Disconnected.");
        }
        } catch (IOException e) {
            System.out.println("Could not connect to the remote server. Check if the domain/port is active.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}