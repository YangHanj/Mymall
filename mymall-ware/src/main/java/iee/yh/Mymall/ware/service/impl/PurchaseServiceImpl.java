package iee.yh.Mymall.ware.service.impl;

import iee.yh.Mymall.ware.entity.PurchaseDetailEntity;
import iee.yh.Mymall.ware.service.PurchaseDetailService;
import iee.yh.Mymall.ware.vo.MergeVo;
import iee.yh.common.constant.WareConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.ware.dao.PurchaseDao;
import iee.yh.Mymall.ware.entity.PurchaseEntity;
import iee.yh.Mymall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public PageUtils queryPageUnreceive() {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status",0,1);
        //List<PurchaseEntity> purchaseEntities = this.baseMapper.selectList(queryWrapper);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(new HashMap<>()),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null){
            //新建
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatus.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);

            purchaseId = purchaseEntity.getId();
        }
        //确认采购单状态是0或者1
        //TODO ......
        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(info -> {
            PurchaseDetailEntity p = purchaseDetailService.getOne(new QueryWrapper<PurchaseDetailEntity>().eq("id", info));
            if (p.getStatus() != 0)
                return null;
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(info);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatus.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        collect.remove(null);
        if (collect == null || collect.size() == 0)
            return;
        purchaseDetailService.updateBatchById(collect);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setStatus(WareConstant.PurchaseStatus.ASSIGNED.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

    /**
     *
     * @param ids 采购单id
     * @param username
     */
    @Override
    public void received(List<Long> ids,String username) {
        //确认当前采购单是新建或者以分配
        List<PurchaseEntity> collect = this.listByIds(ids).stream().filter(info -> {
            Integer status = info.getStatus();
            if (status == WareConstant.PurchaseStatus.CREATED.getCode()
                    || status == WareConstant.PurchaseStatus.ASSIGNED.getCode()) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        //确认是否为自己的采购单
        collect = collect.stream().filter(info -> {
            if (info.getAssigneeName().equals(username))
                return true;
            else return false;
        }).collect(Collectors.toList());
        //改变采购单的状态
        collect = collect.stream().map( info -> {
            info.setStatus(WareConstant.PurchaseStatus.RECEIVE.getCode());
            info.setUpdateTime(new Date());
            return info;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
        //改变采购项的状态
        collect.forEach( info -> {
            List<PurchaseDetailEntity> entity = purchaseDetailService.listDetailByPurchase(info.getId());
            List<PurchaseDetailEntity> purchaseDetailEntities = entity.stream().map(e -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(e.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatus.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(purchaseDetailEntities);
        });
    }
}