package iee.yh.mymall.controller;

import iee.yh.mymall.config.MyThradLocal;
import iee.yh.mymall.service.CartService;
import iee.yh.mymall.vo.CartItem;
import iee.yh.mymall.vo.UserInfoTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author yanghan
 * @date 2022/12/4
 */
@Controller()
@RequestMapping("/cart")
public class CartController {

    @GetMapping("/cart.html")
    public String CartListPage(){
        UserInfoTo userInfoTo = (UserInfoTo) MyThradLocal.getInstance().get();
        return "cartList";
    }

    @Autowired
    CartService cartService;

    /**
     * 添加购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToGart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            Model model,
                            RedirectAttributes attributes){
        CartItem cartItem = cartService.addToCart(skuId, num);
        attributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.mymall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccess(@RequestParam("skuId") Long skuId,Model model){
        CartItem cartItem = cartService.addToCartSuccess(skuId);
        model.addAttribute("item",cartItem);
        return "success";
    }
}
