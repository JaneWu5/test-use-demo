package com.wuzheng.demo.connection.redis;

import com.github.mauricio.async.db.mysql.MySQLConnection;
import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: RedisConnection
 * @Package com.wuzheng.demo.connection.redis
 * @Author WuZheng
 * @Date 2018/7/4 14:36
 * @Description: 连接Redis
 */
public class RedisConnection {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnection.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();
    private static final Properties prop = new Properties();
    private static final RedisClient redis;

    static {
        InputStream inputStream = MySQLConnection.class.getClassLoader().getResourceAsStream("config/redis.cfg.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        RedisOptions config = new RedisOptions()
                .setHost(prop.getProperty("host"))
                .setPort(Integer.parseInt(prop.getProperty("port")));

        redis = RedisClient.create(vertx, config);
    }

    public static void main(String[] args) {
        redis.set("name", "wuzheng", res -> {
            if (res.succeeded()) {
                logger.info("key-value set succeeded!");
            }
        });

        redis.get("name", res -> {
            if (res.succeeded()) {
                logger.info("name: " + res.result());
            }
        });
    }
}
