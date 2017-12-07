package com.github.lukaszbudnik.hystrix.resource;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CompositeResourceService implements ResourceService {

    @Inject
    FastResourceService fastResourceService;

    @Inject
    SlowAndFaultyResourceService slowAndFaultyResourceService;

    @Override
    public int save(String resource) {
        slowAndFaultyResourceService.save(resource);
        return fastResourceService.save(resource);
    }

    @Override
    public String get(int id) {
        return fastResourceService.get(id);
    }

}
