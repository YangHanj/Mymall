package iee.yh.Mymall.ware.service.impl;

import iee.yh.common.constant.WareConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.ware.dao.PurchaseDetailDao;
import iee.yh.Mymall.ware.entity.PurchaseDetailEntity;
import iee.yh.Mymall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByParams(Map<String, Object> params) {
        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key))
            queryWrapper.and((info)->{
                info.eq("sku_id",key).or().eq("purchase_id",key);
            });
        if (!StringUtils.isEmpty(status))
            queryWrapper.eq("status",status);
        if (!StringUtils.isEmpty(wareId))
            queryWrapper.eq("ware_id",wareId);
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchase(Long id) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_id",id);
        List<PurchaseDetailEntity> list = this.list(queryWrapper);

        return list;
    }

}