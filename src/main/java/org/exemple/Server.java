package org.exemple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        boolean isFirst = true;
        String city = null;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер стартовал");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    if (isFirst) {
                        out.println("???");
                        isFirst = false;
                        city = in.readLine();
                        out.println("Ok");
                    } else {
                        out.println("Продолжи игру. Текущий город " + city);
                        String nextCity = in.readLine();
                        assert city != null;
                        if (nextCity.startsWith(String.valueOf(city.toUpperCase().charAt(city.length() - 1)))) {
                            out.println("Ok");
                            city = nextCity;
                        } else {
                            out.println("Not ok");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
