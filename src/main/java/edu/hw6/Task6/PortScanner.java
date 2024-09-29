package edu.hw6.Task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"UncommentedMain", "MagicNumber"})
public class PortScanner {

    private final static Logger LOGGER = LogManager.getLogger();

    private static final String TCP_PROTOCOL = "TCP";

    private static final String UDP_PROTOCOL = "UDP";

    private static final Map<Integer, Connections> MAP_OF_CONNECTION = new HashMap<>();

    private static final String OUTPUT_FORMAT = "%-10s%-10s%-10s";

    private static final int MAX_TASK_PORT = 49151;

    private PortScanner() {
    }

    public static void main(String[] args) {
        someConnections();
        scannPort();
    }

    public static void someConnections() {
        MAP_OF_CONNECTION.put(Connections.Telnet.getPort(), Connections.Telnet);
        MAP_OF_CONNECTION.put(Connections.POP3.getPort(), Connections.POP3);
        MAP_OF_CONNECTION.put(Connections.IMAP.getPort(), Connections.IMAP);
        MAP_OF_CONNECTION.put(Connections.NTP.getPort(), Connections.NTP);
        MAP_OF_CONNECTION.put(Connections.SMB.getPort(), Connections.SMB);
        MAP_OF_CONNECTION.put(Connections.AFP.getPort(), Connections.AFP);
        MAP_OF_CONNECTION.put(Connections.HTTPProxy.getPort(), Connections.HTTPProxy);
        MAP_OF_CONNECTION.put(Connections.PPTP.getPort(), Connections.PPTP);
        MAP_OF_CONNECTION.put(Connections.HTTP.getPort(), Connections.HTTP);
        MAP_OF_CONNECTION.put(Connections.FTP.getPort(), Connections.FTP);
        MAP_OF_CONNECTION.put(Connections.SMTP.getPort(), Connections.SMTP);
        MAP_OF_CONNECTION.put(Connections.SSH.getPort(), Connections.SSH);
        MAP_OF_CONNECTION.put(Connections.HTTPS.getPort(), Connections.HTTPS);
        MAP_OF_CONNECTION.put(Connections.DNS.getPort(), Connections.DNS);
        MAP_OF_CONNECTION.put(Connections.RDP.getPort(), Connections.RDP);
    }

    public static void scannPort() {
        LOGGER.info(OUTPUT_FORMAT.formatted("Протокол", "Порт", "Сервис"));
        for (int port = 0; port <= MAX_TASK_PORT; ++port) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
            } catch (IOException ignore) {
                LOGGER.info(OUTPUT_FORMAT
                    .formatted(TCP_PROTOCOL, port, MAP_OF_CONNECTION.get(port).toString()));
            }

            try {
                DatagramSocket datagramSocket = new DatagramSocket(port);
                datagramSocket.close();
            } catch (SocketException ignore) {
                LOGGER.info(OUTPUT_FORMAT
                    .formatted(UDP_PROTOCOL, port, MAP_OF_CONNECTION.get(port).toString()));
            }
        }
    }
}
