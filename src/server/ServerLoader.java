package server;

/*
*   Класс отвечает за работу сервера,
*   за открытие и закрытие порта и обработку подключений
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerLoader {

    private static ServerSocket server;
    private static ServerHandler serverHandler;
    static Map<Socket, ClientHandler> handlers = new HashMap<>(); // чтобы получать клиента по сокету

    public static void main(String[] args) {

        start(); // открытие порта
        handle(); // обработка клиента
        end(); // закрытие порта

    }

    private static void start() {
        try {
            server = new ServerSocket(8877);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle() {
        serverHandler = new ServerHandler(server);
        serverHandler.start(); // запущен отдельный поток
        startGame();
    }

    private static void startGame() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            if (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                System.out.println(line.length());
//                if (line.equals("end")) {
//                    end();
//                }
//            } else {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {}
//            }
//        }
    }

    public static void end() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientHandler getHandler(Socket socket) {
        return handlers.get(socket);
    }

    // метод для удаления клиента из списка, когда тот отключился
    public static void invalidate(Socket socket) {
        handlers.remove(socket);
    }

}
