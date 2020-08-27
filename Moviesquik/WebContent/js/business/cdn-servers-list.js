/**
 * 
 */

const ON_SERVER_KEY_COPY_ALERT_TEXT = 'Server Key copied to clipboard.';

function onServerKeyCopy(serverKeySpan) 
{
	if ( document.body.createTextRange )
	{
		var range = document.body.createTextRange();
		range.moveToElementText(serverKeySpan[0]);
		range.select();
		document.execCommand("copy");
		alert(ON_SERVER_KEY_COPY_ALERT_TEXT);
	}
	else if ( window.getSelection )
	{
		var selection = window.getSelection();
		var range = document.createRange();
		range.selectNodeContents(serverKeySpan[0]);
		selection.removeAllRanges();
		selection.addRange(range);
		document.execCommand("copy");
		alert(ON_SERVER_KEY_COPY_ALERT_TEXT);
	}
}

$(document).ready(function() 
	{
		$(document).on('click', '.server-key-copy-btn', function() {
			onServerKeyCopy($(this).closest('td').find('.server-key'));
		});
	}
);