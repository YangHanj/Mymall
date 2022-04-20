package iee.yh.Mymall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import iee.yh.Mymall.ware.vo.MergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import iee.yh.Mymall.ware.entity.PurchaseEntity;
import iee.yh.Mymall.ware.service.PurchaseService;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.R;

import javax.validation.constraints.NotNull;


/**
 * 采购信息
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 采购单领取
     * @param ids
     * @return
     */
    @PostMapping("/received/{username}")
    public R received(@RequestBody @NotNull List<Long> ids,@PathVariable String username){
        if (ids.size() == 0)
            return R.error();
        purchaseService.received(ids,username);
        return R.ok();
    }

    @GetMapping("/unreceive/list")
    public R list(){
        PageUtils page = purchaseService.queryPageUnreceive();
        return R.ok().put("page",page);
    }

    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo){
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setUpdateTime(new Date());
        purchase.setCreateTime(new Date());
		purchaseService.save(purchase);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
