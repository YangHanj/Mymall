package iee.yh.Mymall.product.web;

import iee.yh.Mymall.product.service.SkuInfoService;
import iee.yh.Mymall.product.vo.SkuItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @author yanghan
 * @date 2022/11/26
 */
@Controller
public class itemController {

    @Resource
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVo skuItemVo = skuInfoService.item(skuId);
        model.addAttribute("result",skuItemVo);
        return "item";
    }
}
