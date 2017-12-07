package com.github.lukaszbudnik.hystrix.core;

import com.github.lukaszbudnik.hystrix.blog.BlogService;
import com.github.lukaszbudnik.hystrix.blog.SimpleBlogService;
import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.data.SimpleDataService;
import com.github.lukaszbudnik.hystrix.resource.CompletableFutureCompositeResourceService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;
import com.github.lukaszbudnik.hystrix.resource.SlowAndFaultyResourceService;
import com.google.inject.Binder;

public class SimpleCompletableFutureBlogGuiceModule extends AbstractBlogModule {

    public SimpleCompletableFutureBlogGuiceModule(boolean faulty) {
        super(faulty);
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(BlogService.class).to(SimpleBlogService.class);
        binder.bind(ResourceService.class).to(CompletableFutureCompositeResourceService.class);
        binder.bind(DataService.class).to(SimpleDataService.class);
        binder.bind(SlowAndFaultyResourceService.class).toInstance(new SlowAndFaultyResourceService(faulty));
    }

}
