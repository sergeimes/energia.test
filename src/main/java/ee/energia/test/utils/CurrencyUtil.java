package ee.energia.test.utils;

import ee.energia.test.dto.CurrencyItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CurrencyUtil {

    public static Map<CurrencyItem, Integer> getChanges(BigDecimal value) {
        Map<CurrencyItem, Integer> values = new LinkedHashMap<>();
        if (value == null || BigDecimal.ZERO.compareTo(value) > 0) {
            return values;
        }
        AtomicReference<BigDecimal> difference = new AtomicReference<>(value);
        Arrays.stream(CurrencyItem.values()).forEach(item -> {
            if (item.getValue() > 0) {
                BigDecimal[] remainders = difference.get().setScale(2, RoundingMode.HALF_UP).divideAndRemainder(new BigDecimal(item.getValue()));
                if (remainders[0].compareTo(BigDecimal.ONE) >= 0) {
                    difference.set(difference.get().add(BigDecimal.valueOf(item.getValue()).multiply(remainders[0]).multiply(BigDecimal.valueOf(-1))));
                    values.put(item, remainders[0].intValue());
                }
            }
        });
        if (difference.get().compareTo(BigDecimal.ZERO) > 0) {
            CurrencyItem lastCentIem = CurrencyItem.getValue(difference.get().setScale(2, RoundingMode.HALF_UP).floatValue());
            if (CurrencyItem.CENT1.equals(lastCentIem)) {
                values.put(lastCentIem, 1);
            }
        }
        return values;
    }
}
