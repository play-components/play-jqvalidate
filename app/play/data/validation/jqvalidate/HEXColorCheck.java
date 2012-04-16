package play.data.validation.jqvalidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

/**
 * Validate HEX color code.
 *
 * Valid HEX code is :
 * Prefixed with a # character and composed of 6 or 3 characters ([A-F]|[0-9]) 
 * Regex code used : ^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$
 * @author Nicolas Forney [nicolas@eforney.com]
 */
public class HEXColorCheck extends AbstractAnnotationCheck<HEXColor> {

	final static String mes = "validation.hexcolor";
	private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
	private static final Pattern pattern = Pattern.compile(HEX_PATTERN);
	
	@Override
    public void configure(HEXColor hexColor) {
        setMessage(hexColor.message());
    }
	
	@Override
	public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) throws OValException {
		if (value == null || value.toString().length() == 0) {
	            return true;
	    }
		Matcher matcher = pattern.matcher(value.toString());
		return matcher.matches();
	}

}