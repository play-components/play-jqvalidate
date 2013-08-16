package tags.jqvalidate;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import play.data.validation.Email;
import play.data.validation.IPv4Address;
import play.data.validation.IPv6Address;
import play.data.validation.Match;
import play.data.validation.Max;
import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Phone;
import play.data.validation.Range;
import play.data.validation.Required;
import play.data.validation.URL;
import play.data.validation.Validation;
import play.i18n.Messages;
import play.mvc.Router.ActionDefinition;
import play.mvc.Scope.Flash;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;
import play.templates.JavaExtensions;
import data.validation.jqvalidate.HexColor;
import data.validation.jqvalidate.JqValidate;

@FastTags.Namespace("jqvalid")
public class JqValidateTags extends FastTags {

  public static class ValidationData {
    public Map<String, String> rules = new HashMap<String, String>();
    public Map<String, String> messages = new HashMap<String, String>();
  }

  private static final String RULE_ATTRIBUTE_PREFIX = "data-rule-";
  private static final String MESSAGE_ATTRIBUTE_PREFIX = "data-msg-";

  public static void _form(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
    ActionDefinition actionDef = (ActionDefinition) args.get("arg");
    if (actionDef == null) {
      actionDef = (ActionDefinition) args.get("action");
    }
    // test if the action exist
    if (actionDef == null) {
      return;
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

    String classes = "play-jqvalid-form";
    if (args.containsKey("class")) {
      classes += " " + args.get("class").toString();
    }

    if (!("GET".equals(actionDef.method) || "POST".equals(actionDef.method))) {
      String separator = actionDef.url.indexOf('?') != -1 ? "&" : "?";
      actionDef.url += separator + "x-http-method-override=" + actionDef.method.toUpperCase();
      actionDef.method = "POST";
    }
    String id = args.containsKey("id") ? (String) args.get("id") : "play-jqvalid-form__" + UUID.randomUUID();
    out.println("<form class=\"" + classes + "\" id=\"" + id + "\" action=\"" + actionDef.url + "\" method=\""
        + actionDef.method.toUpperCase() + "\" accept-charset=\"utf-8\" enctype=\"" + enctype + "\" "
        + serialize(args, "action", "method", "accept-charset", "enctype") + ">");
    if (!("GET".equals(actionDef.method))) {
      _authenticityToken(args, body, out, template, fromLine);
    }
    out.println(JavaExtensions.toString(body));
    out.println("</form>");
  }

    /**
     * Build the validation data need for the jquery validation plugin
     * 
     * @param f
     *            : field
     * @return validation data
     * @throws Exception
     */
private static ValidationData buildValidationData(Field f) throws Exception {
    JqValidateTags.ValidationData validationData = new JqValidateTags.ValidationData();
    if(f == null){
	return validationData;
    }
    
    JqValidate jqValidate = f.getAnnotation(JqValidate.class);
    
    
    // ----------------------------
    // Required
    // ----------------------------
    Required required = f.getAnnotation(Required.class);
    if (required != null && isRuleAllowed(jqValidate, Required.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "required", "true");
      if (required.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "required", Messages.get(required.message()));
      }
    }

    // ----------------------------
    // Min
    // ----------------------------
    Min min = f.getAnnotation(Min.class);
    if (min != null && isRuleAllowed(jqValidate, Min.class)) {
      Integer value = (int) min.value();
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "min", value.toString());
      if (min.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "min", Messages.get(min.message(), null, min.value()));
      }
    }

    // ----------------------------
    // Max
    // ----------------------------
    Max max = f.getAnnotation(Max.class);
    if (max != null && isRuleAllowed(jqValidate, Max.class)) {
      Integer value = (int) max.value();
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "max", value.toString());
      if (max.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "max", Messages.get(max.message(), null, max.value()));
      }
    }
    // ----------------------------
    // Range
    // ----------------------------
    Range range = f.getAnnotation(Range.class);
    if (range != null && isRuleAllowed(jqValidate, Range.class)) {
      Integer valueMin = (int) range.min();
      Integer valueMax = (int) range.max();
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "range", "[" + valueMin.toString() + ", " + valueMax.toString()
          + "]");
      if (range.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "range",
            Messages.get(range.message(), null, range.min(), range.max()));
      }
    }
    // ----------------------------
    // MaxSize
    // ----------------------------
    MaxSize maxSize = f.getAnnotation(MaxSize.class);
    if (maxSize != null && isRuleAllowed(jqValidate, MaxSize.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "maxlength", Integer.toString(maxSize.value()));
      if (maxSize.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "maxlength",
            Messages.get(maxSize.message(), null, maxSize.value()));
      }
    }
    // ----------------------------
    // MinSize
    // ----------------------------
    MinSize minSize = f.getAnnotation(MinSize.class);
    if (minSize != null && isRuleAllowed(jqValidate, MinSize.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "minlength", Integer.toString(minSize.value()));
      if (minSize.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "minlength",
            Messages.get(minSize.message(), null, minSize.value()));
      }
    }

    // ----------------------------
    // URL
    // ----------------------------
    URL url = f.getAnnotation(URL.class);
    if (url != null && isRuleAllowed(jqValidate, URL.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "url", "true");
      if (url.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "url", Messages.get(url.message()));
      }
    }

    // ----------------------------
    // Email
    // ----------------------------
    Email email = f.getAnnotation(Email.class);
    if (email != null && isRuleAllowed(jqValidate, Email.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "email", "true");
      if (email.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "email", Messages.get(email.message()));
      }
    }

    // ----------------------------
    // IPv4Address
    // ----------------------------
    IPv4Address ipv4Address = f.getAnnotation(IPv4Address.class);
    if (ipv4Address != null && isRuleAllowed(jqValidate, IPv4Address.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "ipv4", "true");
      if (ipv4Address.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "ipv4", Messages.get(ipv4Address.message()));
      }
    }

    // ----------------------------
    // IPv6Address
    // ----------------------------
    IPv6Address ipv6Address = f.getAnnotation(IPv6Address.class);
    if (ipv6Address != null && isRuleAllowed(jqValidate, IPv6Address.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "ipv6", "true");
      if (ipv6Address.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "ipv6", Messages.get(ipv6Address.message()));
      }
    }

    // ----------------------------
    // Match
    // ----------------------------
    Match match = f.getAnnotation(Match.class);
    if (match != null && isRuleAllowed(jqValidate, Match.class)) {
      String rule = match.value();
      if (!rule.startsWith("^")) {
        rule = "^" + rule;
      }
      if (!rule.endsWith("$")) {
        rule = rule + "$";
      }

      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "pattern", rule);
      if (match.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "pattern",
            Messages.get(match.message(), null, match.value()));
      }
    }

    // ----------------------------
    // Phone
    // ----------------------------
    Phone phone = f.getAnnotation(Phone.class);
    // Match must not be defined
    if (phone != null && match == null && isRuleAllowed(jqValidate, Phone.class)) {
      validationData.rules
          .put(RULE_ATTRIBUTE_PREFIX + "pattern",
              "^([\\+][0-9]{1,3}([ \\.\\-]))?([\\(]{1}[0-9]{2,6}[\\)])?([0-9 \\.\\-/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$");
      if (phone.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "pattern", Messages.get(phone.message()));
      }
    }

    // ----------------------------
    // HEXColor
    // ----------------------------
    HexColor hexColor = f.getAnnotation(HexColor.class);
    // Match must not be defined
    if (hexColor != null && match == null && isRuleAllowed(jqValidate, HexColor.class)) {
      validationData.rules.put(RULE_ATTRIBUTE_PREFIX + "pattern", "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
      if (hexColor.message() != null) {
        validationData.messages.put(MESSAGE_ATTRIBUTE_PREFIX + "pattern", Messages.get(hexColor.message()));
      }
    }

    return validationData;
  }

    /**
     * Test if the rule must be applied. <br>
     * The rule will be applied if:
     * <ul>
     * <li>The tag is not defined (all rules will be applied this way we keep
     * the compatibility with the previous versions)</li>
     * <li>The rules is in the allowed list</li>
     * <li>The rules is not in the forbidden list</li>
     * </ul>
     * 
     * @param jqValidate
     *            : the jqvalidate annotation of the field
     * @param dataValidationClass
     *            : the rule to check if it must be applied
     * @return
     */
    private static boolean isRuleAllowed(JqValidate jqValidate, Class<?> dataValidationClass) {
        // if the tag is not defined, all rules will be applied
        // this way we keep the compatibility with the previous versions
        // if the rules is in the allowed list => rule is allowed<br>
        // if the rules is not in the forbidden list => rule is allowed<br>
        if (jqValidate == null || Arrays.asList(jqValidate.allowedRules()).contains(dataValidationClass)
                || !Arrays.asList(jqValidate.forbiddenRules()).contains(dataValidationClass)) {
            return true;
        }
        return false;
    }

