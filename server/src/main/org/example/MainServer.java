package main.org.example;

import main.org.example.dataBase.DBConnector;
import main.org.example.dataBase.DBInitializer;
import main.org.example.dataBase.DBWorker;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class.getCanonicalName());



    public static void main(String[] args) {
        logger.info("Entering server!");

        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            int Port = 56789;
            SocketAddress address = new InetSocketAddress(Port);
            serverSocket.bind(address);
            logger.info("Server listening port "+ Port);
            DBWorker  dbWorker = connectToDB();
            LocalDateBase localDateBase = new LocalDateBase();
            Receiver receiver = new Receiver(localDateBase, dbWorker);
            ConsoleManager consoleManager = new ConsoleManager();
            Thread thread = new Thread(consoleManager);
            thread.start();
            CommandManager commandManager = new CommandManager(receiver);
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            AcceptingConnections connect = new AcceptingConnections(serverSocket, commandManager, logger, executor, forkJoinPool, cachedThreadPool);
            connect.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static DBWorker connectToDB() {
        Connection database;
        database = new DBConnector(logger).connect();
        if (database == null) return null;

        DBInitializer dbInitializer = new DBInitializer(database, logger);
        try {
            dbInitializer.initialize();
        }catch (SQLException throwables) {
            logger.info("Something wrong with db");
            return null;
        }

        try {
            return new DBWorker(database, logger);
        }catch (NoSuchAlgorithmException e){
            logger.info("Hashing algorithm not found!");
            return null;
        }
    }
}