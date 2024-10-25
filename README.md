
# MITM and Phishing Simulation

A Java-based simulation of a **Man-in-the-Middle (MITM)** attack that demonstrates phishing techniques and client-side intrusion detection. This project is created for educational purposes to raise awareness about network security, phishing risks, and the importance of detecting suspicious activity.

## Table of Contents

- [About](#about)
- [Features](#features)
- [How It Works](#how-it-works)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [File Structure](#file-structure)
- [License](#license)

## About

This project simulates:
- A phishing attempt sent from a **MITM** server to a client, asking for sensitive information.
- An **intrusion detection** mechanism within the client that warns about potential phishing attempts.
- A **deciphering** mechanism on the MITM side that "reveals" obfuscated information captured from the client.

**Note**: This simulation is strictly for educational purposes and does not capture real sensitive data.

## Features

- **Phishing Simulation**: The MITM sends a phishing message to the client with a fake verification link.
- **Intrusion Detection**: The client detects and alerts the user about suspicious messages and logs potential phishing attempts.
- **Obfuscation and Deciphering**: Captured data is initially obfuscated, with an option to decipher and reveal the original details.
- **Ransomware Simulation**: The MITM "locks" the client’s account and demands a simulated payment to unlock it.

## How It Works

1. **MITM Simulation**: The `MITMRansomware` server acts as a man-in-the-middle, intercepting communication between the client and server. It sends a phishing message and captures details if the client interacts with it.
2. **Phishing Detection**: The `IDSChatClient` includes a detection system that identifies phishing patterns and warns the user.
3. **Simulated Payment and Unlock**: The MITM demands a payment to unlock the client’s account. Upon "payment," the client regains access, but a message indicates that the funds are already compromised.

## Setup Instructions

### Prerequisites

- **Java JDK**: Make sure you have Java JDK 8+ installed on your system.
- **Git**: For cloning and pushing to the repository (optional but recommended).


### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/MITM-Simulation.git
   cd MITM-Simulation

Compile the Java files:
javac ChatServer.java IDSChatClient.java MITMRansomware.java

Running the Simulation


Start the Chat Server:
java ChatServer

Start the MITM (Ransomware Simulation):
java MITMRansomware

Run the IDS Client:
java IDSChatClient


Usage
Client-Side:
The client will receive a phishing message from the MITM asking for verification.
Type "click link" to proceed to the fake verification form and enter placeholder data (e.g., name, bank account).
If the client ignores warnings and proceeds, the MITM captures and "deciphers" this information.

Simulated Payment:
After the phishing interaction, the MITM "locks" the client’s account, demanding a $5000 simulated payment.
The client can type "pay 5000" to regain access but is notified that the account is already compromised.
File Structure

ChatServer.java: Central server that relays messages between clients.
IDSChatClient.java: Client with phishing detection and logging functionality.
MITMRansomware.java: Man-in-the-Middle simulation with phishing, data capture, deciphering, and simulated ransom demand.

License
This project is licensed under the MIT License - see the LICENSE file for details.
   
