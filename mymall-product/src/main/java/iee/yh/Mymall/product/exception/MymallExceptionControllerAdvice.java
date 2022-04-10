package iee.yh.Mymall.product.exception;

import iee.yh.common.exception.BizCode;
import iee.yh.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理异常
 * @author yanghan
 * @date 2022/4/8
 */
@Slf4j
@RestControllerAdvice(basePackages = "iee.yh.Mymall.product.controller")
public class MymallExceptionControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    public R handlerVailException(Exception e){
        Map<String,String> map = new HashMap<>();
        if (e instanceof MethodArgumentNotValidException){
            log.error("数据校验出现问题{},异常类型{}",e.getMessage(),e.getClass());
            BindingResult bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
            bindingResult.getFieldErrors().forEach(info -> {
                map.put(info.getField(),info.getDefaultMessage());
            });
            return R.error(
                    BizCode.VALID_EXCEPTION.getCode(),
                    BizCode.VALID_EXCEPTION.getMsg()).put("data",map);
        }else {
            log.error("异常类型{}",e.getMessage());
            return R.error(BizCode.UNKNOW_EXCEPTION.getCode(),BizCode.UNKNOW_EXCEPTION.getMsg());
        }
    }
}
