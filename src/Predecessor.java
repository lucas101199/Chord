import java.net.InetSocketAddress;

public class Predecessor extends Thread{

    private Node local;
    private boolean alive;

    public Predecessor(Node _local) {
        local = _local;
        alive = true;
    }

    public void toDie() {
        alive = false;
    }
}
