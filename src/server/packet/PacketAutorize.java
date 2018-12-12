package server.packet;

import server.ServerLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAutorize extends AbstractPacket {

    private String playerName;

    public PacketAutorize() {
    }

    public PacketAutorize(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public short getID() {
        return 1;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(playerName);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        playerName = dis.readUTF();
        // во избежание ошибок, важно контролировать
        // соответствие количества и типа читаемых и записываемых данных
        // т.е. если записываем две переменных, то и читать должны две переменных
    }

    @Override
    public void handle() {
//        ServerLoader.getHandler(getSocket()).setPlayerName(playerName);
//        System.out.println("Player1 is " + playerName);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {}
//        ServerLoader.end();
    }
}
