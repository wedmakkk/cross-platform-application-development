package javaapplication4;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IntegralClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;

        try (
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            System.out.println("Клиент подключен к серверу.");

            while (true) {
                Object obj = in.readObject();

                if (!(obj instanceof ComputationRequest request)) {
                    break;
                }

                RecIntegral rec = new RecIntegral(
                    request.getLower(),
                    request.getUpper(),
                    request.getStep()
                );

                double partial = rec.integrateSqrtMultiThreaded(
                    request.getLower(),
                    request.getUpper(),
                    request.getStep()
                );

                ComputationResponse response = new ComputationResponse(
                    partial,
                    socket.getInetAddress().getHostAddress()
                );

                out.writeObject(response);
                out.flush();
            }

        } catch (Exception e) {
            System.out.println("Клиент завершён: " + e.getMessage());
        }
    }
}