/*!
 * jQuery Validation Plugin - addon functions
 *
 *
 * Copyright (c) 2014 Alexandre Chatiron
 * Released under the MIT license
 */
(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "./jquery.validate"], factory );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

// Accept a value from a file input based on a required mimetype
$.validator.addMethod("maxFileSize", function (val, element, params) {
    if (element.files && element.files.length) {
        for(var i = 0, len = element.files.length; i < len; i++){
            var size = element.files[i].size;
            if (size > params){
                return false;
            }
        }
    }
    return true;
}, "Please enter a smaller file.");
          

}));