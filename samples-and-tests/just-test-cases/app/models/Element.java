package models;

import java.util.List;
import java.util.Set;

import play.*;
import play.data.validation.Email;
import play.data.validation.IPv4Address;
import play.data.validation.IPv6Address;
import play.data.validation.Match;
import play.data.validation.Max;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Phone;
import play.data.validation.Range;
import play.data.validation.Required;
import play.data.validation.URL;
import play.data.validation.Valid;
import play.db.jpa.Model;
import data.validation.jqvalidate.HexColor;
import data.validation.jqvalidate.JqValidate;

import javax.persistence.*;

@Entity
public class Element extends Model {
    
    @JqValidate(forbiddenRules ={Required.class, Match.class, Email.class})
    @Required
    @Match("[A-Z]{3}")
    @Email
    public String noValidation;
    
    @JqValidate(allowedRules = {Required.class}, forbiddenRules ={ Match.class})
    @Required
    @Match("[A-Z]{3}")
    public String requiredValidation;
}
