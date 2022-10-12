package com.appynitty.swachbharatabhiyanlibrary.login;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InternetWorking {

    final private static Executor executor = Executors.newSingleThreadExecutor();

    public static boolean internetIsConnected() {

        try {

            String command = "ping -c 1 google.com";

            return (Runtime.getRuntime().exec(command).waitFor() == 0);

        } catch (Exception e) {

            return false;
        }


    }

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }

}
