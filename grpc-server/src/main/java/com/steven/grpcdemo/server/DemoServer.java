package com.steven.grpcdemo.server;


import com.steven.grpcdemo.server.impl.DemoServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Created by steven on 2017/1/17.
 */
public class DemoServer {
    //private static final Logger logger = Logger.getLogger(DemoServer.class.getName());

    private int port = 50051;
    private Server server;

    private void start() throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(new DemoServiceImpl())
                .build()
                .start();


        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("*** shutting down gRPC server since JVM is shutting down");
                DemoServer.this.stop();
                System.out.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        final DemoServer server = new DemoServer();
        server.start();
        server.blockUntilShutdown();
    }


}
