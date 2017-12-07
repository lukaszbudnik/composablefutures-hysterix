package com.github.lukaszbudnik.hystrix.blog;

import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FullyParallelBlogService implements BlogService {

    private static Logger logger = LoggerFactory.getLogger(FullyParallelBlogService.class);

    @Inject
    DataService dataService;

    @Inject
    ResourceService resourceService;

    @Override
    public void save(Blog blog) throws Exception {
        MDC.put("blogName", blog.getName());

        logger.debug("About to save blog");

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<Void> files = CompletableFuture.runAsync(() ->
                blog.getFiles().parallelStream().forEach(f -> resourceService.save(f)), es);

        CompletableFuture<Void> data = CompletableFuture.runAsync(() ->
                dataService.save(blog.getLines()), es);

        files.thenAcceptBoth(data, (f, d) -> {
        }).get();

        es.shutdown();

        logger.debug("Blog saved");

        MDC.clear();
    }

    @Override
    public Blog get(int limit) {
        return null;
    }
}
