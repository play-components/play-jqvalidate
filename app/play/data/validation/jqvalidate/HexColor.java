package play.data.validation.jqvalidate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.configuration.annotation.Constraint;

/**
 * Validate HEX color code.
 *
 * Valid HEX code is :
 * Prefixed with a # character and composed of 6 or 3 characters ([A-F]|[0-9]) 
 * Regex code used : ^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$
 * @author Nicolas Forney [nicolas@eforney.com]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(checkWith = HexColorCheck.class)
public @interface HexColor {
    String message() default HexColorCheck.mes;
}
