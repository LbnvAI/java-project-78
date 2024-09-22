package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        this.checkMap.put("required", item -> !Objects.isNull(item));
        return this;
    }

    public MapSchema sizeof(int size) {
        this.checkMap.put("sizeof", item -> {
            if (checkMap.containsKey("required")) {
                return checkMap.get("required").test(item) && item.size() == size;
            } else {
                return !Objects.isNull(item) && item.size() == size;
            }
        });
        return this;
    }

    public <T> MapSchema shape(Map<?, BaseSchema<T>> schemas) {
        this.checkMap.put("shape", item ->
                schemas.entrySet().stream().allMatch(entry ->
                        entry.getValue().isValid((T) item.get(entry.getKey()))));
        //schemas.get(entry.getKey()).isValid((T) entry.getValue())));
        return this;
    }
}
