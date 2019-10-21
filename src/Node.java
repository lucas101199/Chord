import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

public class Node {

    private int id;
    private InetSocketAddress address;
    private HashMap<Integer, InetSocketAddress> fingerTable;
    private HashMap<String, Message> messageHashMap;
    Listener l;

    public Node(int id, InetSocketAddress address) throws IOException {
        this.id = id%16;
        this.address = address;
        l = new Listener(this);
        l.start();

        fingerTable = new HashMap<>();
        messageHashMap = new HashMap<>();
    }

    public void PutMessageInHashMap(Message message) {
        this.messageHashMap.put(message.id, message);
    }

    public int getId() {
        return id;
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public InetSocketAddress find_successor(int id_node) throws IOException {
        InetSocketAddress succ = this.getSuccessor();

        for (int i = 1; i < 16; i++) {
            int j = (id_node + i)%16;
            if (sendRequest(new InetSocketAddress("localhost", 9008+j))) {
                return new InetSocketAddress("localhost", 9008+j);
            }
        }
        return succ;
    }

    public InetSocketAddress find_predecessor(int id) throws IOException {
        InetSocketAddress pred = this.getSuccessor();

        for (int i = 15; i > 0; i--) {
            int j = (id + i)%16;
            if (sendRequest(new InetSocketAddress("localhost", 9008+j))) {
                return new InetSocketAddress("localhost", 9008+j);
            }
        }
        return pred;
    }

    public InetSocketAddress getSuccessor() {
        return fingerTable.get(1);
    }

    public boolean sendRequest(InetSocketAddress server) throws IOException {

        if (server == null)
            return false;

        //Socket server1 = new Socket("0.0.0.0",server.getPort());

        Socket talkSocket = null;

        // try to open talkSocket, output request to this socket
        // return null if fail to do so
        try {
            talkSocket = new Socket(server.getAddress(),server.getPort());
            PrintStream output = new PrintStream(talkSocket.getOutputStream());

        } catch (IOException e) {
            //System.out.println("\nCannot send request to "+server.toString()+"\nRequest is: "+req+"\n");
            return false;
        }

        // sleep for a short time, waiting for response
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // get input stream, try to read something from it
        InputStream input = null;
        try {
            input = talkSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Cannot get input stream from "+server.toString()+"\nRequest is: "+"\n");
        }

        // try to close socket
        try {
            talkSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Cannot close socket", e);
        }
        return true;
    }

    public void fillFingerTable(int id) throws IOException {
        for (int i = 0; i <= 3; i++) {
            int j = (id%16 + (int) Math.pow(2,i));
            if (sendRequest(new InetSocketAddress("localhost", 9008 + j))) {
                fingerTable.put(9008 +j, new InetSocketAddress("localhost", 9008 +j));
            }
            else {
                InetSocketAddress add = find_successor(9008 + j);
                fingerTable.put(9008 + j, add);
            }
        }
    }

    public HashMap<Integer, InetSocketAddress> getFingerTable() {
        return fingerTable;
    }

    public HashMap<String, Message> getMessage() {
        return messageHashMap;
    }
}