package com.github.lukaszbudnik.hystrix.core;

import com.github.lukaszbudnik.hystrix.blog.BlogService;
import com.github.lukaszbudnik.hystrix.blog.FullyParallelBlogService;
import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.data.ParallelDataService;
import com.github.lukaszbudnik.hystrix.resource.CompositeHystrixResourceService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;
import com.github.lukaszbudnik.hystrix.resource.SlowAndFaultyResourceService;
import com.google.inject.Binder;

public class FullyParallelCompletableFutureDedicatedExecutorHystrixBlogGuiceModule extends AbstractBlogModule {

    public FullyParallelCompletableFutureDedicatedExecutorHystrixBlogGuiceModule(boolean faulty) {
        super(faulty);
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(BlogService.class).to(FullyParallelBlogService.class);
        binder.bind(ResourceService.class).to(CompositeHystrixResourceService.class);
        binder.bind(DataService.class).to(ParallelDataService.class);
        binder.bind(SlowAndFaultyResourceService.class).toInstance(new SlowAndFaultyResourceService(faulty));
    }

}
