package data.validation.jqvalidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

/**
 * Validate a credit card number.
 * 
 * @author Alexandre Chatiron [alexandre.chatiron@gmail.com]
 */
public class CreditCardNumberCheck extends AbstractAnnotationCheck<CreditCardNumber> {

  protected final static String mes = "validation.creditcardnumber";

  /**
   * The common number pattern for all credit cards: Accept only spaces, digits and dashes
   */
  private static final String COMMON_PATTERN = "^[0-9 \\-]{13,19}$";
  private static final Pattern pattern = Pattern.compile(COMMON_PATTERN);

  @Override
  public void configure(CreditCardNumber creditCardNumber) {
    setMessage(creditCardNumber.message());
  }

  @Override
  public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator)
      throws OValException {
    if (value == null || value.toString().length() == 0) {
      return true;
    }

    // Check format
    Matcher matcher = pattern.matcher(value.toString());
    if (matcher.matches()) {
      return false;
    }

    String creditCardNumber = (String) value;

    // Keep only digit
    creditCardNumber = creditCardNumber.replaceAll("\\D", "");

    return checkWithLuhn(creditCardNumber);
  }

  /**
   * Check if a number passes the LUHN algorithm
   * 
   * @param cardNumber
   *          The cardnumber to check
   * @return True if the number passes the LUHN algorithm, false otherwise
   */
  protected static boolean checkWithLuhn(String cardNumber) {
    if (cardNumber != null) {
      // LUHN Algorithm implementation from Wikipedia
      // http://en.wikipedia.org/wiki/Luhn_algorithm
      final int[][] sumTable = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 } };
      int sum = 0;
      int flip = 0;

      for (int i = cardNumber.length() - 1; i >= 0; i--) {
        sum += sumTable[flip++ & 0x1][Character.digit(cardNumber.charAt(i), 10)];
      }

      return sum % 10 == 0;
    }

    return false;
  }
}