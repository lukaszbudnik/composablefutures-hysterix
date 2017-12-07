package com.github.lukaszbudnik.hystrix.blog;

import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;

import javax.inject.Inject;

public class ParallelBlogService implements BlogService {

    @Inject
    DataService dataService;

    @Inject
    ResourceService resourceService;

    @Override
    public void save(Blog blog) throws Exception {
        blog.getFiles().parallelStream().forEach(f -> resourceService.save(f));
        dataService.save(blog.getLines());
    }

    @Override
    public Blog get(int limit) {
        return null;
    }
}
