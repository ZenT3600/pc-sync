package main.iis.tommasosalvini.pcrestorer.logging;

import java.time.LocalDateTime;

public class Log {

    private static void print(String label, String message) {
        System.out.println("[" + LocalDateTime.now() + "] [" + label + "] " + message);
    }

    public static void info(String message) {
        Log.print("INFO", message);
    }

    public static void debug(String message) {
        Log.print("DEBUG", message);
    }

    public static void warning(String message) {
        Log.print("WARN", message);
    }

    public static void error(String message) {
        Log.print("ERROR", message);
    }

}
