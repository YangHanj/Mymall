package iee.yh.Mymall.product.app;

import java.util.*;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import iee.yh.Mymall.product.entity.BrandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import iee.yh.Mymall.product.entity.CategoryBrandRelationEntity;
import iee.yh.Mymall.product.service.CategoryBrandRelationService;
import iee.yh.common.utils.R;

import javax.validation.constraints.NotNull;


/**
 * 品牌分类关联
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-10 08:55:14
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 获取所有品牌关联的所有分类列表
     * 列表
     */
    @GetMapping("/catelog/list")
    public R list(@RequestParam("brandId")Long params){
        QueryWrapper<CategoryBrandRelationEntity> queryWrapper = new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",params);
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(queryWrapper);
        return R.ok().put("data",data);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){

		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/brands/list")
    public R getBrandWithCategory(@RequestParam(value = "catId",required = true) @NotNull Long catId ){
        List<BrandEntity> brandEntity = categoryBrandRelationService.getBrandWithCategory(catId);
        List<Map<String, Object>> lmap = new ArrayList<>();
        for (BrandEntity entity : brandEntity) {
            Map<String,Object> map = new HashMap<>();
            map.put("brandId",entity.getBrandId());
            map.put("brandName",entity.getName());
            lmap.add(map);
        }
        return R.ok().put("data",lmap);
    }
}
