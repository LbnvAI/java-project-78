package hexlet.code.schemas;

import java.util.Objects;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        this.checkMap.put("required", item -> !Objects.isNull(item) && !item.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        this.checkMap.put("minLength", item -> {
            if (!Objects.isNull(item)) {
                return item.length() >= length;
            } else return false;
        });
        return this;
    }

    public StringSchema contains(String str) {
        this.checkMap.put("contains", item -> {
            if (!Objects.isNull(item)) {
                return item.contains(str);
            } else return false;
        });
        return this;
    }
}
