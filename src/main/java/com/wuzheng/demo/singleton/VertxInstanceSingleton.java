package com.wuzheng.demo.singleton;

import io.vertx.core.Vertx;

/**
 * @ClassName: VertxInstanceSingleton
 * @Package com.guazi.cabinet.demo.qianming.vertx
 * @Author WuZheng
 * @Date 2018/6/28 11:26
 * @Description: 获取vertx实例
 */
public class VertxInstanceSingleton {
    private VertxInstanceSingleton() {
    }

    private static class VertxInstanceLazyHolder {
        private static final Vertx VERTX = Vertx.vertx();
    }

    public static final Vertx getInstance() {
        return VertxInstanceLazyHolder.VERTX;
    }
}
