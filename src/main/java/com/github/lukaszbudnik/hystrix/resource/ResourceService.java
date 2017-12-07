package com.github.lukaszbudnik.hystrix.resource;

public interface ResourceService {

    int save(String resource);

    String get(int id);

}
