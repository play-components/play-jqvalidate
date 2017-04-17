package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;
import data.validation.jqvalidate.JqValidate;

@Entity
public class Element extends Model {

  @JqValidate(forbiddenRules = { Required.class, Match.class, Email.class })
  @Required
  @Match("[A-Z]{3}")
  @Email
  public String noValidation;

  @JqValidate(allowedRules = { Required.class }, forbiddenRules = { Match.class })
  @Required
  @Match("[A-Z]{3}")
  public String requiredValidation;

  @ManyToOne
  public Task task;

}
