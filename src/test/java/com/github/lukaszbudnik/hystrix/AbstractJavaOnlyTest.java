package com.github.lukaszbudnik.hystrix;

import com.github.lukaszbudnik.hystrix.blog.Blog;
import com.github.lukaszbudnik.hystrix.blog.BlogService;
import com.github.lukaszbudnik.hystrix.core.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public abstract class AbstractJavaOnlyTest {

    private final boolean faulty;

    private Blog blog;

    public AbstractJavaOnlyTest(boolean faulty) {
        this.faulty = faulty;
    }

    @Before
    public void setup() {
        blog = new Blog();
        blog.setFiles(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        blog.setLines(Arrays.asList("1", "2", "3", "4", "5", "6"));
    }


    @Test
    public void s1SaveSimpleBlog() throws Exception {
        Injector injector = Guice.createInjector(new SimpleBlogGuiceModule(false));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }

    @Test
    public void s2SaveSimpleCompletableFutureBlog() throws Exception {
        Injector injector = Guice.createInjector(new SimpleCompletableFutureBlogGuiceModule(faulty));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }

    @Test
    public void s3SaveParallelBlog() throws Exception {
        Injector injector = Guice.createInjector(new ParallelBlogGuiceModule(faulty));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }

    @Test
    public void s4SaveParallelCompletableFutureBlog() throws Exception {
        Injector injector = Guice.createInjector(new ParallelCompletableFutureBlogGuiceModule(faulty));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }

    @Test
    public void s5SaveParallelCompletableFutureDedicatedExecutorBlog() throws Exception {
        Injector injector = Guice.createInjector(new ParallelCompletableFutureDedicatedExecutorBlogGuiceModule(faulty));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }

    @Test
    public void s6SaveFullyParallelCompletableFutureDedicatedExecutorBlog() throws Exception {
        Injector injector = Guice.createInjector(new FullyParallelCompletableFutureDedicatedExecutorBlogGuiceModule(faulty));
        BlogService blogService = injector.getInstance(BlogService.class);

        long start = System.currentTimeMillis();
        blogService.save(blog);
        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Execution took ==> " + time / 1000d);
    }
}
