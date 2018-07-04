package com.wuzheng.demo.connection.mysql;

import com.github.mauricio.async.db.mysql.MySQLConnection;
import com.wuzheng.demo.singleton.VertxInstanceSingleton;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: MySqlConnection
 * @Package com.wuzheng.demo.connection.mysql
 * @Author WuZheng
 * @Date 2018/7/4 11:10
 * @Description: mySqL连接
 */
public class MySqlConnection {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnection.class);
    private static final Vertx vertx = VertxInstanceSingleton.getInstance();
    private static final Properties config = new Properties();
    private static final SQLClient client;

    static {
        InputStream inputStream = MySQLConnection.class.getClassLoader().getResourceAsStream("config/mysql.cfg.properties");
        try {
            config.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        JsonObject mySQLClientConfig = new JsonObject()
                .put("host", config.getProperty("host"))
                .put("port", Integer.parseInt(config.getProperty("port")))
                .put("maxPoolSize", Integer.parseInt(config.getProperty("maxPoolSize")))
                .put("username", config.getProperty("username"))
                .put("password", config.getProperty("password"))
                .put("database", config.getProperty("database"))
                .put("charset", config.getProperty("charset"))
                .put("queryTimeout", Integer.parseInt(config.getProperty("queryTimeout")));
        client = MySQLClient.createShared(vertx, mySQLClientConfig);
    }

    public static void main(String[] args) {
        client.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query("select * from test", result -> {
                    ResultSet rs = result.result();
                    logger.info("results: " + rs.getResults());
                    connection.close();
                });
            } else {
                // Failed to get connection - deal with it
            }
        });
    }

}
