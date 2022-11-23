package iee.yh.Mymall.product.feign;

import iee.yh.Mymall.product.vo.SkuHasstockVo;
import iee.yh.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasstock")
    R getSKuHasStock(@RequestBody List<Long> skuIds);
}
