package com.github.lukaszbudnik.hystrix;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderTest {

    public <T, I> List<I> getOrderSnapshot(List<T> list, Function<T, I> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    public <T, I> Comparator<T> createComparator(List<I> snapshot, Function<T, I> function) {
        return (o1, o2) -> {
            I if1 = function.apply(o1);
            I if2 = function.apply(o2);

            int i1 = snapshot.indexOf(if1);
            int i2 = snapshot.indexOf(if2);

            if (i1 < i2) {
                return 1;
            } else if (i1 > i2) {
                return -1;
            } else {
                return 0;
            }
        };
    }

    @Test
    public void shouldPreserveOrderTest() {
        List<Book> books = new ArrayList();
        books.add(new Book("abc"));
        books.add(new Book("def"));
        books.add(new Book("ghi"));

        List<String> orderSnapshot = getOrderSnapshot(books, (b) -> b.getIsbn());

        Stream<Book> processed = books.parallelStream().map((b) -> {
            long sleep = 0;
            if (b.getIsbn().equals("abc")) {
                sleep = 1000;
            } else if (b.getIsbn().equals("def")) {
                sleep = 1300;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return b;
        });

//        processed.forEach(System.out::println);

        Stream<Book> sorted = processed.sorted(createComparator(orderSnapshot, (b) -> b.getIsbn()));

        sorted.forEach(System.out::println);



    }



}
