package iee.yh.Mymall.Search.service;

import iee.yh.common.to.es.SkuEsModel;
import iee.yh.common.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductSaveService {
    Boolean productStatusUp( List<SkuEsModel> skuEsModels);
}
