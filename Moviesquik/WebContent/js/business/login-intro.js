/**
 * 
 */

const INTRO_IMAGES_COUNT = 3;
const INTRO_IMAGES_CHANGE_INTERVAL = 5000;  // expressed in milliseconds
const INTRO_IMAGE_PREFIX_FILENAME = 'res/drawable/business_intro_';
const INTRO_IMAGE_POSTFIX_FILENAME = '.jpg';
const INTRO_IMAGE_TAG_ID = '#business-background-img';

var currentImageIndex = 0;

function setIntroImageChangeTimeout() 
{
	setTimeout( changeIntroImage, INTRO_IMAGES_CHANGE_INTERVAL );
}

function changeIntroImage() 
{
	currentImageIndex++;
	if ( currentImageIndex >= INTRO_IMAGES_COUNT )
		currentImageIndex = 0;
	
	$(INTRO_IMAGE_TAG_ID).fadeOut('slow', function() {
		$(INTRO_IMAGE_TAG_ID)
			.attr('src', INTRO_IMAGE_PREFIX_FILENAME + currentImageIndex.toString() + INTRO_IMAGE_POSTFIX_FILENAME);
		$(INTRO_IMAGE_TAG_ID).fadeIn('slow', function() {
			setIntroImageChangeTimeout();
		})
	});
}

$(document).ready(function() 
	{
		setIntroImageChangeTimeout();
	}
);