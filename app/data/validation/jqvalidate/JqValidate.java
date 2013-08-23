package data.validation.jqvalidate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.sf.oval.configuration.annotation.Constraint;

/**
 * This field must not be considered by jqValidate.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface JqValidate {

    /**
     * (Optional) The validation rules that must be applied to
     * the target.
     * <p> Defaults to all rules being applied.
     */
    Class[] allowedRules() default {};
    
    /**
     * (Optional) The validation rules that must not be applied to
     * the target.
     * <p> Defaults to no rules will not being applied.
     */
    Class[] forbiddenRules() default {};
}

