## Pipe operation in Java 8

A common coding pattern is transforming data from A -> B. With intro of functional programming concepts in Java, this has become even more common.
Languages like <b>Elixir</b> and <b>F#</b> offer a special operator for pipe, something similar can be achieved in <b>Java</b>.
Function's andThen method is a way to accomplish the task but the apply at the end and method order does not read naturally.

Ex:
<pre>
 BiFunction<Integer, Integer, Integer> multiply = (a, b) -> ab;
 Boolean result =
     multiply
         .andThen(a -> a + 10)
         .andThen(a -> a % 2 == 0)
         .apply(2, 3);
</pre>
vs
<pre>
 Boolean result =
     Pipe.of(2, 3)
         .pipe((a, b) -> ab)
         .pipe(a -> a + 10)
         .pipe(a -> a % 2 == 0)
         .result();
</pre>

This classes also introduces a peak method for debugging/logging intermediate values in the chain.

Ex:
<pre>
 BiFunction<Integer, Integer, Integer> multiply = (a, b) -> ab;
 Boolean result =
     multiply
         .andThen(a -> a + 10)
         .andThen(a -> a % 2 == 0)
         .andThen(a -> {
                 System.out.println(a);
                 return a;
          })
         .apply(2, 3);
</pre>
vs
<pre>
 Boolean result =
     Pipe.of(2, 3)
         .pipe((a, b) -> ab)
         .pipe(a -> a + 10)
         .pipe(a -> a % 2 == 0)
         .peak(System.out::println)
         .result();
</pre>
