/*
 * play-jqvalidate JavaScript component
 *
 * 
 */
(function ($) {
	$.metadata.setType('html5');
	$(document).ready(function () {
		$('.play-jqvalid-form').validate({
			meta : 'validate'
		});
	});
})(jQuery);
