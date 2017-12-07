package com.github.lukaszbudnik.hystrix.data;

import java.util.List;

public interface DataService {

    void save(List<String> data);

    List<String> get(int id);

}
