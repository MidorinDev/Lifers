package life.midorin.info.lifers.util;

import java.util.function.Consumer;

public class Constants {

    private final static Consumer<?> NO_OPERATION = it -> {};

    public static <T> Consumer<T> noOperation() {
        return (Consumer<T>) NO_OPERATION;
    }
}
