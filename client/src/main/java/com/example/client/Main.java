package com.example.client;

import com.example.client.login.LoginWindow;
import com.example.client.utilities.RequestHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Main extends Application {
    public static final int PORT = 56789;
    private static boolean reconnectionMode = false;
    private static int attempts = 0;
    @Override
    public void start(Stage primaryStage){


            LoginWindow loginWindow = new LoginWindow();
            loginWindow.show();

    }
    public static void main(String...args) {
        if (reconnectionMode){
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            RequestHandler.getInstance().setRemoteHostSocketAddress(
                    new InetSocketAddress(InetAddress.getLocalHost(), PORT));
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(RequestHandler.getInstance().getRemoteHostSocketAddress());
            RequestHandler.getInstance().setRemoteHostSocketChannel(socketChannel);
        }catch (IOException e){
            reconnectionMode = true;
            if (attempts == 4) {
                System.exit(0);
            }
            attempts++;
            main(args);
        }

        launch(args);
    }
}
