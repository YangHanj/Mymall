package iee.yh.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import iee.yh.common.utils.R;
import iee.yh.mymall.config.MyThradLocal;
import iee.yh.mymall.feign.ProductFeignService;
import iee.yh.mymall.service.CartService;
import iee.yh.mymall.vo.CartItem;
import iee.yh.mymall.vo.SkuInfoVo;
import iee.yh.mymall.vo.UserInfoTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yanghan
 * @date 2022/12/4
 */
@Service
public class CartServiceImpl implements CartService {
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public CartItem addToCart(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> operations = getCartOps();

        String res = (String) operations.get(skuId.toString());

        if (StringUtils.isEmpty(res)){
            // 添加购物车
            CartItem cartItem = new CartItem();
            CompletableFuture<Void> getInfo = CompletableFuture.runAsync(() -> {
                // 查询商品信息
                R info = productFeignService.info(skuId);
                SkuInfoVo skuInfoVo = info.getData(new TypeReference<SkuInfoVo>() {});
                cartItem.setCheck(true);
                cartItem.setCount(num);
                cartItem.setImage(skuInfoVo.getSkuDefaultImg());
                cartItem.setTitle(skuInfoVo.getSkuTitle());
                cartItem.setSkuId(skuId);
                cartItem.setPrice(skuInfoVo.getPrice());
            }, executor);

            CompletableFuture<Void> getSkuSaleAttrValues = CompletableFuture.runAsync(() -> {
                // 查询sku组合信息
                List<String> skuSaleAttrValues = productFeignService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttr(skuSaleAttrValues);
            }, executor);

            try {
                CompletableFuture.allOf(getInfo,getSkuSaleAttrValues).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String value = JSON.toJSONString(cartItem);
            operations.put(skuId.toString(),value);

            return cartItem;
        }else {
            CartItem item = JSON.parseObject(res, CartItem.class);
            item.setCount(item.getCount() + num);
            operations.put(skuId.toString(),JSON.toJSONString(item));
            return item;
        }
    }

    @Override
    public CartItem addToCartSuccess(Long skuId) {
        BoundHashOperations<String, Object, Object> operations = getCartOps();
        String res = (String) operations.get(skuId.toString());
        return JSON.parseObject(res, CartItem.class);
    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = (UserInfoTo) MyThradLocal.getInstance().get();
        String cartKey = "";
        String CART_PREFIX = "mymall:cart:";
        if (userInfoTo.getUserId() != null){
            cartKey = CART_PREFIX + userInfoTo.getUserId();
        }else {
            // 用户未登录，使用临时购物车
            cartKey = CART_PREFIX + userInfoTo.getUserKey();
        }
        return redisTemplate.boundHashOps(cartKey);
    }
}
