package ee.energia.test.common;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public final class CustomFilterBuilder {
    private Pageable pageable;
    private Map<String, Object> fields;

    public CustomFilterBuilder() {
        fields = new HashMap<>();
    }

    public static CustomFilterBuilder aCustomFilter() {
        return new CustomFilterBuilder();
    }

    public CustomFilterBuilder withPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    public CustomFilterBuilder withParameter(String key, Object value) {
        this.fields.put(key, value);
        return this;
    }

    public CustomFilter build() {
        CustomFilter filter = new CustomFilter();
        filter.setPageable(pageable);
        filter.setFields(fields);
        return filter;
    }
}
