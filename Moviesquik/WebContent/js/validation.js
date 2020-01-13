/**
 *  Form validation script.
 */

$(document).ready(function()
{
	if ( $("#first_name").length )
	{
		bootstrapValidate('#first_name', 'min:3:Please enter at least 3 characters.');
		bootstrapValidate('#first_name', 'max:30:Please enter no more than 30 characters.');
		bootstrapValidate('#first_name', 'required:First name required.');
		bootstrapValidate('#first_name', 'alphanumeric:First name must contain only alphanumeric characters.');
	}
	
	if ( $("#last_name").length )
	{
		bootstrapValidate('#last_name', 'min:3:Please enter at least 3 characters.');
		bootstrapValidate('#last_name', 'max:30:Please enter no more than 30 characters.');
		bootstrapValidate('#last_name', 'required:Last name required.');
		bootstrapValidate('#last_name', 'alphanumeric:First name must contain only alphanumeric characters.');
	}
	
	if ( $("#email").length )
	{
	    bootstrapValidate('#email', 'email:Please enter a valid email address.');
	    bootstrapValidate('#email', 'required:Email address required.');
	    bootstrapValidate('#first_name', 'max:320:Please enter no more than 320 characters.');
	}
    
	if ( $("#birthday").length )
		bootstrapValidate('#birthday', 'required:Birthday is required.');
    
	if ( $("#password").length )
	{
	    bootstrapValidate('#password', 'required:Password required.');
	    bootstrapValidate('#password', 'min:8:Password must contain from 8 to 20 characters.');
	    bootstrapValidate('#password', 'max:20:Password must contain from 8 to 20 characters.');
	}
    
	if ( $("#confirm_password").length )
	{
	    bootstrapValidate('#confirm_password', 'required:Password required.');
	    bootstrapValidate('#confirm_password', 'min:8:Password must contain from 8 to 20 characters.');
	    bootstrapValidate('#confirm_password', 'max:20:Password must contain from 8 to 20 characters.');
	    bootstrapValidate('#confirm_password', 'matches:#password:Confirmed password does not match provided password.');
	}

	if ( $("#agree_checkbox").length )
		bootstrapValidate('#agree_checkbox', 'required:Please accept our Condition of use and Privacy.');
    
	if ( $("#cc-name").length )
	{
	    bootstrapValidate('#cc-name', 'min:3:Please enter at least 3 characters.');
		bootstrapValidate('#cc-name', 'max:30:Please enter no more than 30 characters.');
		bootstrapValidate('#cc-name', 'required:Credit card name required.');
		bootstrapValidate('#cc-name', 'alphanumeric:Credit card name must contain only alphanumeric characters.');
	}
	
	if ( $("#cc-number").length )
	{
		bootstrapValidate('#cc-number', 'min:16:Please enter exactly 16 digits.');
		bootstrapValidate('#cc-number', 'max:16:Please enter exactly 16 digits.');
		bootstrapValidate('#cc-number', 'required:Credit card number required.');
		bootstrapValidate('#cc-number', 'numeric:Credit card number must contain only numeric digits.');
	}
	
	if ( $("#cc-cvv").length )
	{
		bootstrapValidate('#cc-cvv', 'min:3:Please enter exactly 3 digits.');
		bootstrapValidate('#cc-cvv', 'max:3:Please enter exactly 3 digits.');
		bootstrapValidate('#cc-cvv', 'required:CVV required.');
		bootstrapValidate('#cc-cvv', 'numeric:CVV must contain only numeric digits.');
	}

});

function onSubmitUserDataForm()
{
	if ( ! $("#agree_checkbox").prop('checked'))
		{
			alert("Please accept our Condition of use and Privacy.");
			return false;
		}
	if ( $("div.invalid-feedback:visible").length )
		{
			alert("Please insert all required data in the correct format.");
			return false;
		}
	return true;
}

function onSubmitCreditCardDataForm()
{
	if ( ! $("#agree_checkbox").prop('checked'))
		{
			alert("Please accept our Terms of Use and Privacy Statement.");
			return false;
		}
	if ( $("div.invalid-feedback:visible").length )
		{
			alert("Please insert all required data in the correct format.");
			return false;
		}
	return true;
}

//  $(document).ready(function() {
//    $('#signupform').bootstrapValidator({
//        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
//        feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
//            validating: 'glyphicon glyphicon-refresh'
//        },
//        fields: {
//            first_name: {
//                validators: {
//                        stringLength: {
//                        min: 2,
//                    },
//                        notEmpty: {
//                        message: 'Please supply your first name'
//                    }
//                }
//            },
//             last_name: {
//                validators: {
//                     stringLength: {
//                        min: 2,
//                    },
//                    notEmpty: {
//                        message: 'Please supply your last name'
//                    }
//                }
//            },
//            email: {
//                validators: {
//                    notEmpty: {
//                        message: 'Please supply your email address'
//                    },
//                    emailAddress: {
//                        message: 'Please supply a valid email address'
//                    }
//                }
//            },
//            phone: {
//                validators: {
//                    notEmpty: {
//                        message: 'Please supply your phone number'
//                    },
//                    phone: {
//                        country: 'US',
//                        message: 'Please supply a vaild phone number with area code'
//                    }
//                }
//            },
//            address: {
//                validators: {
//                     stringLength: {
//                        min: 8,
//                    },
//                    notEmpty: {
//                        message: 'Please supply your street address'
//                    }
//                }
//            },
//            city: {
//                validators: {
//                     stringLength: {
//                        min: 4,
//                    },
//                    notEmpty: {
//                        message: 'Please supply your city'
//                    }
//                }
//            },
//            state: {
//                validators: {
//                    notEmpty: {
//                        message: 'Please select your state'
//                    }
//                }
//            },
//            zip: {
//                validators: {
//                    notEmpty: {
//                        message: 'Please supply your zip code'
//                    },
//                    zipCode: {
//                        country: 'US',
//                        message: 'Please supply a vaild zip code'
//                    }
//                }
//            },
//            comment: {
//                validators: {
//                      stringLength: {
//                        min: 10,
//                        max: 200,
//                        message:'Please enter at least 10 characters and no more than 200'
//                    },
//                    notEmpty: {
//                        message: 'Please supply a description of your project'
//                    }
//                    }
//                }
//            }
//        });
//});


