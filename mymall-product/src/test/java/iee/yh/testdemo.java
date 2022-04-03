package iee.yh;

import iee.yh.Mymall.product.entity.BrandEntity;
import iee.yh.Mymall.product.mymallProductApplication;
import iee.yh.Mymall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = mymallProductApplication.class)
public class testdemo {

    @Resource
    public BrandService brandService;

    @Test
    public void contextLoads(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Apple");
        boolean save = brandService.save(brandEntity);
        System.out.println(save);
    }
    @Test
    public void contextLoads2(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("m1 yyds");
        boolean save = brandService.updateById(brandEntity);
        System.out.println(save);
    }

}
