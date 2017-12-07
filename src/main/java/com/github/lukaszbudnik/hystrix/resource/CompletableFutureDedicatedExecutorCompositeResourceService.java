package com.github.lukaszbudnik.hystrix.resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class CompletableFutureDedicatedExecutorCompositeResourceService implements ResourceService {

    @Inject
    FastResourceService fastResourceService;

    @Inject
    SlowAndFaultyResourceService slowAndFaultyResourceService;

    @Override
    public int save(String resource) {

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<Integer> slow = CompletableFuture.supplyAsync(() ->
                slowAndFaultyResourceService.save(resource)
                , es);

        CompletableFuture<Integer> fast = CompletableFuture.supplyAsync(() ->
                fastResourceService.save(resource)
                , es);

        try {
            return fast.thenCombine(slow, (f, s) -> f).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            es.shutdown();
        }

    }

    @Override
    public String get(int id) {
        return fastResourceService.get(id);
    }

}