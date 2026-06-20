# Multithreaded Chat Simulator

A real-time multi-user chat application built using Core Java, Javalin, WebSockets, and concurrent programming principles. The project demonstrates thread-safe communication, client management, real-time message broadcasting, and browser-based chat functionality through a lightweight Java backend.

---

## Features

### Real-Time Communication

* Browser-based WebSocket chat
* Instant message broadcasting
* Multiple concurrent users
* Dynamic username updates
* Join and leave notifications

### Concurrency & Thread Safety

* Concurrent client management using `ConcurrentHashMap`
* Thread-safe message broadcasting
* Safe client registration and removal
* Multi-user communication without race conditions

### Web Server

* Built with Javalin 6
* Embedded Jetty server
* Static file hosting
* WebSocket endpoint management

### User Commands

Change username:

```text
/nick YourName
```

Example:

```text
/nick Omkar
```

---

## Technology Stack

### Backend

* Java 17
* Maven
* Javalin 6
* Jetty Server
* WebSockets

### Frontend

* HTML5
* CSS3
* JavaScript

### Concurrency

* ConcurrentHashMap
* Thread-safe broadcasting
* Multi-client session management

---

## Architecture

```text
+----------------------+
| Browser Client       |
+----------+-----------+
           |
           | WebSocket
           v
+----------------------+
| Javalin Web Server   |
+----------+-----------+
           |
           v
+----------------------+
| ConcurrentHashMap    |
| Connected Clients    |
+----------+-----------+
           |
           v
+----------------------+
| Broadcast Engine     |
+----------+-----------+
           |
           v
+----------------------+
| Active Chat Users    |
+----------------------+
```

---

## Project Structure

```text
multithreaded-chat-simulator/
│
├── pom.xml
├── README.md
├── railway.json
├── nixpacks.toml
│
└── src/
    └── main/
        ├── java/
        │   ├── ChatRoom.java
        │   ├── Client.java
        │   ├── ClientHandler.java
        │   ├── Message.java
        │   ├── Server.java
        │   └── WebServer.java
        │
        └── resources/
            ├── public/
            │   └── index.html
            │
            └── logs/
                └── chat.log
```

---

## Getting Started

### Clone Repository

```bash
git clone https://github.com/your-username/multithreaded-chat-simulator.git
cd multithreaded-chat-simulator
```

### Build Project

```bash
mvn clean package
```

### Run Application

```bash
java -jar target/multithreaded-chat-simulator-1.0-SNAPSHOT.jar
```

Expected output:

```text
==================================
Web Chat Server Started
==================================
Frontend : http://localhost:8080
WebSocket: ws://localhost:8080/chat
==================================
```

---

## Using The Application

Open:

```text
http://localhost:8080
```

in multiple browser windows.

### User 1

```text
/nick Omkar
Hello everyone!
```

### User 2

```text
/nick Rahul
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

## WebSocket Endpoint

```text
ws://localhost:8080/chat
```

Production:

```text
wss://your-domain/chat
```

---

## Concurrency Concepts Demonstrated

### Concurrent Collections

```java
ConcurrentHashMap<WsContext, String>
```

### Thread Safety

* Safe client registration
* Safe client removal
* Concurrent message broadcasting

### Multi-User Communication

* Multiple active browser sessions
* Shared chat state
* Real-time updates

---

## Future Improvements

* Private Messaging
* Chat Rooms
* Authentication & Authorization
* PostgreSQL Integration
* Message Persistence
* Docker Support
* Redis Pub/Sub
* JWT Security
* Spring Boot Migration

---

## Learning Outcomes

This project demonstrates:

* Java Backend Development
* Real-Time Communication
* WebSocket Programming
* Concurrent Programming
* Thread Safety
* Maven Build Management
* Javalin Framework
* Client-Server Architecture
* Browser-Based Applications

---

## License

MIT License
