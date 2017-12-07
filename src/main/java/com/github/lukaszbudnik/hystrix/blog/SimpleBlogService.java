package com.github.lukaszbudnik.hystrix.blog;

import com.github.lukaszbudnik.hystrix.data.DataService;
import com.github.lukaszbudnik.hystrix.resource.ResourceService;

import javax.inject.Inject;

public class SimpleBlogService implements BlogService {

    @Inject
    DataService dataService;

    @Inject
    ResourceService resourceService;

    @Override
    public void save(Blog blog) throws Exception {
        for (String file : blog.getFiles()) {
            resourceService.save(file);
        }
        dataService.save(blog.getLines());
    }

    @Override
    public Blog get(int limit) {
        return null;
    }
}
