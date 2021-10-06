package ee.energia.test.dto;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum CurrencyItem {
    EUR500(500F), EUR100(100F), EUR50(50F), EUR20(20F), EUR10(10F), EUR5(5F), EUR2(2F), EUR1(1F),
    CENT50(0.5F), CENT20(0.2F), CENT10(0.1F), CENT5(0.05F), CENT2(0.02F), CENT1(0.01F), CENT0(0F);

    private final Float value;

    CurrencyItem(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public static CurrencyItem getValue(Float value) {
        AtomicReference<CurrencyItem> returnValue = new AtomicReference<>(CENT0);
        Arrays.stream(CurrencyItem.values()).forEach(item -> {
            if (value.compareTo(item.getValue()) == 0) {
                returnValue.set(item);
            }
        });
        return returnValue.get();
    }
}
