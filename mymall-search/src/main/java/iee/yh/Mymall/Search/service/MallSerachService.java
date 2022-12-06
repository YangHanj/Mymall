package iee.yh.Mymall.Search.service;

import iee.yh.Mymall.Search.vo.SearchParam;
import iee.yh.Mymall.Search.vo.SearchResult;

/**
 * @author yanghan
 * @date 2022/11/26
 */
public interface MallSerachService {

    /**
     * @param searchParam 参与检索的所有参数
     * @return 检索得到的结果
     */
    SearchResult search(SearchParam searchParam);
}
