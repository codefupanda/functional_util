package com.codefupanda.util.function;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A common coding pattern is transforming data from A -> B. With intro of functional programming concepts in Java, this has become even more common.
 * Languages like <b>Elixir</b> and <b>F#</b> offer a special operator for pipe, something similar can be achieved in <b>Java</b> with this class.
 *
 * {@link Function}'s andThen is a way to accomplish the task but the apply at the end and method order does not read naturally.
 *
 * Ex:
 * <pre>
 *  BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
 *  Boolean result =
 *      multiply
 *          .andThen(a -> a + 10)
 *          .andThen(a -> a % 2 == 0)
 *          .apply(2, 3);
 * </pre>
 * vs
 * <pre>
 *  Boolean result =
 *      Pipe.of(2, 3)
 *          .pipe((a, b) -> a * b)
 *          .pipe(a -> a + 10)
 *          .pipe(a -> a % 2 == 0)
 *          .result();
 * </pre>
 *
 * This classes also introduces a peak method for debugging/logging intermediate values in the chain.
 *
 * Ex:
 * <pre>
 *  BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
 *  Boolean result =
 *      multiply
 *          .andThen(a -> a + 10)
 *          .andThen(a -> a % 2 == 0)
 *          .andThen(a -> {
 *                  System.out.println(a);
 *                  return a;
 *           })
 *          .apply(2, 3);
 * </pre>
 * vs
 * <pre>
 *  Boolean result =
 *      Pipe.of(2, 3)
 *          .pipe((a, b) -> a * b)
 *          .pipe(a -> a + 10)
 *          .pipe(a -> a % 2 == 0)
 *          .peak(System.out::println)
 *          .result();
 * </pre>
 * @author Shashank (shashank.physics@gmail.com)
 */
public class Pipe<R> {

    private R result;

    private Pipe(R result) {
        this.result = result;
    }

    public static <R> Pipe<R> of(R init) {
        return new Pipe<>(init);
    }

    public static <U, V> BiPipe<U, V> of(U first, V second) {
        return new BiPipe<>(first, second);
    }

    public <T> Pipe<T> pipe(Function<R, T> function) {
        return new Pipe<>(function.apply(result));
    }

    /**
     * Cases where you want to have side effect but continue the method chain with same value.
     * Quite handy for printing values.
     *
     * @param consumer
     * @return
     */
    public Pipe<R> peak(Consumer<R> consumer) {
        consumer.accept(result);
        return this;
    }

    public R result() {
        return result;
    }

    /**
     * BiPipe will be used {@link BiFunction}.
     *
     * @param <U>
     * @param <V>
     */
    static class BiPipe<U, V> {
        private U first;
        private V second;

        private BiPipe(U first, V second) {
            this.first = first;
            this.second = second;
        }

        public <R> Pipe<R> pipe(BiFunction<U, V, R> function) {
            return new Pipe<>(function.apply(first, second));
        }

        public BiPipe<U, V> peak(BiConsumer<U, V> consumer) {
            consumer.accept(first, second);
            return this;
        }
    }
}
