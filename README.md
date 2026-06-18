# Multithreaded Chat Simulator

A high-performance, production-ready backend chat server built from scratch using Core Java and advanced concurrency paradigms. This architecture simulates a real-time, multi-user chat room environment with thread pools, synchronization locks, inter-thread communication, and persistent logging.

## 🏗️ Architectural Features & Milestones

- **Multi-User Socket Networking:** Implemented parallel user connections using native `ServerSocket` and `Socket` channels.
- **Resource Governance:** Managed system resources by switching from manual thread creation to an enterprise-grade `ExecutorService` fixed thread pool (`Executors.newFixedThreadPool(10)`).
- **Critical Sections & Synchronization:** Created a centralized state registry (`ChatRoom`) guarded by `synchronized` methods to completely eradicate data-erasing **Race Conditions** and `ConcurrentModificationExceptions`.
- **Advanced Coordination:** Mastered low-level concurrency primitives (`wait()` and `notifyAll()`) to implement a highly efficient **Producer-Consumer Pattern** for internal log reading without consuming heavy CPU processing loops.
- **Client Identity Handshaking:** Formatted professional server notifications (`[SERVER]: User joined`) using customized connection streams and user-selected handles.
- **Permanent File Persistence:** Designed an efficient, sequential file I/O framework utilizing a thread-safe `BufferedWriter` to save timestamped logs securely into `logs/chat.log`.

## 📂 Project Structure

```text
Multithreaded-Chat-Simulator/
│
├── src/
│   ├── Server.java          # Server bootstrapping engine & Thread Pool management
│   ├── Client.java          # Dual-threaded client interface (Parallel Reader/Writer)
│   ├── ClientHandler.java   # Dedicated client thread runnable lifecycle task
│   ├── ChatRoom.java        # Central synchronized routing and state manager
│   └── Message.java         # Structured data model encapsulation
│
├── logs/
│   └── chat.log             # Persistent on-disk transaction and history logs
│
└── README.md                # System documentation

##🚀 How to Run

    Open your terminal and navigate to the project directory.

    Compile all components:
    Bash

javac src/*.java

Start the Server instance:
Bash

java -cp src Server

Open separate terminal tabs and launch multiple clients to experience real-time synchronization:
Bash

java -cp src Client