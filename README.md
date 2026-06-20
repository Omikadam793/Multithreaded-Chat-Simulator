# Multithreaded Chat Simulator

A real-time multi-user chat platform built using Core Java, advanced multithreading concepts, WebSockets, and the Javalin web framework.

The project demonstrates enterprise-level concurrency patterns, synchronized shared resources, thread pool management, real-time message broadcasting, and browser-based communication using WebSockets.

---

## 🚀 Features

### Core Java Multithreading

- Multi-user concurrent chat system
- Thread-safe shared chat room management
- Synchronization using `synchronized` methods
- Race-condition prevention
- Concurrent client handling
- Producer-Consumer communication concepts
- Persistent chat logging

### Networking

- TCP Socket-based chat architecture
- ServerSocket and Socket communication
- Parallel client connections
- Client identity management

### WebSocket Chat Interface

- Real-time browser-based chat
- Live message broadcasting
- Dynamic username changes using:

```text
/nick YourName
```

- Multiple browser support
- Instant updates across connected clients

### Javalin Web Server

- Lightweight Java web framework
- Embedded Jetty server
- Static file hosting
- WebSocket endpoint management

---

## 🏗️ Architecture

```text
Browser Client
      │
      ▼
WebSocket (/chat)
      │
      ▼
Javalin Web Server
      │
      ▼
Concurrent Chat Registry
(ConcurrentHashMap)
      │
      ▼
Broadcast Engine
      │
      ▼
Connected Clients
```

---

## 📂 Project Structure

```text
multithreaded-chat-simulator/
│
├── pom.xml
├── README.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Server.java
│   │   │   ├── Client.java
│   │   │   ├── ClientHandler.java
│   │   │   ├── ChatRoom.java
│   │   │   ├── Message.java
│   │   │   └── WebServer.java
│   │   │
│   │   └── resources/
│   │       └── public/
│   │           └── index.html
│   │
│   └── logs/
│       └── chat.log
│
└── target/
```

---

## 🛠 Technologies Used

- Java 21
- Maven
- Javalin 6
- WebSockets
- Jetty Server
- ConcurrentHashMap
- ExecutorService
- TCP Sockets
- BufferedWriter
- HTML
- CSS
- JavaScript

---

## ⚡ How To Run

### Clone Repository

```bash
git clone <your-repository-url>
cd multithreaded-chat-simulator
```

### Compile Project

```bash
mvn clean compile
```

### Start Server

```bash
mvn exec:java
```

Expected Output:

```text
==================================
Web Chat Server Started
==================================
Frontend : http://localhost:8080
WebSocket: ws://localhost:8080/chat
==================================
```

---

## 🌐 Open Chat Application

Open:

```text
http://localhost:8080
```

in multiple browser tabs or windows.

---

## 👤 Set Username

Inside the chat box:

```text
/nick Omkar
```

Example:

```text
SERVER: Anonymous changed name to Omkar
```

---

## 💬 Example Chat

Browser 1:

```text
/nick Omkar
Hello everyone!
```

Browser 2:

```text
/ nick Rahul
Hi Omkar!
```

Output:

```text
SERVER: Anonymous changed name to Omkar
SERVER: Anonymous changed name to Rahul
Omkar: Hello everyone!
Rahul: Hi Omkar!
```

---

## 🔥 Concurrency Concepts Demonstrated

### Thread Pools

```java
Executors.newFixedThreadPool(10)
```

### Synchronization

```java
synchronized
```

### Concurrent Collections

```java
ConcurrentHashMap
```

### Inter-Thread Communication

```java
wait()
notifyAll()
```

### Producer Consumer Pattern

Used for efficient log processing and event coordination.

---

## 📈 Future Improvements

- Private Messaging
- Chat Rooms
- Message History API
- User Authentication
- PostgreSQL Persistence
- Docker Deployment
- Railway Cloud Deployment
- Redis Pub/Sub
- JWT Security
- Spring Boot Migration

---

## 🎯 Learning Outcomes

This project demonstrates:

- Core Java Networking
- Concurrent Programming
- Thread Safety
- Synchronization
- WebSocket Communication
- Real-Time Systems Design
- Maven Project Management
- Backend Architecture Design
- Browser-to-Server Messaging

---

## 📜 License

MIT License