public static void buildValidationDataString(ValidationData validationData) {
    StringBuilder result = new StringBuilder("");
    if (validationData.rules.size() > 0) {
      boolean first = true;
      for (String rule : validationData.rules.keySet()) {
        if (first) {
          first = false;
        } else {
          result.append(" ");
        }
        result.append("data-rule-");
        result.append(rule);
        result.append("=");
        result.append(validationData.rules.get(rule));
        result.append("");
      }
    }

    if (validationData.messages.size() > 0) {
      result.append(" ");
      boolean first = true;
      for (String key : validationData.messages.keySet()) {
        if (first) {
          first = false;
        } else {
          result.append(" ");
        }
        result.append("data-msg-");
        result.append(key);
        result.append("=\"");
        result.append(validationData.messages.get(key));
        result.append("\"");
      }
    }
  }

  public static void _field(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
    Map<String, Object> field = new HashMap<String, Object>();
    String _arg = args.get("arg").toString();
    field.put("name", _arg);
    field.put("id", _arg.replace('.', '_'));
    field.put("flash", Flash.current().get(_arg));
    field.put("flashArray", field.get("flash") != null && !field.get("flash").toString().isEmpty() ? field.get("flash")
        .toString().split(",") : new String[0]);
    field.put("error", Validation.error(_arg));
    field.put("errorClass", field.get("error") != null ? "hasError" : "");

    String[] pieces = _arg.split("\\.");
    Object obj = body.getProperty(pieces[0]);
    if (obj != null) {
      if (pieces.length > 1) {
        for (int i = 1; i < pieces.length; i++) {
          try {
            Field f = obj.getClass().getField(pieces[i]);
            if (i == (pieces.length - 1)) {
              field.put("validationData", buildValidationData(f));

              try {
                Method getter = obj.getClass().getMethod("get" + JavaExtensions.capFirst(f.getName()));
                field.put("value", getter.invoke(obj, new Object[0]));
              } catch (NoSuchMethodException e) {
                field.put("value", f.get(obj).toString());
              }
            } else {
              obj = f.get(obj);
            }
          } catch (Exception e) {
            // if there is a problem reading the field we don't set any value
          }
        }
      } else {
        field.put("value", obj);
      }
    }
    body.setProperty("field", field);
    body.call();
  }
}
