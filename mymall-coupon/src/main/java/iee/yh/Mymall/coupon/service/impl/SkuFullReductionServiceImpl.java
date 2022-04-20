package iee.yh.Mymall.coupon.service.impl;

import iee.yh.Mymall.coupon.entity.MemberPriceEntity;
import iee.yh.Mymall.coupon.entity.SkuLadderEntity;
import iee.yh.Mymall.coupon.service.MemberPriceService;
import iee.yh.Mymall.coupon.service.SkuLadderService;
import iee.yh.common.to.MemberPrice;
import iee.yh.common.to.SkuReductionTo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.coupon.dao.SkuFullReductionDao;
import iee.yh.Mymall.coupon.entity.SkuFullReductionEntity;
import iee.yh.Mymall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private SkuLadderService skuLadderService;
    @Autowired
    private MemberPriceService memberPriceService;
    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        //保存满减打折，会员价
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        if (skuLadderEntity.getFullCount() > 0)
            skuLadderService.save(skuLadderEntity);

        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal(0)) == 1)
            this.save(skuFullReductionEntity);

        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPrice.stream().map(info -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(info.getId());
            memberPriceEntity.setMemberLevelName(info.getName());
            memberPriceEntity.setMemberPrice(info.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(i -> {
            return i.getMemberPrice().compareTo(new BigDecimal(0)) == 1;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(memberPriceEntities);
    }

}