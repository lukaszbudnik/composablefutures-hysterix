package com.github.lukaszbudnik.hystrix.resource;

import com.google.inject.Injector;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class CompositeHystrixResourceService implements ResourceService {

    @Inject
    FastResourceService fastResourceService;

    @Inject
    Injector injector;

    @Override
    public int save(String resource) {

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();

        SlowAndFaultyResourceServiceSaveCommand slowAndFaultyResourceServiceSaveCommand = injector.getInstance(SlowAndFaultyResourceServiceSaveCommand.class);
        slowAndFaultyResourceServiceSaveCommand.setResource(resource);

        CompletableFuture<Integer> slow = CompletableFuture.supplyAsync(() ->
                slowAndFaultyResourceServiceSaveCommand.execute(),
                es);

        CompletableFuture<Integer> fast = CompletableFuture.supplyAsync(() ->
                fastResourceService.save(resource),
                es);

        try {
            return fast.thenCombine(slow, (f, s) -> f).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            es.shutdown();
            System.out.println("Resource ==> " + resource + " executed commands ==> " + HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
            ctx.shutdown();
        }

    }

    @Override
    public String get(int id) {
        return fastResourceService.get(id);
    }

}