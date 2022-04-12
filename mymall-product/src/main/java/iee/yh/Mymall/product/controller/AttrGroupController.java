package iee.yh.Mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import iee.yh.Mymall.product.entity.AttrEntity;
import iee.yh.Mymall.product.service.AttrAttrgroupRelationService;
import iee.yh.Mymall.product.service.AttrService;
import iee.yh.Mymall.product.service.CategoryService;
import iee.yh.Mymall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import iee.yh.Mymall.product.entity.AttrGroupEntity;
import iee.yh.Mymall.product.service.AttrGroupService;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.R;



/**
 * 属性分组
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogid}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("")Long catelogid){
        PageUtils pageUtils = attrGroupService.queryPage(params, catelogid);
        return R.ok().put("page", pageUtils);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        try{
            Long catelogId = attrGroup.getCatelogId();
            attrGroup.setCatelogPath(categoryService.findCatelogPath(catelogId));
            return R.ok().put("attrGroup", attrGroup);
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }
    @RequestMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] attrGroupRelationVo){
        attrService.deleteRelation(attrGroupRelationVo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @Autowired
    private AttrService attrService;

    /**
     * @param id 当前分组id
     * @return
     */
    @GetMapping("/{id}/attr/relation")
    public R attrRelation(@PathVariable Long id){
        List<AttrEntity> attrEntities = attrService.getRelationAttr(id);
        return R.ok().put("data",attrEntities);
    }

    @GetMapping("/{id}/noattr/relation")
    public R noattrRelation(@RequestParam Map<String,Object> params,@PathVariable Long id){
        PageUtils pageUtils = attrService.getNoRelationAttr(params,id);
        return R.ok().put("page",pageUtils);
    }
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        attrAttrgroupRelationService.saveBatch(relationVos);
        return R.ok();
    }

}
