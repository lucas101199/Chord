import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

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
        BigInteger rt = message.WhereToStoreMessage();
        System.out.println(rt);

        System.out.println(node1.getFingerTable());
        System.out.println(node2.getFingerTable());
        System.out.println(node3.getFingerTable());
    }
}
