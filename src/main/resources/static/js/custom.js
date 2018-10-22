// JavaScript Document for Squarecut

$(window).load(function() {

//Image Slider
$('.slider-image').flexslider({
    	animation: "fade",
		slideshowSpeed: 4000,
		animationDuration: 600,
    	controlNav: true,
    	keyboardNav: true,
    	directionNav: false,
		pauseOnHover: true,
		pauseOnAction: true  
	
});

//Text + Image Slider
$('.slider-text-image').flexslider({
    	animation: "fade",
		slideshowSpeed: 4000,
		animationDuration: 600,
    	controlNav: true,
    	keyboardNav: true,
    	directionNav: false,
		pauseOnHover: true,
		pauseOnAction: true  
	
});

//testimonial slider
$('.slider-testimonial').flexslider({
    	animation: "fade",
		slideshowSpeed: 7000,
		animationDuration: 200,
    	controlNav: true,
    	keyboardNav: true,
    	directionNav: false,
		pauseOnHover: true,
		pauseOnAction: true    
});	

});       

$('.sliderTab').click(function (e) {
   location.reload() 
	
})
   
$(document).ready(function(){

//prettyPhoto						
$('a[data-rel]').each(function() {
    $(this).attr('rel', $(this).data('rel'));
});
$("a[rel^='prettyPhoto[gallery1]']").prettyPhoto({
	animation_speed: 'fast',
	slideshow: 5000,
	autoplay_slideshow: false,
	opacity: 0.80,
	show_title: false,
	theme: 'pp_default', /* light_rounded / dark_rounded / light_square / dark_square / facebook */
	overlay_gallery: false,
	social_tools: false,
	changepicturecallback: function(){
	var $pp = $('.pp_default');
	if( parseInt( $pp.css('left') ) < 0 ){
	$pp.css('left', 0 );
	}
	}
});					

//Contact form
$(function() {
	var v = $("#contactform").validate({
	submitHandler: function(form) {
	$(form).ajaxSubmit({
	target: "#result",
	clearForm: true
	});
	}
	});
});
//To clear form field on page refresh
$('#contactform #message').val('');

//Subscribe form
$(function() {
	var v = $(".subform1").validate({
	submitHandler: function(form) {
	$(form).ajaxSubmit({
	target: ".subresult1",
	clearForm: true
	});
	}
	});
});

//Subscribe form
$(function() {
	var v = $(".subform2").validate({
	submitHandler: function(form) {
	$(form).ajaxSubmit({
	target: ".subresult2",
	clearForm: true
	});
	}
	});
});


});
	
// Below scripts do not require modification

/* IE Image Resizing - by Ethan Marcotte - http://unstoppablerobotninja.com/entry/fluid-images/ */
var imgSizer = {
	Config : {
		imgCache : []
		,spacer : "../images/spacer.gif"
	}

	,collate : function(aScope) {
		var isOldIE = (document.all && !window.opera && !window.XDomainRequest) ? 1 : 0;
		if (isOldIE && document.getElementsByTagName) {
			var c = imgSizer;
			var imgCache = c.Config.imgCache;

			var images = (aScope && aScope.length) ? aScope : document.getElementsByTagName("img");
			for (var i = 0; i < images.length; i++) {
				images[i].origWidth = images[i].offsetWidth;
				images[i].origHeight = images[i].offsetHeight;

				imgCache.push(images[i]);
				c.ieAlpha(images[i]);
				images[i].style.width = "100%";
			}

			if (imgCache.length) {
				c.resize(function() {
					for (var i = 0; i < imgCache.length; i++) {
						var ratio = (imgCache[i].offsetWidth / imgCache[i].origWidth);
						imgCache[i].style.height = (imgCache[i].origHeight * ratio) + "px";
					}
				});
			}
		}
	}

	,ieAlpha : function(img) {
		var c = imgSizer;
		if (img.oldSrc) {
			img.src = img.oldSrc;
		}
		var src = img.src;
		img.style.width = img.offsetWidth + "px";
		img.style.height = img.offsetHeight + "px";
		img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizingMethod='scale')"
		img.oldSrc = src;
		img.src = c.Config.spacer;
	}

	// Ghettomodified version of Simon Willison's addLoadEvent() -- http://simonwillison.net/2004/May/26/addLoadEvent/
	,resize : function(func) {
		var oldonresize = window.onresize;
		if (typeof window.onresize != 'function') {
			window.onresize = func;
		} else {
			window.onresize = function() {
				if (oldonresize) {
					oldonresize();
				}
				func();
			}
		}
	}
}



