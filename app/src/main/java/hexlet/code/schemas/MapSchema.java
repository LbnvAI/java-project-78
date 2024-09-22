package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?,?>> {
    public MapSchema required() {
        this.checkMap.put("required", item -> !Objects.isNull(item));
        return this;
    }

    public MapSchema sizeof(int size) {
        this.checkMap.put("sizeof", item -> {
            if (checkMap.containsKey("required")) {
                return checkMap.get("required").test(item) && item.size() == size;
            } else {
                return Objects.isNull(item) || item.size() == size;
            }
        });
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema<?>> schemas) {
        this.checkMap.put("shape", item ->
                item.entrySet().stream().allMatch(entry ->
                        schemas.get(entry.getKey()).isValid(entry.getValue())));
        return this;
    }
}
