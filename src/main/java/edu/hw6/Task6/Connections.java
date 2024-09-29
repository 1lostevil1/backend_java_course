package edu.hw6.Task6;

public enum Connections {

    Telnet(23),
    POP3(110),
    IMAP(143),
    NTP(123),
    SMB(445),
    AFP(548),
    HTTPProxy(8080),
    PPTP(1723),
    HTTP(80),
    FTP(21),
    SMTP(25),
    SSH(22),
    HTTPS(443),
    DNS(53),
    RDP(3389);

    private final int port;

    public int getPort() {
        return port;
    }

    Connections(int port) {
        this.port = port;
    }
}
