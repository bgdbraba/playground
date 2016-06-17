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

$('.scribeout-child').click(function(e) {
		var table = $('#example').DataTable();
		var id = $(this).data('id');
		var target = $(e.target);
        var numberOfChildren = parseInt($('#childrenOnPlayground').text()) - 1;

        jsRoutes.controllers.Application.scribeOut(id).ajax({
            success: function(data, status) {
                target.closest('tr').remove();
                $('#childrenOnPlayground').text(numberOfChildren);
            }
        });
});
