package com.wuzheng.demo.connection.grpc;

import com.wuzheng.demo.proto.GreeterGrpc;
import com.wuzheng.demo.proto.HelloReply;
import com.wuzheng.demo.proto.HelloRequest;
import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @ClassName: GrpcServer
 * @Package com.wuzheng.demo.connection.grpc
 * @Author WuZheng
 * @Date 2018/7/4 19:31
 * @Description: 一个rpc服务提供方
 */
public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();

    public static void main(String[] args) throws IOException {
        GreeterGrpc.GreeterVertxImplBase service = new GreeterGrpc.GreeterVertxImplBase() {
            @Override
            public void sayHello(HelloRequest request, Future<HelloReply> future) {
                future.complete(HelloReply.newBuilder()
                        .setMessage("Hello, " + request.getName() + "!").build());
            }
        };

        VertxServer rpcServer = VertxServerBuilder
                .forAddress(vertx, "localhost", 8080)
                .addService(service)
                .build();

        // Start is asynchronous
        rpcServer.start();
    }
}
