package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerHandler extends Thread {

    private final ServerSocket server;

    public ServerHandler(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            // обработка подключающихся клиентов
            try {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                ServerLoader.handlers.put(client, handler);
            } catch (SocketException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // пауза между проверками
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
