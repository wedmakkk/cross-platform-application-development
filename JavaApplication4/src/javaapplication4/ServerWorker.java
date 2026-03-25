package javaapplication4;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerWorker {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ServerWorker(Socket socket) throws Exception {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendTask(ComputationRequest request) throws Exception {
        out.writeObject(request);
        out.flush();
    }

    public ComputationResponse readResponse() throws Exception {
        Object obj = in.readObject();
        return (ComputationResponse) obj;
    }

    public String getClientInfo() {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    public boolean isAlive() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void close() {
        try { in.close(); } catch (Exception ignored) {}
        try { out.close(); } catch (Exception ignored) {}
        try { socket.close(); } catch (Exception ignored) {}
    }
}