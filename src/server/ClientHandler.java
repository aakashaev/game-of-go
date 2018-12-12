package server;

/*
*   Класс отвечает за обработку конкретного клиента,
*   за прослушивание всего что от него приходит.
 */

import game.model.Player;
import server.packet.AbstractPacket;
import server.packet.PacketManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket client;
    private Player player;

    public ClientHandler(Socket client) {
        this.client = client;
        start();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public void invalidate() {
        ServerLoader.invalidate(client);
    }

    @Override
    public void run() {
        while (true) {
            // поток данных пришедших от клиента
            if (!readData()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
        }
    }

    private boolean readData() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            if (dis.available() <= 0) {
                return false;
            }
            short id = dis.readShort();

            AbstractPacket packet = PacketManager.getPacket(id);
            packet.setSocket(client);
            packet.read(dis); // чтение
            packet.handle(); // обработка
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
