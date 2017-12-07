package com.github.lukaszbudnik.hystrix.resource;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class SlowAndFaultyResourceService implements ResourceService {

    private boolean faulty;

    public SlowAndFaultyResourceService(boolean faulty) {
        this.faulty = faulty;
    }

    @Override
    public int save(String resource) {
        Random r = new Random();
        try {
            Thread.sleep(600 + new Random().nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (faulty && r.nextBoolean()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("boom, there was 50% probability it would fail, I know high!");
        }
        return 123;
    }

    @Override
    public String get(int id) {
        Random r = new Random();
        try {
            Thread.sleep(300 + new Random().nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (faulty && r.nextInt(4) == 3) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("boom, there was 25% probability it would fail");
        }
        return "resource-" + id;
    }
}
