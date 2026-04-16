package javaapplication4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class IntegralClient {

    private static final int REGISTER_PORT = 6000;

    public static void main(String[] args) {
        String serverHost = "127.0.0.1";

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            // 1. REGISTER на порт 6000
            String registerMessage = "REGISTER";
            byte[] regData = registerMessage.getBytes(StandardCharsets.UTF_8);

            DatagramPacket regPacket = new DatagramPacket(
                    regData,
                    regData.length,
                    serverAddress,
                    REGISTER_PORT
            );

            socket.send(regPacket);
            System.out.println("REGISTER отправлен на сервер.");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);

                System.out.println("Ожидание TASK...");
                socket.receive(requestPacket);

                String message = new String(
                        requestPacket.getData(),
                        0,
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Получено сообщение: " + message);

                if (message.startsWith("TASK;")) {
                    try {
                        String[] parts = message.split(";");

                        double lower = Double.parseDouble(parts[1]);
                        double upper = Double.parseDouble(parts[2]);
                        double step = Double.parseDouble(parts[3]);

                        System.out.println("Начало вычисления: lower=" + lower +
                                ", upper=" + upper + ", step=" + step);

                        // Можно через пустой конструктор, чтобы не упереться в валидацию подинтервала
                        RecIntegral rec = new RecIntegral();
                        double partial = rec.integrateSqrtMultiThreaded(lower, upper, step);

                        String resultMessage = "RESULT;" + partial;
                        byte[] resultData = resultMessage.getBytes(StandardCharsets.UTF_8);

                        // RESULT отправляется туда, откуда пришёл TASK, то есть на порт 6001
                        DatagramPacket resultPacket = new DatagramPacket(
                                resultData,
                                resultData.length,
                                requestPacket.getAddress(),
                                requestPacket.getPort()
                        );

                        socket.send(resultPacket);
                        System.out.println("RESULT отправлен: " + partial);

                    } catch (Exception ex) {
                        System.out.println("Ошибка на клиенте при обработке TASK:");
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Неизвестное сообщение: " + message);
                }
            }

        } catch (Exception e) {
            System.out.println("Критическая ошибка клиента:");
            e.printStackTrace();
        }
    }
}