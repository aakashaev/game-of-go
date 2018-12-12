package server;


import server.packet.AbstractPacket;
import server.packet.PacketAutorize;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientLoader {

    private static Socket socket;

    public static void main(String[] args) {
        // соединение с сервером по порту 8877
        // если он не запущен, то выбросится исключение
        connect();
        handle();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        end();
    }

    private static void sendPacket(AbstractPacket packet) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeShort(packet.getID()); // записываем в выходной поток данных ID пакета
            packet.write(dos);
            dos.flush(); // смываем данные из выходного потока
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect() {
        try {
            socket = new Socket("localhost", 8877);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle() {
//        sendPacket(new PacketAutorize("Best Player"));
    }

    private static void end() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
