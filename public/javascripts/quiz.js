// ------------ TIMER ------------ //

var timeLeft;
var timer;

function startTimer() {
	timeLeft = $('#timeLeft').html();
	timer = setInterval(countDown, 1000);
}

function stopTimer() {
	clearInterval(timer);
}

function pad(num, size) {
    var s = "0" + num;
    return s.substr(s.length-size);
}

function countDown() {
	var hours = Math.floor(timeLeft/3600).toString();
	var minutes = (Math.floor(timeLeft/60)%60).toString();
	var seconds = (timeLeft%60).toString();
	var time = pad(hours,2) + ':' + pad(minutes,2) + ':' + pad(seconds,2);
	$('#timer').html(time);
	if(timeLeft == 0) {
		stopTimer();
		window.location.href = jsRoutes.controllers.PlayContestController.submit().url;
	}
	timeLeft = timeLeft-1;
}

//------------------------------- //

//---------- INITIATE QUIZ ----------//

$(document).ready(function() {
	
	startTimer();
	$('.select-question').first().parent().addClass('active');
	
});

//-----------------------------------//


//---------- QUESTION HANDLING ----------//

$('.select-question').on('click', function(event) {
	jsRoutes.controllers.PlayContestController.getQuestionPage($(event.target).attr('id')).ajax({
		success: function(data, status) {
			$('#question-container').html(data);
			$('.select-question').parent().removeClass('active');
			$(event.target).parent().addClass('active');
		}
	});
	event.preventDefault();
});

$('.select-next-question').on('click', function(event) {
	var list = $('.question-selection').children('li');
	var index = list.index('.active');
	$(list.get((index+1)%list.length)).children('a').click();
});

$('.select-previous-question').on('click', function(event) {
	var list = $('.question-selection').children('li');
	var index = list.index('.active');
	$(list.get(index-1)).children('a').click();
});

$('#question-container').on('submit', 'form', function(event) {
	var $form = $(this);
	$.ajax({
		data: $form.serialize(),
		type: $form.attr('method'),
		url: $form.attr('action'),
		success: function(data,status) {
			//
		}
	});

    event.preventDefault();
});

$('#question-container').on('click', '.select-answer', function(event) {
	$(event.target).parents('form').submit();
});

$('#question-container').on('change', '.text-answer', function(event) {
	$(this).parents('form').submit();
});

//---------------------------------------//


//---------- FEEDBACK HANDLING ----------//

$('.show-feedback').on('click', function(event) {
	jsRoutes.controllers.PlayContestController.getFeedback($(event.target).attr('id')).ajax({
		success: function(data, status) {
			$('#feedback-container').html(data);
		}
	});
	event.preventDefault();
});

//---------------------------------------//