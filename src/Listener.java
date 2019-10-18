import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread{

    private Node local;
    private ServerSocket serverSocket;
    private boolean alive;

    public Listener (Node n) {
        local = n;
        alive = true;
        InetSocketAddress localAddress = local.getAddress();
        int port = localAddress.getPort();

        //open server/listener socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("\nCannot open listener port "+port+". Now exit.\n", e);
        }
    }

    @Override
    public void run() {
        while (alive) {
            Socket talkSocket = null;
            try {
                talkSocket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(
                        "Cannot accepting connection", e);
            }

            //new talker
            new Thread(new Client(talkSocket, local)).start();
        }
    }

    public void toDie() {
        alive = false;
    }
}

