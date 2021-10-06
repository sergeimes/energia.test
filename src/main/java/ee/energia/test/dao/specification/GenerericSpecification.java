package ee.energia.test.dao.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GenerericSpecification<T> implements Specification<T> {

    private SpecSearchCriteria criteria;

    public GenerericSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        Path<String> path = getPath(root);
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(path, criteria.getValue());
            case NEGATION:
                return builder.notEqual(path, criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(path, criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(path, criteria.getValue().toString());
            case LIKE:
                return builder.like(builder.lower(path), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(builder.lower(path), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(builder.lower(path), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(builder.lower(path), "%" + criteria.getValue() + "%");
            case IS_NULL:
                return path.isNull();
            case IS_NOT_NULL:
                return path.isNotNull();
            default:
                return null;
        }
    }

    private Path<String> getPath(Root<T> root) {
        Path<String> path;
        if (criteria.getKey().contains(".")) {
            String[] split = criteria.getKey().split("\\.");
            int keyPosition = 0;
            path = root.get(split[keyPosition]);
            for (String criteriaKeys : split) {
                if (keyPosition > 0) {
                    path = path.get(criteriaKeys);
                }
                keyPosition++;
            }
        } else {
            path = root.get(criteria.getKey());
        }
        return path;
    }

}