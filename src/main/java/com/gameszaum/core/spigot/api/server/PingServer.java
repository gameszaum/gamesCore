package com.gameszaum.core.spigot.api.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingServer {

    private String host;
    private int port;
    private Socket socket = new Socket();
    private String[] data = new String[999];

    public PingServer(String host, int port) {
        this.host = host;
        this.port = port;

        update();
    }

    public String getMotd() {
        return data[0];
    }

    public int getOnline() {
        return Integer.parseInt(data[1]);
    }

    public int getMax() {
        return Integer.parseInt(data[2]);
    }

    public void update() {
        try {
            socket.close();
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }
            data = str.toString().split("§");
            data[0] = data[0].substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}