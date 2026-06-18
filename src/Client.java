import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner consoleInput = new Scanner(System.in);

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
            // NEW: BACKGROUND READER THREAD
            // This constantly listens to the server completely independent of your keyboard
            // ==========================================
            Thread readerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = input.readLine()) != null) {
                        System.out.println("\n" + serverMessage);
                        System.out.print("Type message: "); // Reprint the prompt cleanly
                    }
                } catch (Exception e) {
                    System.out.println("Connection to server closed.");
                }
            });
            readerThread.start(); // Start listening in the background!

            // ==========================================
            // MAIN THREAD: THE WRITER
            // This thread only cares about what you type
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}