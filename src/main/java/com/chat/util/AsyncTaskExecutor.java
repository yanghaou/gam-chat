package com.chat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * task executor
 * Created by caixinning on 2017/12/16.
 */
public class AsyncTaskExecutor {

    private static final int queueCapacity = 10000;
    private static final int corePoolSize = 8;
    private static final int maxPoolSize = 20;
    private static final int keepAliveSeconds = 60;
    private static Logger log = LoggerFactory.getLogger(AsyncTaskExecutor.class);

    private static volatile ThreadPoolTaskExecutor taskPool = null;

    static {
        taskPool = createExecutor();
        log.info("异步服务线程池配置成功...");
    }

    /**
     * 执行异步任务
     */
    public static void execute(Runnable task) {
        try {
            taskPool.execute(task);
            logQueueSize();
        } catch (Exception e) {
            log.error("异步任务启动失败！", e);
        }
    }

    public static <V> Future<V> submit(Callable<V> callable) {
        Future<V> future = taskPool.submit(callable);
        logQueueSize();
        return future;
    }


    private static void logQueueSize() {
        int queueSize = taskPool.getThreadPoolExecutor().getQueue().size();
        if (queueSize > 0 && queueSize % 100 == 0) {
            log.info("AsyncTaskExecutor 队列长度: {}，队列已使用: {}%", queueSize, (100f * queueSize) / queueCapacity);
        }
    }


    private static ThreadPoolTaskExecutor createExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setQueueCapacity(queueCapacity);
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setKeepAliveSeconds(keepAliveSeconds);
        pool.initialize();
        return pool;
    }
    
}
