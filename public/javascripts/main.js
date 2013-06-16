$('.submit-modal').on('click', function(event) {
	$(this).parents('.modal').children('.modal-body').children('form').submit();
});

 // dynamic navbar element activation
$(function() {
	  $('li a[href^="/' + location.pathname.split("/")[1] + '/"]').parent().addClass('active');
	  $('li a[href="/' + location.pathname.split("/")[1] + '"]').parent().addClass('active');
});

$('.generate-password').on('click', function(event) {
	jsRoutes.controllers.Application.getGeneratedPassword().ajax({
		success: function(data, status) {
			$('#password').val(data);
		}
	});
});