package com.wuzheng.demo.connection.kafka;

import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: ConsumerConnection
 * @Package com.wuzheng.demo.connection.kafka
 * @Author WuZheng
 * @Date 2018/7/4 10:12
 * @Description: 连接卡夫卡的类
 */
public class ConsumerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerConnection.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();
    private static final Properties config = new Properties();
    private static final KafkaConsumer<String, String> consumer;

    static {
        InputStream inputStream = ConsumerConnection.class.getClassLoader().getResourceAsStream("config/kafka.cfg.properties");
        try {
            config.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        consumer = KafkaConsumer.create(vertx, config);
        consumer.handler(record -> {
            logger.info("Processing key=" + record.key() + ",value=" + record.value() +
                    ",partition=" + record.partition() + ",offset=" + record.offset());
        });
    }


    public static void main(String[] args) {
        consumer.subscribe("layton", ar -> {
            if (ar.succeeded()) {
                logger.info("subscribed!");
            } else {
                logger.error("Could not subscribe " + ar.cause().getMessage());
            }
        });
    }
}
