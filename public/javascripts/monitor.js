// ------------ TIMER ------------ //

var timeLeft = new Array();
var checkTest = new Array();
var checkDone = 0;
var timer;

function startTimer() {
	var times = $('.timeLeft');
	for(var i = 0; i < times.length; i++) {
		timeLeft[i] = $(times.get(i)).html();
	}
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
	var times = $('.timeLeft');
	if(checkDone == timeLeft.length) {
		stopTimer();
	} else {
		for(var i = 0; i < timeLeft.length; i++) {
			var thisTime = timeLeft[i];
			if(thisTime > -1) {
				var hours = Math.floor(thisTime/3600).toString();
				var minutes = (Math.floor(thisTime/60)%60).toString();
				var seconds = (thisTime%60).toString();
				var time = pad(hours,2) + ':' + pad(minutes,2) + ':' + pad(seconds,2);
				$(times.get(i)).html(time);
				timeLeft[i] -= 1;
			} else if(checkTest[i] != 1){
				checkDone += 1;
				checkTest[i] = 1;
			}
		}
	}
}

//------------------------------- //

//---------- INITIATE MONITOR ----------//

$(document).ready(function() {
	
	startTimer();
	
});

//--------------------------------------//



//---------- GRANT TIME ----------//

$('tbody').on('click', '.grant-time', function(event) {
	var input = $(event.target).parents('p').children('.grant-input');
	var time = input.val();
	jsRoutes.controllers.MonitorContestController.grantTime(input.attr('grant-target'), time).ajax({
		success: function(data, status) {
			if(isNaN(data)) {
				input.val(data);
			} else {
				location.reload();
			}
		}
	});
});

//--------------------------------//