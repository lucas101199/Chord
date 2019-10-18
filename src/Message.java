import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message {

    String id;
    String sender;
    String recipient;
    String topic;
    long timestamp;
    String content;

    public Message(String id, String sender, String recipient, String topic, long timestamp, String content) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.topic = topic;
        this.timestamp = timestamp;
        this.content = content;
    }

    public BigInteger WhereToStoreMessage() throws NoSuchAlgorithmException {
        String gh = "16";
        BigInteger rt = new BigInteger(gh);
        BigInteger result = new BigInteger(this.id).mod(rt);
        return result;
    }
}
