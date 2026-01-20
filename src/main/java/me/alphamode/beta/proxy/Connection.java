package me.alphamode.beta.proxy;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;

public class Connection {
    private static int TOTAL_CONNECTIONS = 0;
    public static final int MAX_BUFFER_SIZE = 2097151;

    private boolean connected = false;
    private final Thread readThread;
    private final Thread writeThread;
    private final ByteBuffer readBuffer;
    private final ByteBuffer writeBuffer;
    private final SocketChannel socket;
    private final SocketChannel proxySocket;
    private Queue<Payload> payloads = new ArrayDeque<>();

    public Connection(SocketChannel socket) throws IOException {
        this.socket = socket;
        int threadId = TOTAL_CONNECTIONS++;
        this.readThread = Thread.ofVirtual().name("Read thread: " + threadId).unstarted(this::readLoop);
        this.writeThread = Thread.ofVirtual().name("Write thread: " + threadId).unstarted(this::writeLoop);

        this.readBuffer = ByteBuffer.allocateDirect(MAX_BUFFER_SIZE);
        this.writeBuffer = ByteBuffer.allocateDirect(MAX_BUFFER_SIZE);

        this.proxySocket = SocketChannel.open();
    }



    public void listen(SocketAddress address) throws IOException {
        assert this.proxySocket.connect(address);
        connected = true;
        this.readThread.start();
        this.writeThread.start();
    }

    public void disconnect() {
        connected = false;
    }

    public void readLoop() {
        while (connected) {
            try {
                readBuffer.clear();
                int pos = this.readBuffer.position();
                int read = this.socket.read(readBuffer);
                if (read == -1) {
                    disconnect();
                    return;
                }
                this.payloads.offer(new Payload(readBuffer.slice(pos, read)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeLoop() {
        while (connected) {
            if (!proxySocket.isConnected()) {
                continue;
            }
//            try {
                for (Payload payload : this.payloads) {
//                    Packet.read(payload.buffer());
                    try {
                        this.proxySocket.write(payload.buffer());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.payloads.clear();
//            }
        }
    }

    public record Payload(ByteBuffer buffer) {}
}
