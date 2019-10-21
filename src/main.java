import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        InetSocketAddress s1 = new InetSocketAddress("localhost", 9008);
        InetSocketAddress s2 = new InetSocketAddress("localhost", 9009);
        InetSocketAddress s3 = new InetSocketAddress("localhost", 9014);

        Node node1 = new Node(s1.getPort(), s1);
        Node node2 = new Node(s2.getPort(), s2);
        Node node3 = new Node(s3.getPort(), s3);

        node1.fillFingerTable(s1.getPort());
        node2.fillFingerTable(s2.getPort());
        node3.fillFingerTable(s3.getPort());

        String id = "12457874578543691245451369536125";
        String sender = "lucas";
        String recipient = "lll";
        String topic = "chord";
        long timestamp = 123456789;
        String content = "algorithm";

        Message message = new Message(id, sender, recipient, topic, timestamp, content);
        DHT dht = new DHT(message);

        dht.node.put(node1.getId()%16, node1);
        dht.node.put(node2.getId()%16, node2);
        dht.node.put(node3.getId()%16, node3);

        BigInteger rt = message.WhereToStoreMessage();
        System.out.println(rt);

        dht.fillHashMap(message);

        System.out.println(message);

        System.out.println(node1.getFingerTable());
        System.out.println(node2.getFingerTable());
        System.out.println(node3.getFingerTable());

        System.out.println(node1.getMessage());
        System.out.println(node2.getMessage());
        System.out.println(node3.getMessage());
    }
}
