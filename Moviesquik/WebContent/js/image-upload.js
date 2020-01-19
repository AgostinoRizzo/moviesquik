/**
 * 
 */

var IMG_EXTENSIONS = ['image/png', 'image/jpg', 'image/jpeg', 'image/gif'];

function checkExtention( type ) {
	for ( ext in IMG_EXTENSIONS )
		if ( type == IMG_EXTENSIONS[ext] )
			return true;
	return false;		
}

function readURL(input) {
    if (input.files && input.files[0]) {
    	
    	if ( checkExtention(input.files[0].type) )
    	{
	    	var reader = new FileReader();
	
	        reader.onload = function (e) {
	        	
			$(".custom-file-label").html(input.files[0].name);
			$(".input-img-preview").attr("src", e.target.result);
			$(".profile-img-input").attr("value", e.target.result);
			
	        };
	        
	        reader.readAsDataURL(input.files[0]);
    	}
    	else
    		{
    			alert("Invalid image extenstion. Please choose from " + IMG_EXTENSIONS.join(', '));
    			$(".profile-img-edit-form").trigger("reset");
    		}
    }
}

$(document).ready(function() {
  $('input[type="file"]').on("change", function() {
    readURL(this);
    
  });
});