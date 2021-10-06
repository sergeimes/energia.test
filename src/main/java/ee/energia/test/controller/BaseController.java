package ee.energia.test.controller;

import ee.energia.test.common.CustomFilter;
import ee.energia.test.common.CustomFilterBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class BaseController {

    private static final int DEFAULT_SIZE = 5;

    protected CustomFilter getPageFilter(Map<String,String> parameters) {
        int page = 0;
        if (parameters.containsKey("p")) {
            page = Integer.valueOf(parameters.get("p"));
            parameters.remove("p");
        }
        int size = 0;
        if (parameters.containsKey("s")) {
            size = Integer.valueOf(parameters.get("s"));
            parameters.remove("s");
        }
        if (size <= 0 || size > 100) {
            size = DEFAULT_SIZE;
        }
        PageRequest pageRequest = PageRequest.of(page, size);

        if (parameters.containsKey("sort") && StringUtils.isNoneEmpty(parameters.get("sort"))) {
            String sort = parameters.get("sort");
            String dir = parameters.get("dir");
            Sort.Direction direction = Sort.Direction.ASC;
            if (StringUtils.isNoneEmpty(dir)) {
                try {
                    direction = Sort.Direction.valueOf(StringUtils.upperCase(dir));
                } catch (IllegalArgumentException e) {
                }
            }
            pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));
            parameters.remove("sort");
            if (parameters.containsKey("dir")) {
                parameters.remove("dir");
            }
        }

        CustomFilterBuilder builder = CustomFilterBuilder.aCustomFilter();
        if (pageRequest != null) {
            builder.withPageable(pageRequest);
        }
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.withParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
