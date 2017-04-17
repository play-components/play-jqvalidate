package models;

import javax.persistence.Entity;

import data.validation.jqvalidate.CreditCardNumber;
import data.validation.jqvalidate.HexColor;
import data.validation.jqvalidate.JqValidate;
import play.data.validation.Email;
import play.data.validation.IPv4Address;
import play.data.validation.IPv6Address;
import play.data.validation.Match;
import play.data.validation.Phone;
import play.data.validation.Range;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

@Entity
public class Task extends Model {
  @Required
  public String details;
  @Required
  @Range(max = 10, min = 1)
  public int priority;
  @Required
  @Email
  public String authorEmail;
  @Required
  @URL
  public String authorUrl;

  @IPv4Address
  public String authorIPv4;
  @IPv6Address
  public String authorIPv6;

  @Phone
  public String authorPhone;

  @Match("[A-Z]{3}")
  public String countryAbbreviation;

  @CreditCardNumber
  public String creditCardNumber;

  @HexColor
  public String color;

  @JqValidate(forbiddenRules = { Required.class, Match.class, Email.class })
  @Required
  @Match("[A-Z]{3}")
  @Email
  public String noValidation;

  @JqValidate(allowedRules = { Required.class }, forbiddenRules = { Match.class, Email.class })
  @Required
  @Match("[A-Z]{3}")
  @Email
  public String requiredValidation;
}
