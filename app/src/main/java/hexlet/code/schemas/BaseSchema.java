package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema <T> {
    protected Map<String, Predicate<T>> checkMap = new HashMap<>();

    public final boolean isValid(T item) {
        return checkMap.values().stream().allMatch(v -> v.test(item));
    }
}
