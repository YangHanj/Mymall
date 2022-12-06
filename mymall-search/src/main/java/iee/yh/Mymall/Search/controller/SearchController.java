package iee.yh.Mymall.Search.controller;

import iee.yh.Mymall.Search.service.MallSerachService;
import iee.yh.Mymall.Search.vo.SearchParam;
import iee.yh.Mymall.Search.vo.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @author yanghan
 * @date 2022/11/26
 */
@Controller
public class SearchController {

    @Resource
    MallSerachService mallSerachService;

    @GetMapping("/list.html")
    public String listPage(SearchParam searchParam, Model model){
        SearchResult res = mallSerachService.search(searchParam);
        model.addAttribute("result",res);
        return "list";
    }
}
