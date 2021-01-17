package com.black_dog20.bml.utils.stream;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtils {

    /**
     * Collects a stream to a shuffled stream.
     *
     * @return a collector that shuffles and returns a stream.
     */
    public static <T> Collector<T, ?, Stream<T>> toShuffledStream() {
        return Collectors.collectingAndThen(Collectors.toList(), collected -> {
            Collections.shuffle(collected);
            return collected.stream();
        });
    }
}