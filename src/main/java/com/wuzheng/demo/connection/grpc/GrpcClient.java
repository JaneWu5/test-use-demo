package com.wuzheng.demo.connection.grpc;

import com.wuzheng.demo.proto.GreeterGrpc;
import com.wuzheng.demo.proto.HelloRequest;
import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: GrpcClient
 * @Package com.wuzheng.demo.connection.grpc
 * @Author WuZheng
 * @Date 2018/7/4 19:57
 * @Description: client
 */
public class GrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();

    public static void main(String[] args) {
        ManagedChannel channel = VertxChannelBuilder
                .forAddress(vertx, "localhost", 8080)
                .usePlaintext(true)
                .build();

        // Get a stub to use for interacting with the remote service
        GreeterGrpc.GreeterVertxStub stub = GreeterGrpc.newVertxStub(channel);
        HelloRequest request = HelloRequest.newBuilder().setName("JenniferWzz").build();
        // Call the remote service
        stub.sayHello(request, ar -> {
            if (ar.succeeded()) {
                logger.info("Got the server response: " + ar.result().getMessage());
            } else {
                logger.info("Could not reach server " + ar.cause().getMessage());
            }
        });
    }
}
