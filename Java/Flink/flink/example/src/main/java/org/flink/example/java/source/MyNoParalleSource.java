package org.flink.example.java.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * 单线程，从1开始递增数据
 * <p>
 * SourceFunction 和 SourceContext 都需要指定数据类型，如果不指定，代码运行的时候会报错
 *
 * @author korov
 * @date 2020/7/26
 */
public class MyNoParalleSource implements SourceFunction<Long> {
    private static final long serialVersionUID = 5801129105463683192L;

    private long count = 1L;
    private boolean isRunning = true;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (isRunning) {
            ctx.collect(count);
            count++;
            // 每秒产生一条
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
