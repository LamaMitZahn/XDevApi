package de.ruben.xdevapi.custom.gui.consumer;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, T1, T2> {

    void accept(T t, T1 t1, T2 t2);

    default TriConsumer<T, T1, T2> andThen(TriConsumer<? super T, ? super T1, ? super T2> after) {
        Objects.requireNonNull(after);

        return (t, t1, t2) -> {
            accept(t, t1, t2);
            after.accept(t, t1, t2);
        };
    }

}
