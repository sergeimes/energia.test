package ee.energia.test.component;

import ee.energia.test.service.BasketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final BasketService basketService;

    //run every 10 minutes
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void scheduleTaskClearVerifiedExpiredCodes() {
        basketService.resetExpiredSessions();
        log.debug("Expired baskets are cleared");
    }
}
