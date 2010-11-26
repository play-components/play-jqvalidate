package tags.jqvalidate;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import play.Logger;
import play.data.validation.*;
import play.i18n.Messages;
import play.libs.I18N;
import play.mvc.Router.ActionDefinition;
import play.mvc.Scope.Flash;
import play.templates.FastTags;
import play.templates.JavaExtensions;
import play.templates.GroovyTemplate.ExecutableTemplate;

@FastTags.Namespace("jqvalid")
public class JqValidateTags extends FastTags {
    
    /**
     * Generates a html form element linked to a controller action, backed by client-side validation
     * @param args tag attributes
     * @param body tag inner body
     * @param out the output writer
     * @param template enclosing template
     * @param fromLine template line number where the tag is defined
     */
    public static void _form(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
        ActionDefinition actionDef = (ActionDefinition) args.get("arg");
        if (actionDef == null) {
            actionDef = (ActionDefinition) args.get("action");
        }
        String enctype = (String) args.get("enctype");
        if (enctype == null) {
            enctype = "application/x-www-form-urlencoded";
        }
        if (actionDef.star) {
            actionDef.method = "POST"; // prefer POST for form ....
        }
        if (args.containsKey("method")) {
            actionDef.method = args.get("method").toString();
        }
        if (!("GET".equals(actionDef.method) || "POST".equals(actionDef.method))) {
            String separator = actionDef.url.indexOf('?') != -1 ? "&" : "?";
            actionDef.url += separator + "x-http-method-override=" + actionDef.method.toUpperCase();
            actionDef.method = "POST";
        }
        String id = args.containsKey("id") ? (String)args.get("id") : "play-jqvalid-form__"+UUID.randomUUID(); 
        out.println("<form class=\"play-jqvalid-form\" id=\""+ id +"\" action=\"" + actionDef.url + "\" method=\"" + actionDef.method.toUpperCase() + "\" accept-charset=\"utf-8\" enctype=\"" + enctype + "\" " + serialize(args, "action", "method", "accept-charset", "enctype") + ">");
        if (!("GET".equals(actionDef.method))) {
            _authenticityToken(args, body, out, template, fromLine);
        }
        out.println(JavaExtensions.toString(body));
        out.println("</form>");
    }
    
    private static String buildValidationDataString(Field f){
        StringBuilder result = new StringBuilder("{");
        List<String> rules = new ArrayList<String>();
        Map<String,String> messages = new HashMap<String,String>();
        try{
        Required required = f.getAnnotation(Required.class);
        if(required != null){
            rules.add("required:true");
            if(required.message() != null){
                messages.put("required", Messages.get(required.message()));
            }
        }
        Min min = f.getAnnotation(Min.class);
        if(min != null){
            rules.add("min:"+new Double(min.value()).toString());
            if(min.message() != null){
                messages.put("min", Messages.get(min.message(), null, min.value()));
            }
        }
        Max max = f.getAnnotation(Max.class);
        if(max != null){
            rules.add("max:"+new Double(max.value()).toString());
            if(max.message() != null){
                messages.put("max", Messages.get(max.message(), null, max.value()));
            }
        }
        Range range = f.getAnnotation(Range.class);
        if(range != null){
            rules.add("range:["+new Double(range.min()).toString()+", "+new Double(range.max()).toString()+"]");
            if(range.message() != null){
                messages.put("range", Messages.get(range.message(), null, min.value(), max.value()));
            }
        }
        MaxSize maxSize = f.getAnnotation(MaxSize.class);
        String t = maxSize == null ? "null" : "not null";
        if(maxSize != null){
            rules.add("maxlength:"+new Integer(maxSize.value()).toString());
            if(maxSize.message() != null){
                messages.put("maxlength", Messages.get(maxSize.message(), null, maxSize.value()));
            }
        }
        MinSize minSize = f.getAnnotation(MinSize.class);
        if(minSize != null){
            rules.add("minlength:"+new Integer(minSize.value()).toString());
            if(minSize.message() != null){
                messages.put("minlength", Messages.get(minSize.message(), null, minSize.value()));
            }
        }
        URL url = f.getAnnotation(URL.class);
        if(url != null){
            rules.add("url:true");
            if(url.message() != null){
                messages.put("url", Messages.get(url.message()));
            }
        }
        Email email = f.getAnnotation(Email.class);
        if(email != null){
            rules.add("email:true");
            if(email.message() != null){
                messages.put("email", Messages.get(email.message()));
            }
        }
        }catch(Exception e){
            Logger.info(e.getMessage());
        }
        if(rules.size() > 0){
            boolean first = true;
            for(String rule : rules){
                if(first){
                    first = false;
                }else{
                    result.append(",");
                }
                result.append(rule);
            }
        }
        if(messages.size() > 0){
            result.append(",messages:{");
            boolean first = true;
            for(String key : messages.keySet()){
                if(first){
                    first = false;
                }else{
                    result.append(",");
                }
                result.append("\"");
                result.append(key);
                result.append("\"");
                result.append(":");
                result.append("\"");
                try{
                    result.append(messages.get(key));
                }catch(Exception e){
                }
                result.append("\"");
            }
            result.append("}");
        }
        result.append("}");
        return result.toString();
    }
    
    public static void _field(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
        Map<String,Object> field = new HashMap<String,Object>();
        String _arg = args.get("arg").toString();
        field.put("name", _arg);
        field.put("id", _arg.replace('.','_'));
        field.put("flash", Flash.current().get(_arg));
        field.put("flashArray", field.get("flash") != null && !field.get("flash").toString().isEmpty() ? field.get("flash").toString().split(",") : new String[0]);
        field.put("error", Validation.error(_arg));
        field.put("errorClass", field.get("error") != null ? "hasError" : "");
        
        
        
//        field.put("validationData", );
        String[] pieces = _arg.split("\\.");
        Object obj = body.getProperty(pieces[0]);
        if(obj != null){
            if(pieces.length > 1){
                for(int i = 1; i < pieces.length; i++){
                    try{
                        Field f = obj.getClass().getField(pieces[i]);
                        if(i == (pieces.length-1)){
                            field.put("validationData", buildValidationDataString(f));
                            
                            try{
                                Method getter = obj.getClass().getMethod("get"+JavaExtensions.capFirst(f.getName()));
                                field.put("value", getter.invoke(obj, new Object[0]));
                            }catch(NoSuchMethodException e){
                                field.put("value",f.get(obj).toString());
                            }
                        }else{
                            obj = f.get(obj);
                        }
                    }catch(Exception e){
                        // if there is a problem reading the field we dont set any value
                    }
                }
            }else{
                field.put("value", obj);
            }
        }
        body.setProperty("field", field);
        body.call();
    }
    
}
