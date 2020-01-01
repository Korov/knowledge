package com.korov.gradle.knowledge.accumulation.Thread.Executor;

import java.util.List;
import java.util.concurrent.*;

public abstract class Renderer {
    private final ExecutorService executorService;

    public Renderer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> infos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
        for (ImageInfo imageInfo : infos) {
            completionService.submit(new Callable<>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);

        try {
            for (int t = 0, n = infos.size(); t < n; t++) {
                Future<ImageData> future = completionService.take();
                ImageData imageData = future.get();
                renderImage(imageData);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
