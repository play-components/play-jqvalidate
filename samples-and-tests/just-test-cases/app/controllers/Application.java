package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Query;

import play.Logger;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;
import play.mvc.Controller;
import play.mvc.Util;

public class Application extends Controller {

  public static void index() {
    render();
  }

  
}
