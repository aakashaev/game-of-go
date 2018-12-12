package server.packet;


import java.util.HashMap;
import java.util.Map;

public class PacketManager {

    private static final Map<Short, Class<? extends AbstractPacket>> packets = new HashMap<>();

    static {
        packets.put((short) 1, PacketAutorize.class);
    }

    public static AbstractPacket getPacket(short id) {
        try {
            return packets.get(id).newInstance();
            // чтобы работал newInstance обязательно нужно, чтобы в пакете с id был пустой конструктор
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
