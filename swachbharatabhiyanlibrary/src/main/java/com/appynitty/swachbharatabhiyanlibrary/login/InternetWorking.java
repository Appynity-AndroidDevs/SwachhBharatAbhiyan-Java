package com.appynitty.swachbharatabhiyanlibrary.login;

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

}
