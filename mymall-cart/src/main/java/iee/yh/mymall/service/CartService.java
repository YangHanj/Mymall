package iee.yh.mymall.service;

import iee.yh.mymall.vo.CartItem;

/**
 * @author yanghan
 * @date 2022/12/4
 */
public interface CartService {
    CartItem addToCart(Long skuId, Integer num);

    CartItem addToCartSuccess(Long skuId);
}
