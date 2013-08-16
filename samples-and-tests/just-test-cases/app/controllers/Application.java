package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Query;

import models.Element;
import models.Task;
import play.Logger;
import play.data.validation.Valid;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Util;

public class Application extends Controller {

    public static void index() {
        Task task = new Task();
        render(task); 
    }
    
    public static void save(@Valid Task task) {
	flash.clear();
	if (validation.hasErrors()) {
	    flash.error("Data KO!!");
	} else {
	    flash.success("Data OK!!");
	}
	render("Application/index.html", task);
    }
    
    public static void annotationJqValidate() {
	Element task = new Element();
        render(task); 
    }
    
    public static void saveElement(@Valid Element task) {
	flash.clear();
	if (validation.hasErrors()) {
	    flash.error("Data KO!!");
	} else {
	    flash.success("Data OK!!");
	}
        render("Application/annotationJqValidate.html", task);
    }
}
