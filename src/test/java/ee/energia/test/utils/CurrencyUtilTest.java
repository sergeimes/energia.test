package ee.energia.test.utils;

import ee.energia.test.dto.CurrencyItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyUtilTest {

    @Test
    void getChangesFor100() {
        Map<CurrencyItem, Integer> changes = CurrencyUtil.getChanges(new BigDecimal(100));
        assertThat(changes).isNotEqualTo(null);
        assertThat(changes.size()).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.EUR100)).isEqualTo(1);
    }

    @Test
    void getChangesFor12() {
        Map<CurrencyItem, Integer> changes = CurrencyUtil.getChanges(new BigDecimal(12));
        assertThat(changes).isNotEqualTo(null);
        assertThat(changes.size()).isEqualTo(2);
        assertThat(changes.get(CurrencyItem.EUR10)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.EUR2)).isEqualTo(1);
    }

    @Test
    void getChangesFor14DOT22() {
        Map<CurrencyItem, Integer> changes = CurrencyUtil.getChanges(new BigDecimal(14.22));
        assertThat(changes).isNotEqualTo(null);
        assertThat(changes.size()).isEqualTo(4);
        assertThat(changes.get(CurrencyItem.EUR10)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.EUR2)).isEqualTo(2);
        assertThat(changes.get(CurrencyItem.CENT20)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.CENT2)).isEqualTo(1);
    }

    @Test
    void getChangesFor14DOT23() {
        Map<CurrencyItem, Integer> changes = CurrencyUtil.getChanges(new BigDecimal(14.23));
        assertThat(changes).isNotEqualTo(null);
        assertThat(changes.size()).isEqualTo(5);
        assertThat(changes.get(CurrencyItem.EUR10)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.EUR2)).isEqualTo(2);
        assertThat(changes.get(CurrencyItem.CENT20)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.CENT2)).isEqualTo(1);
        assertThat(changes.get(CurrencyItem.CENT1)).isEqualTo(1);
    }

}