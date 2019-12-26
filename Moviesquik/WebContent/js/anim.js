/**
 * 
 */

$(document).ready( function()
	{
		var box1 = document.getElementById("cont1");	
		var box2 = document.getElementById("cont2");
		var box3 = document.getElementById("cont3");
		
		var pos1 = 0;
		var pos2 = 340;
		var pos3 = 680;
		
		box1.style.left = pos1 + 'px';
		box2.style.left = pos2 + 'px';
		box3.style.left = pos3 + 'px';
		
		setInterval(() => {
			
			if (pos1 <= -300) pos1 = screen.width;
			if (pos2 <= -300) pos2 = screen.width;
			if (pos3 <= -300) pos3 = screen.width;

			pos1 -= 1;
			pos2 -= 1;
			pos3 -= 1;
			
			box1.style.left = pos1 + 'px';
			box2.style.left = pos2 + 'px';
			box3.style.left = pos3 + 'px';
		
		}, 20);
	});