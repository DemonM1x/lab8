package main.org.example;

import main.org.example.utility.Request;
import main.org.example.utility.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class AcceptingConnections {

    private final ServerSocketChannel serverSocket;
    private final Selector selector;
    private final ExecutorService cachedThreadPool;
    private final ExecutorService executor;
    private final ForkJoinPool forkJoinPool;
    private final CommandManager commandManager;

    private final Set<SelectionKey> workingKeys =
            Collections.synchronizedSet(new HashSet<>());


    private final Logger logger;


    public AcceptingConnections(ServerSocketChannel aServerSocket, CommandManager commandManager, Logger logger, ExecutorService executor, ForkJoinPool forkJoinPool, ExecutorService cachedThreadPool) throws IOException {
        serverSocket = aServerSocket;
        this.logger = logger;
        selector = Selector.open();

        this.commandManager = commandManager;
        serverSocket.configureBlocking(false);
        this.cachedThreadPool = cachedThreadPool;
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        this.executor = executor;
        this.forkJoinPool = forkJoinPool;
    }

    public void run() throws IOException {


            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isValid() && !workingKeys.contains(key)) {
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocket.accept();
                            logger.info("Сервер соединился с" + socketChannel.getRemoteAddress());
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            workingKeys.add(key);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                           Supplier<Request> requestReader = new RequestReader(socketChannel, logger);
                            Function<Request, Response> requestHandler = new RequestHandler(commandManager, socketChannel, logger);
                            Consumer<Response> responseSender = new Deliver(key, workingKeys, logger);
                            CompletableFuture
                                    .supplyAsync(requestReader, cachedThreadPool)
                                    .thenApplyAsync(requestHandler, executor)
                                    .thenAcceptAsync(responseSender, forkJoinPool);
                        }
                    }
                        iter.remove();

                }
            }
        }
}
