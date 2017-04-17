package controllers;

import models.Element;
import models.Task;
import play.data.validation.Valid;
import play.mvc.Controller;

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
    Element element = new Element();
    render(element);
  }

  public static void saveElement(@Valid Element element) {
    if (element != null) {
      validation.valid("element.task", element.task);
    }
    if (validation.hasErrors()) {
      validation.keep();
      params.flash();
      flash.error("Data KO!!");
    } else {
      flash.success("Data OK!!");
    }
    render("Application/annotationJqValidate.html", element);
  }
}
