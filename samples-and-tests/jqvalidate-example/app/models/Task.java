package models;

import java.util.List;
import java.util.Set;

import siena.*;

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
import play.data.validation.jqvalidate.HexColor;

import javax.persistence.*;

public class Task extends Model {
    @Required public String details;
    @Required @Range(max=10,min=1) public int priority;
    @Required @Email public String authorEmail;
    @Required @URL public String authorUrl;
    
    @IPv4Address public String authorIPv4;
    @IPv6Address public String authorIPv6;
    
    @Phone public String authorPhone;
    
    @Match("[A-Z]{3}") 
    public String countryAbbreviation;
    
    @Match("^[0-9]{10,19}$")
    public String creditCardNumber;
    
    @HexColor
    public String color;
}
