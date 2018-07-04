package com.wuzheng.demo.connection.kafka;

import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import io.vertx.kafka.client.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ProducerConnection
 * @Package com.wuzheng.demo.connection.kafka
 * @Author WuZheng
 * @Date 2018/7/4 14:00
 * @Description: 生产者
 */
public class ProducerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ProducerConnection.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();

    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("acks", "1");

        // use producer for interacting with Apache Kafka
        KafkaProducer<String, String> producer = KafkaProducer.create(vertx, config);
        for (int i = 0; i < 5; i++) {
            // only topic and message value are specified, round robin on destination partitions
            KafkaProducerRecord<String, String> record =
                    KafkaProducerRecord.create("layton", "message_姐姐的_" + i);
            producer.write(record, done -> {
                if (done.succeeded()) {

                    RecordMetadata recordMetadata = done.result();
                    logger.info("Message " + record.value() + " written on topic=" + recordMetadata.getTopic() +
                            ", partition=" + recordMetadata.getPartition() +
                            ", offset=" + recordMetadata.getOffset());
                }

            });
        }

    }
}
