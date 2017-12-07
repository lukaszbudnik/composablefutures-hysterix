package com.github.lukaszbudnik.hystrix.resource;


import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class FastResourceService implements ResourceService {
    @Override
    public int save(String resource) {
        try {
            Thread.sleep(500 + new Random().nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 123;
    }

    @Override
    public String get(int id) {
        try {
            Thread.sleep(200 + new Random().nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "resource-" + id;
    }
}
