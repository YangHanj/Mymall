package iee.yh.Mymall.product.web;

import iee.yh.Mymall.product.entity.CategoryEntity;
import iee.yh.Mymall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class indexController {

    @Resource
    CategoryService categoryService;

    @GetMapping({"/index.html","/"})
    public String indexPage(Model model){
        // 查询一级分类
        List<CategoryEntity> categoryServices = categoryService.getLeve1Categorys();
        model.addAttribute("categorys",categoryServices);
        return "index";
    }
}
