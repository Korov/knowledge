package com.korov.gradle.knowledge.accumulation.thread.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

abstract class FutureRenderer {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfos) {
                    result.add(imageInfo.downloadImage());
                }
                return result;
            }
        };
        Future<List<ImageData>> future = executorService.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
