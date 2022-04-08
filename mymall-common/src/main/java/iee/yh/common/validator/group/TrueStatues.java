package iee.yh.common.validator.group;

import iee.yh.common.validator.TrueStatuesConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * @author yanghan
 * @date 2022/4/8
 */
@Documented
@Constraint(validatedBy = {TrueStatuesConstraintValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrueStatues {
    String message() default "{iee.yh.common.validator.group.TrueStatues.message}";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int[] vals() default {};
}
