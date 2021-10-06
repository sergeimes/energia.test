package ee.energia.test.controller;

import ee.energia.test.dto.CheckoutBasket;
import ee.energia.test.dto.ShopItemDTO;
import ee.energia.test.service.ShopItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/shopitems")
@RequiredArgsConstructor
@CafeteriaAPI
public class ShopItemController extends BaseController {

    private final ShopItemService agentService;

    @GetMapping("/find")
    public Page<ShopItemDTO> findItems(@RequestParam Map<String, String> parameters) {
        return agentService.findItems(getPageFilter(parameters));
    }

    @GetMapping("/all")
    public List<ShopItemDTO> getAllItems() {
        return agentService.getAll();
    }

    @GetMapping("/{itemId}")
    public ShopItemDTO getItem(@PathVariable Long itemId) {
        return agentService.findById(itemId);
    }

    @PostMapping("/save")
    public ShopItemDTO save(@Valid @RequestBody ShopItemDTO shopItem) {
        return agentService.save(shopItem);
    }

}
