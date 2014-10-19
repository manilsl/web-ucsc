$(document).ready(function() {
	$(function() {
		$("#search").autocomplete({

			source : function(request, response) {
				$.ajax({
					url : "SearchController",
					type : "POST",
					data : {
						term : request.term
					},
					dataType : "json",
					success : function(data) {
						response(data);
					}
				});
			}
		});
	});
});
$request = $.ajax({
	type : "POST",
	url : "calendar_func.php",
	data : {
	"method" : "2",
	"year" : year,
	"from" : currentMonth
	}
	}).done(function(msg) {
	if (!validateAjaxResponse(msg)) {
	alert("ERROR");
	}
	//addEvents(msg);

	var id = 'evt-' + year + '-' + currentMonth;
	if (!idExists('#evt-data-container', "#" + id)) {
	$('#evt-data-container').prepend(msg);

	} else {
	//Need to check if events has been alterd if so update with the new ones else keep it as it is
	hideTitleLoading();
	}
	});