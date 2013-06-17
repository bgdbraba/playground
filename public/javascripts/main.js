$('.submit-modal').on('click', function(event) {
	$(this).parents('.modal').children('.modal-body').children('form').submit();
});

 // dynamic navbar element activation
$(function() {
	  $('li a[href^="/' + location.pathname.split("/")[1] + '/"]').parent().addClass('active');
	  $('li a[href="/' + location.pathname.split("/")[1] + '"]').parent().addClass('active');
});

$('#teacherSchoolSelect').on('change', function(event) {
	jsRoutes.controllers.ClassController.getClassesForSchool($('#teacherSchoolSelect option:selected').val()).ajax({
		success: function(data, status) {
			$('#teacherClassSelect').html(data);
		}
	});
});

$('#teacherClassSelect').on('change', function(event) {
	jsRoutes.controllers.StudentController.getStudentsForClass($('#teacherClassSelect option:selected').val()).ajax({
		success: function(data, status) {
			$('#teacherStudentSelect').html(data);
		}
	});
});


$('.languageSelect').on('click', function(event) {
	jsRoutes.controllers.Application.setLanguage($(event.target).attr('data-lang')).ajax({
		success: function(data, status) {
			location.reload();
		}
	});
	event.preventDefault();
});

$(".popover-with-html")
.popover({html: true})
.click(function(e) {
  e.preventDefault()
})

$('.show-feedback').on('click', function(event) {
	jsRoutes.controllers.PlayContestController.getFeedback($(event.target).attr('id')).ajax({
		success: function(data, status) {
			$('#feedback-container').html(data);
		}
	});
	event.preventDefault();
});

$('.show-question-iframe').on('click', function(event) {
	jsRoutes.controllers.QuestionController.viewQuestion($(event.target).attr('id')).ajax({
		success: function(data, status) {
			$('#question-container').html(data);
		}
	});
});

$('.check-student').on('click', function(event) {
	jsRoutes.controllers.StudentController.getStudentCheck($('#bebrasId').val()).ajax({
		success: function(data, status) {
			$('#idcheck-container').html(data);
		}
	});
});

$('.generate-password').on('click', function(event) {
	jsRoutes.controllers.Application.getGeneratedPassword().ajax({
		success: function(data, status) {
			$('#password').val(data);
		}
	});
});

// subnav fixed when scroll
//$(document).scroll(function(){
//    // If has not activated (has no attribute "data-top"
//    if (!$('.subnav').attr('data-top')) {
//        // If already fixed, then do nothing
//        if ($('.subnav').hasClass('subnav-fixed')) return;
//        // Remember top position
//        var offset = $('.subnav').offset()
//        $('.subnav').attr('data-top', offset.top);
//    }
//
//    if ($('.subnav').attr('data-top') - $('.subnav').outerHeight() <= $(this).scrollTop())
//        $('.subnav').addClass('subnav-fixed');
//    else
//        $('.subnav').removeClass('subnav-fixed');
//});
