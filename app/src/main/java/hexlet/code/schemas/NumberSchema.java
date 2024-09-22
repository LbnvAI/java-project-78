package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        this.checkMap.put("required", item -> !Objects.isNull(item));
        return this;
    }

    public NumberSchema positive() {
        this.checkMap.put("positive", item -> {
            if (checkMap.containsKey("required")) {
                return checkMap.get("required").test(item) && item > 0;
            } else {
                return Objects.isNull(item) || item > 0;
            }
        });
        return this;
    }

    public NumberSchema range(int begin, int end) {
        this.checkMap.put("range", item -> !Objects.isNull(item) && item >= begin && item <= end);
        return this;
    }
}
