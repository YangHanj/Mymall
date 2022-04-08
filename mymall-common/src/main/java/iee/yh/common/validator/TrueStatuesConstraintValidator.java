package iee.yh.common.validator;

import iee.yh.common.validator.group.TrueStatues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义校验器
 * @author yanghan
 * @date 2022/4/8
 */
public class TrueStatuesConstraintValidator implements ConstraintValidator<TrueStatues,Integer> {

    private Set<Integer> set = new HashSet<>();
    //初始化方法
    @Override
    public void initialize(TrueStatues constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            set.add(val);
        }
    }

    /**
     *
     * @param integer 需要校验的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if(set.contains(integer))
            return true;
        return false;
    }
}
