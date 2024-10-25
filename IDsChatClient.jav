import java.io.*;
import java.net.*;
import java.util.Scanner;

public class IDSChatClient {
    private static final String clientName = "John Doe";
    private static final String bankAccount = "1234-5678-9012";
    private static final String schoolAddress = "123 University Ave, City";
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12346);  // Connect to MITM or Server on port 12346
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to the chat server");
        
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                    
                    if (detectPhishing(serverMessage)) {
                        alertUser(serverMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        
        while (true) {
            String userMessage = scanner.nextLine();
            out.println(userMessage);
        }
    }

    // Phishing Detection Logic
    private static boolean detectPhishing(String serverMessage) {
        if (serverMessage.contains("verify your account") || serverMessage.contains("unusual login activity")) {
            return true;
        }
        return false;
    }

    // Alert the user about potential phishing attempt
    private static void alertUser(String serverMessage) {
        System.err.println("ALERT: Phishing attempt detected!");
        System.err.println("Details: " + serverMessage);
        logSuspiciousActivity(serverMessage);
    }

    // Log suspicious activity
    private static void logSuspiciousActivity(String serverMessage) {
        try (PrintWriter log = new PrintWriter(new FileWriter("intrusion_log.txt", true))) {
            log.println("Suspicious Activity: " + serverMessage);
            log.println("Timestamp: " + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

