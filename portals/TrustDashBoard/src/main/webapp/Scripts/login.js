$(function() {
	$('#loginForm').submit(function(){
		var valid1 = true;
		var valid2 = true;
		$('.validationErrorDiv').each(function() {
			$(this).remove();
		});
		
	});
	$('#userNameValue').focus();
});

function validateValue(inputID){
	if ($.trim($('#'+inputID).val()) == "") {
		$('#'+inputID).parent().parent().find('td:eq(2)').append(validationDiv);
		return false;
	}
	return true;
}

function getRegisterUserPage(){
    $('#mainContainer').prepend(disabledDiv);
    sendHTMLAjaxRequest(true, "getView/getRegisterPage.htm", null,registerUserPageSuccess , null);
}

function registerUserPageSuccess(responseHTML){
    $('#mainContainer').parent().html(responseHTML);
}