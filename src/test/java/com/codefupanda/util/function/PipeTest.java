package com.codefupanda.util.function;

import org.junit.Test;

import java.util.function.BiFunction;

public class PipeTest {

    @Test
    public void testFlow() {
        final Integer result = Pipe.of(2).pipe(a -> a * 2).pipe(a -> a + 2).result();
        assert 6 == result;
    }

    @Test
    public void testFlowPeak() {
        final Integer result = Pipe.of(2).pipe(a -> a * 2).pipe(a -> a + 2).peak(System.out::println).result();
        assert 6 == result;
    }

    @Test
    public void testBiFlow() {
        final Integer result = Pipe.of(2, 3).pipe((a, b) -> a * b).pipe(a -> a * 10).result();
        assert 60 == result;
    }

    @Test
    public void testBiFlowAnother() {
        final Integer result = Pipe.of(2, 3).pipe(Integer::sum).result();
        assert 5 == result;
    }

    @Test
    public void testBiFlowPeak() {
        final Integer result = Pipe.of(2, 3).peak((a, b) -> System.out.print(a + " " + b)).pipe((a, b) -> a * b).pipe(a -> a * 10).result();
        assert 60 == result;
    }

    @Test
    public void fs() {
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        Boolean result =
                Pipe.of(2, 3)
                    .pipe((a, b) -> a * b)
                    .pipe(a -> a + 10)
                    .pipe(a -> a % 2 == 0)
                    .peak(System.out::println)
                    .result();
        System.out.println(result);
    }
}
