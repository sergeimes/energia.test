package ee.energia.test.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BasketSession {
    private String session;
    private Date created;
}
