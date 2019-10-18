import java.io.IOException;
import java.net.InetSocketAddress;

public class main3 {

    public static void main(String[] args) throws IOException {

        InetSocketAddress s3 = new InetSocketAddress("127.0.0.1", 8812);

        Node node3 = new Node(s3.getPort(),s3);

        node3.fillFingerTable(s3.getPort());
    }
}
