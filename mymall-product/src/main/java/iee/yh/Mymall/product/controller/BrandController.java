package iee.yh.Mymall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import iee.yh.common.validator.group.AddGroup;
import iee.yh.common.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iee.yh.Mymall.product.entity.BrandEntity;
import iee.yh.Mymall.product.service.BrandService;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody @Validated({AddGroup.class}) BrandEntity brand/*,BindingResult result*/){
//        Map<String,String> map = new HashMap<>();
//        if (result.hasErrors()){
//            result.getFieldErrors().forEach(info -> {
//                String message = info.getDefaultMessage();
//                String field = info.getField();
//                map.put(field,message);
//            });
//            return R.error(400,"数据不合法").put("data",map);
//        }
		try{
            brandService.save(brand);
        }
        catch (Exception e){
            throw e;
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody @Validated({UpdateGroup.class}) BrandEntity brand){
		brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
