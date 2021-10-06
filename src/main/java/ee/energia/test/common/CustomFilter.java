package ee.energia.test.common;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class CustomFilter {

    private Pageable pageable;
    private Map<String, Object> fields;

    public CustomFilter() {
        fields = new HashMap<>();
    }

    public CustomFilter(Pageable pageable) {
        this();
        this.pageable = pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Object getField(String key) {
        return fields.get(key);
    }

    public String getStringField(String key) {
        String value = null;
        Object o = getField(key);
        if (o != null) {
            value = o.toString();
        }
        return value;
    }

    public Long getLongField(String key) {
        Long value = null;
        String o = getStringField(key);
        if (o != null) {
            value = Long.valueOf(o);
        }
        return value;
    }
}
