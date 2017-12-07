package com.github.lukaszbudnik.hystrix.blog;

public interface BlogService {

    void save(Blog blog) throws Exception;

    Blog get(int limit);

}
