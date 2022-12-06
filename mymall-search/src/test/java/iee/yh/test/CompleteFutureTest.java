package iee.yh.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author yanghan
 * @date 2022/11/26
 */
public class CompleteFutureTest {
    public static ExecutorService executor = new ThreadPoolExecutor(8,
            8,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100),
            new ThreadFactoryBuilder().build(),
            new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            int i = 10 / 2;
            System.out.println(Thread.currentThread().getName() + " end");
            return i;
        }, executor);
        future = future.thenApplyAsync(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                integer++;
                return integer;
            }
        }, executor);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
