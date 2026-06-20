import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String sender;
    private final String content;
    private final LocalDateTime timestamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getFormattedMessage() {
        return sender + ": " + content;
    }

    public String getLogFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: %s", timestamp.format(formatter), sender, content);
    }
}