import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DHT {

    HashMap<Integer, Node> node;
    List<Integer> port;
    Message message;

    public DHT(Message message) {
        this.node = new HashMap<>();
        this.message = message;
        this.port = new ArrayList<>();

        for (Node no : node.values()) {
            port.add(no.getId()%16);
        }
    }

    public void fillHashMap (Message message) throws NoSuchAlgorithmException {
        BigInteger rt = message.WhereToStoreMessage();
        int key = rt.intValue();

        if (port.contains(key)) {
            node.get(key).PutMessageInHashMap(message);
            }

        else {
            for (int i = key; i < 32; i++) {
                if (port.contains(i % 16)) {
                    node.get(i%16).PutMessageInHashMap(message);
                }
            }
        }
    }
}
