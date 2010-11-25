package controllers;

import play.*;
import play.data.validation.Valid;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        Task task = new Task();
        render(task); 
    }
    
    public static void save(@Valid Task task) {
        render("Application/index.html", task);
    }

}
