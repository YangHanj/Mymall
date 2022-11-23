package iee.yh.Mymall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import iee.yh.Mymall.product.entity.CategoryEntity;
import iee.yh.Mymall.product.service.CategoryService;
import iee.yh.common.utils.R;


/**
 * 商品三级分类
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表,查出所有分类以及子分类，以树型结构显示
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params){
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
        //防止获取到脏数据
        //本次刷新过程不允许其他操作
        synchronized (this){
            CategoryEntity category = categoryService.getById(catId);
            return R.ok().put("data", category);
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
        //首先查找数据库中是否存在
        Integer sameCategory = categoryService.getSameCategory(category);
        if (sameCategory != null){
            categoryService.updateByIdForProduct(sameCategory);
        }
        else categoryService.save(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateByIdDetail(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    /**
     * 利用@RequestBody
     */
    public R delete(@RequestBody Long[] catIds){
        //1、检查当前需要删除的菜单是否被别的地方引用
		//categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }


}
