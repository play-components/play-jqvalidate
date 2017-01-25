package data.validation.jqvalidate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.configuration.annotation.Constraint;

/**
 * Validate a credit card number.
 *
 * @author Alexandre Chatiron [alexandre.chatiron@gmail.com]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(checkWith = CreditCardNumberCheck.class)
public @interface CreditCardNumber {
    String message() default CreditCardNumberCheck.mes;
    
    boolean checkIIN() default true;
    boolean checkValidation() default true;
}
