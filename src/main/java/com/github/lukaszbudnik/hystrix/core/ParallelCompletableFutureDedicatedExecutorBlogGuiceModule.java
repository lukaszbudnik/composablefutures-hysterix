package com.github.lukaszbudnik.hystrix.core;

import com.github.lukaszbudnik.hystrix.blog.BlogService;
import com.github.lukaszbudnik.hystrix.blog.ParallelBlogService;
import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.data.ParallelDataService;
import com.github.lukaszbudnik.hystrix.resource.CompletableFutureDedicatedExecutorCompositeResourceService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;
import com.github.lukaszbudnik.hystrix.resource.SlowAndFaultyResourceService;
import com.google.inject.Binder;

public class ParallelCompletableFutureDedicatedExecutorBlogGuiceModule extends AbstractBlogModule {

    public ParallelCompletableFutureDedicatedExecutorBlogGuiceModule(boolean faulty) {
        super(faulty);
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(BlogService.class).to(ParallelBlogService.class);
        binder.bind(ResourceService.class).to(CompletableFutureDedicatedExecutorCompositeResourceService.class);
        binder.bind(DataService.class).to(ParallelDataService.class);
        binder.bind(SlowAndFaultyResourceService.class).toInstance(new SlowAndFaultyResourceService(faulty));
    }

}
