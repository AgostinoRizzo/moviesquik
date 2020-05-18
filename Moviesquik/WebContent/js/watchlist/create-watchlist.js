/**
 * 
 */


$(document).ready( function() 
{
	$("#new-watchlist-btn").click( function() 
	{
		$("#new-watchlist-form").removeClass("d-none");
		$(this).addClass("d-none");
	});
	
	$("#create-watchlist-btn").click( function() 
	{
		var form = $(this).closest("#new-watchlist-form");
		var nameElem = $(form).find("#name");
		var descriptionElem = $(form).find("#description-text");
		
		if ( $("div.invalid-feedback:visible").length || nameElem.val().length == 0)
		{
			alert("Please insert all required data in the correct format.");
			return false;
		}
		
		$(form).submit();
	});
	
	$("#cancel-watchlist-btn").click( function() 
	{
		var form = $(this).closest("#new-watchlist-form");
		$(form).find("#name").val("");
		$(form).find("#description-text").val("");
		
		$(form).addClass("d-none");
		$("#new-watchlist-btn").removeClass("d-none");
	});
	
	if ( $("#create-watchlist-modal").length )
	{
		$("#create-watchlist-modal").modal('show');
	}
	
	// fields validation
	if ( $("#name").length )
	{
		bootstrapValidate('#name', 'min:3:Please enter at least 3 characters.');
		bootstrapValidate('#name', 'max:30:Please enter no more than 30 characters.');
		bootstrapValidate('#name', 'required:Watch List name required.');
		bootstrapValidate('#name', 'alphanumeric:Watch List name must contain only alphanumeric characters.');
	}
});