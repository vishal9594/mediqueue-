(function ($) {
    "use strict";
    var windowOn = $(window);

    /*-----------------------------------------------------------------------------------
        Template Name: Medilix - Healthcare & Medical Bootstrap HTML5 Template
        Author: RRDevs
        Support: https://support.rrdevs.net
        Description: Medilix - Healthcare & Medical Bootstrap HTML5 Template.
        Version: 1.0
        Developer: Sabbir Ahmmed (https://github.com/ahmmedsabbirbd)
    -----------------------------------------------------------------------------------

     */
   /*======================================
   Data Css js
   ========================================*/
    $("[data-background]").each(function() {
        $(this).css(
            "background-image",
            "url( " + $(this).attr("data-background") + "  )"
        );
    });

    $("[data-width]").each(function() {
        $(this).css("width", $(this).attr("data-width"));
    });

    class GSAPAnimation {
        static Init() {
            /*title-animation*/
            $('.title-animation').length && this.sectionTitleAnimation('.title-animation'); 
        }
        
        static sectionTitleAnimation(activeClass) {
            let sectionTitleLines = gsap.utils.toArray(activeClass);

            sectionTitleLines.forEach(sectionTextLine => {
                const tl = gsap.timeline({
                    scrollTrigger: {
                        trigger: sectionTextLine,
                        start: 'top 90%',
                        end: 'bottom 60%',
                        scrub: false,
                        markers: false,
                        toggleActions: 'play none none none'
                    }
                });

                const itemSplitted = new SplitText(sectionTextLine, { type: "chars, words" });
                gsap.set(sectionTextLine, { perspective: 100 });
                itemSplitted.split({ type: "words" })
                tl.from(itemSplitted.words, {
                    opacity: 0, 
                    autoAlpha: 0, 
                    transformOrigin: "top center -50",
                    y: "10px",
                    duration: 0.9,
                    stagger: 0.1,
                    ease: "power2.out",
                });
            });
        }
    }

    class RRDEVS {
        static LoadedAfter() {
            $('#preloader').delay(1).fadeOut(0);

            $(".odometer").waypoint(
                function () {
                    var odo = $(".odometer");
                    odo.each(function () {
                        var countNumber = $(this).attr("data-count");
                        var element = $(this);
                        setTimeout(function() {
                            element.html(countNumber);
                        }, 1000); // 1000 milliseconds delay (1 second)
                    });
                },
                {
                    offset: "80%",
                    triggerOnce: true,
                }
            );

            /*Wow Js*/
            if ($('.wow').length) {
                var wow = new WOW({
                    boxClass: 'wow',
                    animateClass: 'animated',
                    offset: 0,
                    mobile: false,
                    live: true
                });
                wow.init();
            }

            /*GSAPAnimation*/
            GSAPAnimation.Init();
        }
    }

    /*======================================
      Preloader activation
      ========================================*/
    $(window).on('load', RRDEVS.LoadedAfter);
    $(".preloader-close").on("click",  RRDEVS.LoadedAfter)

    window.addEventListener('resize', function() {
        gsap.globalTimeline.clear();
    });

    /*======================================
      Mobile Menu Js
      ========================================*/
    $("#mobile-menu").meanmenu({
        meanMenuContainer: ".mobile-menu",
        meanScreenWidth: "1199",
        meanExpand: ['<i class="fa-regular fa-angle-right"></i>'],
    });

    /*======================================
      Sidebar Toggle
      ========================================*/
    $(".offcanvas__close,.offcanvas__overlay").on("click", function () {
        $(".offcanvas__area").removeClass("info-open");
        $(".offcanvas__overlay").removeClass("overlay-open");
    });
    // Scroll to bottom then close navbar
    $(window).scroll(function(){
        if($("body").scrollTop() > 0 || $("html").scrollTop() > 0) {
            $(".offcanvas__area").removeClass("info-open");
            $(".offcanvas__overlay").removeClass("overlay-open");
        }
    });
    $(".sidebar__toggle").on("click", function () {
        $(".offcanvas__area").addClass("info-open");
        $(".offcanvas__overlay").addClass("overlay-open");
    });

    /*======================================
      Body overlay Js
      ========================================*/
    $(".body-overlay").on("click", function () {
        $(".offcanvas__area").removeClass("opened");
        $(".body-overlay").removeClass("opened");
    });

    /*======================================
      Sticky Header Js
      ========================================*/
    $(window).scroll(function () {
        if ($(this).scrollTop() > 10) {
            $("#header-sticky").addClass("rr-sticky");
        } else {
            $("#header-sticky").removeClass("rr-sticky");
        }
    });

    /*======================================
      MagnificPopup image view
      ========================================*/
    $(".popup-image").magnificPopup({
        type: "image",
        gallery: {
            enabled: true,
        },
    });

    /*======================================
      MagnificPopup video view
      ========================================*/
    $(".popup-video").magnificPopup({
        type: "iframe",
    });

    /*======================================
      Page Scroll Percentage
      ========================================*/
    const scrollTopPercentage = ()=> {
        const scrollPercentage = () => {
            const scrollTopPos = document.documentElement.scrollTop;
            const calcHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
            const scrollValue = Math.round((scrollTopPos / calcHeight) * 100);
            const scrollElementWrap = $("#scroll-percentage");

            scrollElementWrap.css("background", `conic-gradient( var(--rr-theme-primary) ${scrollValue}%, var(--rr-color-900) ${scrollValue}%)`);

            if ( scrollTopPos > 100 ) {
                scrollElementWrap.addClass("active");
            } else {
                scrollElementWrap.removeClass("active");
            }

            if( scrollValue < 96 ) {
                $("#scroll-percentage-value").text(`${scrollValue}%`);
            } else {
                $("#scroll-percentage-value").html('<i class="fa-solid fa-angle-up"></i>');
            }
        }
        window.onscroll = scrollPercentage;
        window.onload = scrollPercentage;

        // Back to Top
        function scrollToTop() {
            document.documentElement.scrollTo({
                top: 0,
                behavior: "smooth"
            });
        }

        $("#scroll-percentage").on("click", scrollToTop);
    }
    scrollTopPercentage();

    /*======================================
	One Page Scroll Js
	========================================*/
    var link = $('.onepagenav #mobile-menu ul li a, .onepagenav .mean-nav ul li a');
    link.on('click', function(e) {
        var target = $($(this).attr('href'));
        $('html, body').animate({
            scrollTop: target.offset().top - 76
        }, 600);
        $(this).parent().addClass('active');
        e.preventDefault();
    });
    $(window).on('scroll', function(){
        scrNav();
    });

    function scrNav() {
        var sTop = $(window).scrollTop();
        $('section').each(function() {
            var id = $(this).attr('id'),
                offset = $(this).offset().top-1,
                height = $(this).height();
            if(sTop >= offset && sTop < offset + height) {
                link.parent().removeClass('active');
                $('.main-menu').find('[href="#' + id + '"]').parent().addClass('active');
            }
        });
    }
    scrNav();

    /*======================================
	Smoth animatio Js
	========================================*/
    $(document).on('click', '.smoth-animation', function (event) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: $($.attr(this, 'href')).offset().top - 50
        }, 300);
    });

    /*brand__active***/
    let brand = new Swiper(".brand__active", {
        slidesPerView: 1,
        spaceBetween: 156,
        loop: true,
        roundLengths: true,
        clickable: true,
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1401: {
                slidesPerView: 5,
            },
            1200: {
                slidesPerView: 4,
            },
            992: {
                slidesPerView: 3,
            },
            576: {
                spaceBetween: 30,
                slidesPerView: 3,
            },
            481: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    /*testimonial__slider***/
    let header3TopSlider = new Swiper(".testimonial__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        navigation: {
            prevEl: ".testimonial__slider__arrow-prev",
            nextEl: ".testimonial__slider__arrow-next",
        },
        clickable: true,
        autoplay: {
            delay: 3000,
        }
    });

    /*client-testimonial__slider***/
    let clienttestimonial__slider = new Swiper(".client-testimonial__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        clickable: true,
        pagination: {
            el: ".client-testimonial__slider-dot",
            clickable: true,
        },
        autoplay: {
            delay: 3000,
        }
    });

    /*doctor__slider***/
    let doctor__slider = new Swiper(".doctor__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        clickable: true,
        pagination: {
            el: ".doctor__slider-dot",
            clickable: true,
        },
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1200: {
                slidesPerView: 3,
            },
            768: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    /*latest-work__slider***/
    let latest_work__slider = new Swiper(".latest-work__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        clickable: true,
        centeredSlides: true,
        pagination: {
            el: ".latest-work__slider-dot",
            clickable: true,
        },
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            768: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    /*banner-3__slider***/
    let Banner3Slider = new Swiper(".banner-3__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        clickable: true,
        autoplay: {
            delay: 3000,
        },
        pagination: {
            clickable: true,
            el: ".banner-3__pagination",
            renderBullet: function (index, className) {
                let indexText = (index + 1 < 10) ? '0' + (index + 1) : (index + 1);
                return '<span class="' + className + '">' + indexText + "</span>";
            },
        },
    });

    /*blog-2__slider***/
    let blog2__slider = new Swiper(".blog-2__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        navigation: {
            prevEl: ".blog-2__slider__arrow-prev",
            nextEl: ".blog-2__slider__arrow-next",
        },
        clickable: true,
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1200: {
                slidesPerView: 3,
            },
            768: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    /*testimonial-2__slider***/
    let testimonial2__slider = new Swiper(".testimonial-2__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        navigation: {
            prevEl: ".testimonial-2__slider__arrow-prev",
            nextEl: ".testimonial-2__slider__arrow-next",
        },
        clickable: true,
        autoplay: {
            delay: 3000,
        }
    });

    /*service-2__slider***/
    let service2__slider = new Swiper(".service-2__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        navigation: {
            prevEl: ".service-2__slider__arrow-prev",
            nextEl: ".service-2__slider__arrow-next",
        },
        clickable: true,
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1200: {
                slidesPerView: 3,
            },
            768: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    /*team__slider***/
    let team__slider = new Swiper(".team__slider", {
        slidesPerView: 1,
        spaceBetween: 40,
        loop: false,
        roundLengths: true,
        clickable: true,
        scrollbar: {
            el: ".team__scrollbar",
            hide: true,
        },
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1200: {
                slidesPerView: 3,
            },
            768: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

    //slider-text
    const scrollers = document.querySelectorAll(".rr-scroller");
    if (!window.matchMedia("(prefers-reduced-motion: reduce)").matches) {
        addAnimation();
    }
    function addAnimation() {
        scrollers.forEach((scroller) => {
            scroller.setAttribute("data-animated", true);

            const scrollerInner = scroller.querySelector(".rr-scroller__inner");
            const scrollerContent = Array.from(scrollerInner.children);

            scrollerContent.forEach((item) => {
                const duplicatedItem = item.cloneNode(true);
                duplicatedItem.setAttribute("aria-hidden", true);
                scrollerInner.appendChild(duplicatedItem);
            });
        });
    }

    /* lastNobullet ***/
    function lastNobullet() {
        var lastElement = false;
        $(".footer__copyright-menu ul li, .last_item_not_horizental_bar .col-lg-4").each(function() {
            if (lastElement && lastElement.offset().top != $(this).offset().top) {
                $(lastElement).addClass("no_bullet");
            } else {
                $(lastElement).removeClass("no_bullet");
            }
            lastElement = $(this);
        }).last().addClass("no_bullet");
    };
    lastNobullet();

    $(window).resize(function(){
        lastNobullet();
    });

    $('#pricing-appointment__form').submit(function(event) {
        event.preventDefault();
        var form = $(this);
        $('.loading-form').show();

        setTimeout(function() { 
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
                data: form.serialize()
            }).done(function(data) {
                $('.loading-form').hide();
                if($('.success-message').length==0){
                $('.pricing-appointment__form').append('<p class="success-message mt-3 mb-0">Your appointment has been booked successfully. Appointment Number: '+data.appointmentSeq+'</p>');
                }
                else{
				$('.success-message').text('Your appointment has been booked successfully. Appointment Number: '+data.appointmentSeq)
				}
				
				$("#totalAppointment").text(data.appointmentSeq);
				
				
            }).fail(function(data) {
                $('.loading-form').hide();
                if($('.error-message').length==0){
                $('.pricing-appointment__form').append('<p class="error-message mt-3 mb-0">Something went wrong. Please try again later.</p>');
				}
				else{
				$('.success-message').text('Something went wrong. Please try again later.')
				}
            });
        }, 1000);
    });
	
	$(document).on('click', '#name,#email,#phone,#firstName,#lastName,#mobile,#dob,#password,#cpassword', function (event) {
		if($('.success-message').length!=0){ //already one appointment done, new change will trigger new appointment
		if($("#pricing-appointment__form").length){
			$("#pricing-appointment__form")[0].reset();
		}
		
		else if	($("#patient_reg").length){ //to reset once registered successfully onclick of any input
			$("#patient_reg")[0].reset();
		}
			
			$('.success-message').remove();
			if($('.error-message').length!=0)
				$('.error-message').remove()
		}
	
	})
	
    $('.take-appointment-3__form-input-select select, .take-appointment__form-input-select select, .doctor-details__form-input-select select, .appointment-2__form-input-select select, .pricing-appointment__form-select select').niceSelect();
    $( "#datepicker" ).datepicker({
        dateFormat: "yy/mm/dd"
    });
	
	$("#dateForApp" ).datepicker
	({
        dateFormat: "dd/mm/yy",
        
        
    });
    $("#dateForApp").datepicker("setDate", new Date());
	
    $(".search-open-btn").on("click", function () {
        $(".search__popup").addClass("search-opened");
    });

    $(".search-close-btn").on("click", function () {
        $(".search__popup").removeClass("search-opened");
    });

    /* Popular Causes Progress Bar ***/
    if ($(".count-bar").length) {
        $(".count-bar").appear(
            function() {
                var el = $(this);
                var percent = el.data("percent");
                $(el).css("width", percent).addClass("counted");
            }, {
                accY: -50
            }
        );
    }


    /* image compare js ***/
    var ctrl = jQuery('.filter__container .comparison-ctrl');
    var pic_right = jQuery('.filter__container .pic--right');
    Draggable.create(ctrl,{
        bounds: ctrl.parent(),
        type: "x",
        onDrag: function(){
            pic_right.css('left','calc(50% + '+(this.x - 2)+'px)');
        }
    });

    /*specialist-doctor__slider***/
    let specialist = new Swiper(".specialist-doctor__slider", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: false,
        roundLengths: true,
        clickable: true,
        centerMode: true,
        scrollbar: {
            el: ".specialist-doctor__scrollbar ",
            hide: true,
        },
        autoplay: {
            delay: 3000,
        },
        breakpoints: {
            1200: {
                slidesPerView: 4,
            },
            768: {
                slidesPerView: 3,
            },
            575: {
                slidesPerView: 2,
            },
            0: {
                slidesPerView: 1,
            },
        },
    });

})(jQuery);


function statusbuttonChange(th){
//$('[name=statusbutton]').click(function(){
     //console.log($(th).siblings())
   //$(th).siblings()[0].click();
   	$('.loading-form').show();
	
	/*status = $(th).children().children()[0].textContent;
	
	if(status =="Done"){
		status =0;
	}
	else{
		status =1;
	}*/
	status = $(th).attr("isdone")
	if(status ==1 || status ==2){
		status =0;
	}
	else{
		status =1;
	}
	th_id= $(th).attr('appointment-id');
$.ajax({
                //url: $($(th).siblings()[0]).attr('href'),
                url: "/update/"+th_id+"/"+status,
				  type: "POST",
				  data: {id : th_id,
				  		isdone: status	},
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("done");
                /*setTimeout(function(){
					if(status==1){
                	//$(th).css("background","green");
                	$(th).attr("data-color","green");
                	$(th).attr("isdone","1");
                	}
                else{
                	//$(th).css("background","");
                	$(th).attr("data-color","none");
                	$(th).attr("isdone","0");
                	}
                	$($(th).children().children()[1]).blur();
                	
				},300)*/
                if(status==0){
					if(data.success==1){
						$(th).attr("isdone","0");
						
						//$(th).find('span.text-Done').addClass("d-none")
						
						$(th).find('span.text-Pending').fadeIn();
						$(th).find('span.text-Done').fadeOut();
						$(th).find('span.text-Pending').removeClass("d-none");
					}
				}else if(status==1){
					if(data.success==1){
						$(th).attr("isdone","1");
						
						//$(th).find('span.text-Pending').addClass("d-none")
						$(th).find('span.text-Pending').fadeOut();
						$(th).find('span.text-Done').fadeIn();
						$(th).find('span.text-Done').removeClass("d-none");
					}
				}
                	
               var firstText= $(th).children().children()[0].textContent
               var secondText= $(th).children().children()[1].textContent
               
              // $(th).children().children()[0].textContent = secondText;
               //$(th).children().children()[1].textContent = firstText;
               $(th).parents().eq(2).find("[name^=remark]").focus();
                
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed");

            });
   
   
   return;
//})
}

function getCurrentSeq(){
	$('.loading-form').show();
	
	
	$.ajax({
        url: '/getCurrentSeq', // <-- point to server-side PHP script 
        cache: false,
        contentType: false,
        processData: false,
        //data: form_data,                         
        type: 'post',
     }).done(function(data) {
                $('.loading-form').hide();
                console.log("done")
                
                
                $("#currentSeq").text(data.appointmentSeq)
                
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed")

            });
}
function viewItem(th){
	console.log(th)
	$('.loading-form').show();
	
	if($("#viewImage"+$(th).attr('appointment-id')).attr("src") !=""){
		Large($("#viewImage"+$(th).attr('appointment-id'))[0]);
		$('.loading-form').hide();
	}else{
	
	
$.ajax({
                url: "/viewItem",
				  type: "POST",
				  data: {id : $(th).attr('appointment-id'),
				  		patient_id: $(th).attr('patient-id')	},
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("done")
                
                $("#viewImage"+$(th).attr('appointment-id')).attr("src","data:image/jpeg;base64,"+data.imgData+"");
                
                Large($("#viewImage"+$(th).attr('appointment-id'))[0]);
                
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed")

            });
            }
}


function deleteItem(th){
	console.log(th)
	$('.loading-form').show();
	
	appointment_id = $(th).attr('appointment-id')
	
$.ajax({
                url: "/deleteItem",
				  type: "POST",
				  data: {id : $(th).attr('appointment-id'),
				  		patient_id: $(th).attr('patient-id')	},
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("done")
                $("#viewI"+appointment_id).addClass("d-none");
					$("#deleteI"+appointment_id).addClass("d-none");
                	$("input[type=file][appointment-id="+appointment_id+"]").removeClass("d-none");
					$("input[type=file][appointment-id="+appointment_id+"]").val("")

               
               
                
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed")

            });
    }


function getElementLeft(elm) 
{
    var x = 0;

    //set x to elm’s offsetLeft
    x = elm.offsetLeft;

    //set elm to its offsetParent
    elm = elm.offsetParent;

    //use while loop to check if elm is null
    // if not then add current elm’s offsetLeft to x
    //offsetTop to y and set elm to its offsetParent

    while(elm != null)
    {
        x = parseInt(x) + parseInt(elm.offsetLeft);
        elm = elm.offsetParent;
    }
    return x;
}

function getElementTop(elm) 
{
    var y = 0;

    //set x to elm’s offsetLeft
    y = elm.offsetTop;

    //set elm to its offsetParent
    elm = elm.offsetParent;

    //use while loop to check if elm is null
    // if not then add current elm’s offsetLeft to x
    //offsetTop to y and set elm to its offsetParent

    while(elm != null)
    {
        y = parseInt(y) + parseInt(elm.offsetTop);
        elm = elm.offsetParent;
    }

    return y;
}
function Large(obj)
{
    var imgbox=document.getElementById("imgbox");
    imgbox.style.visibility='visible';
    var img = document.createElement("img");
    img.id="zoomedImg"
    img.src=obj.src;
    //img.style.width='400px';
    img.style.height='-webkit-fill-available';
    
    if(img.addEventListener){
        img.addEventListener('mouseout',Out,false);
    } else {
        img.attachEvent('onmouseout',Out);
    }             
    imgbox.innerHTML='';
    imgbox.appendChild(img);
//    imgbox.style.left=(getElementLeft(obj)-50) +'px';
     imgbox.style.left='0px';
//    imgbox.style.top=(getElementTop(obj)-50) + 'px';
//imgbox.style.top='100%';


$(imgbox).css('position-area' , 'center');
$(".section-space").css("opacity",0.5);
}
function Out()
{
    document.getElementById("imgbox").style.visibility='hidden';
    $(".section-space").css("opacity","unset")
}

function writeItem(th){
	appointment_id = $(th).attr('appointment-id');
	canvasRender(appointment_id);
}

function getTouchPos(canvasDom, touchEvent) {
        const rect = canvasDom.getBoundingClientRect();
        return {
            x: touchEvent.touches[0].clientX - rect.left,
            y: touchEvent.touches[0].clientY - rect.top
        };
    }

function canvasRender(app_id){
	
	
//	$("#signatureCanvas"+app_id).removeClass("d-none");
	$("#signatureCanvas").removeClass("d-none");
	$("#captureButton"+app_id).removeClass("d-none");
	
//	const canvas = document.getElementById("signatureCanvas"+app_id);
 	const canvas = document.getElementById("signatureCanvas");
 	canvas.style.visibility='visible';
     const ctx = canvas.getContext("2d");
     let drawing = false;
	
	
	let lastX = 0;
    let lastY = 0;
		
	
     canvas.addEventListener("mousedown", (e) => {
       drawing = true;
       ctx.beginPath();
       ctx.moveTo(e.offsetX, e.offsetY);
     });
	
	canvas.addEventListener('touchstart', (e) => {
        e.preventDefault(); // Prevent scrolling/zooming
        isDrawing = true;
        const touch = getTouchPos(canvas, e);
        [lastX, lastY] = [touch.x, touch.y];
        ctx.beginPath(); // Start a new path for each new stroke
        ctx.moveTo(lastX, lastY);
    }, false); // Use capture phase if needed, but false is usually fine

	// Event listener for when the touch moves
    canvas.addEventListener('touchmove', (e) => {
        e.preventDefault(); // Prevent scrolling/zooming
        if (!isDrawing) return;

        const touch = getTouchPos(canvas, e);
        ctx.lineTo(touch.x, touch.y);
        ctx.stroke();
        [lastX, lastY] = [touch.x, touch.y]; // Update last position for continuous line
    }, false);

    // Event listener for when the touch ends
    canvas.addEventListener('touchend', () => {
        isDrawing = false;
    }, false);

    // Event listener for when the touch is cancelled (e.g., system interrupt)
    canvas.addEventListener('touchcancel', () => {
        isDrawing = false;
    }, false);
	
     canvas.addEventListener("mousemove", (e) => {
       if (!drawing) return;
       ctx.lineTo(e.offsetX, e.offsetY);
       ctx.stroke();
     });
//	pointermove
	
		
     canvas.addEventListener("mouseup", () => {
       drawing = false;
     });
    
//    const clearBtn = document.getElementById("clearButton"+app_id); 
//    clearBtn.addEventListener('click', () => {
//        ctx.clearRect(0, 0, canvas.width, canvas.height);
//    });
    
    

     const captureButton = document.getElementById("captureButton"+app_id);
     captureButton.addEventListener("click", () => {
    	 const dataURL = canvas.toDataURL('image/png'); // Get image data as PNG
        
       const signatureImage = dataURL;
       console.log("Signature Image Data:", signatureImage);
       document.getElementById("signatureCanvas").style.visibility='hidden';
 	
       
     /*  if(document.getElementById("canvasImg")){
       	document.getElementById("canvasImg").remove();
       }
       
       
       
       var imgbox=document.getElementById("imgbox");
       imgbox.style.visibility='visible';
       var img = document.createElement("img");
       img.id="canvasImg"
       img.src= signatureImage;
       //img.style.width='400px';
       img.style.height='-webkit-fill-available';
       imgbox.appendChild(img);
       
    	imgbox.style.visibility='visible';*/
       
      // Large(img);
       // You can send this image data to a server or store it
     });
}

$(document).on("click",function(e){
//    console.log(e.target)
if(e.target.id!="zoomedImg"){
	//Out();
}
})

function uploadPrescription(th){
	$('.loading-form').show();
	var file_data = th.files[0];   
    var form_data = new FormData();
    var appointment_id = $(th).attr('appointment-id')                  
    form_data.append('file', file_data);
    form_data.append('id',$(th).attr('appointment-id'));
    form_data.append('patient_id',$(th).attr('patient-id'));
    $.ajax({
        url: '/upload', // <-- point to server-side PHP script 
        //dataType: 'text',  // <-- what to expect back from the PHP script, if anything
        cache: false,
        contentType: false,
        processData: false,
        data: form_data,                         
        type: 'post',
//        success: function(php_script_response){
//            alert(php_script_response); // <-- display response from the PHP script, if any
//        }
     }).done(function(data) {
                $('.loading-form').hide();
                console.log("done")
                
                if(data.success =="1"){
				//if($('[name=statusbutton][appointment-id='+appointment_id+']').css("background") !="green"){
                //$('[name=statusbutton][appointment-id='+appointment_id+']').css("background","green");
	                var statusBtn = $('[name=statusbutton][appointment-id='+appointment_id+']');
	                var firstText= $(statusBtn).children().children()[0].textContent
	               var secondText= $(statusBtn).children().children()[1].textContent
	               $(statusBtn).attr("isdone","2"); 
	              // $(statusBtn).children().children()[0].textContent = secondText;
	               //$(statusBtn).children().children()[1].textContent = firstText;
	               
	               
						
//						$(th).find('span.text-Pending').fadeIn();
//						$(th).find('span.text-Done').fadeOut();
//						$(th).find('span.text-Pending').removeClass("d-none");
//					
					
					$("#viewI"+appointment_id).removeClass("d-none");
					$("#deleteI"+appointment_id).removeClass("d-none");
                	$("input[type=file][appointment-id="+appointment_id+"]").addClass("d-none");
//	               		$(th).find('span.text-Pending').fadeOut();
//						$(th).find('span.text-Done').fadeIn();
//						$(th).find('span.text-Done').removeClass("d-none");

						$("#status"+appointment_id).find('span.text-Pending').fadeOut();
						$("#status"+appointment_id).find('span.text-Done').fadeIn();
						$("#status"+appointment_id).find('span.text-Done').removeClass("d-none");
              // }
               }
                
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed")

            });
}


//verifyOtp

$('#otpEmail').submit(function(event) {
        event.preventDefault();
        var form = $(this);
        $('.loading-form').show();
	

var data = form.serializeArray(); // convert form to array

        setTimeout(function() { 
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
               data: $.param(data),
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("verifyOtp done")
                $('.success-message').remove();
			if($('.error-message').length!=0)
				$('.error-message').remove()
				if(data.res=='true')
                 {
				$('.register-form').append('<p class="success-message mt-3 mb-0">OTP verified successfully.</p>');
                }
                else{
					if(data.error){
						$('.register-form').append('<p class="error-message mt-3 mb-0">'+data.error+'</p>');
					}
					else{
						$('.register-form').append('<p class="error-message mt-3 mb-0">Something went wrong. Please try again later.</p>');
						
					}
				} 
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("verifyOtp failed")
                 $('.register-form').append('<p class="error-message mt-3 mb-0">Something went wrong. Please try again later.</p>');
            });
        }, 1000);
    });



//savePatient
 $('#patient_reg').submit(function(event) {
        event.preventDefault();
        var form = $(this);
        $('.loading-form').show();
	
//	var formData = form.serialize();
//	 console.log(formData)
var fullName= $('#firstName').val()+" "+$('#lastName').val()
var data = form.serializeArray(); // convert form to array
data.push({name: "name", value: fullName});

        setTimeout(function() { 
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
               // data: form.serialize()
               data: $.param(data),
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("patient_reg done")
                $('.success-message').remove();
			if($('.error-message').length!=0)
				$('.error-message').remove()
				if(data.success==1)
                 {
				$('.register-form').append('<p class="success-message mt-3 mb-0">Your have registered successfully.</p>');
                }
                else{
					if(data.error){
						$('.register-form').append('<p class="error-message mt-3 mb-0">'+data.error+'</p>');
					}
					else{
					$('.register-form').append('<p class="error-message mt-3 mb-0">Something went wrong. Please try again later.</p>');
					}
				} 
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("patient_reg failed")
                 $('.register-form').append('<p class="error-message mt-3 mb-0">Something went wrong. Please try again later.</p>');
            });
        }, 1000);
    });

	


function remarksUpdate(th){
	$('.loading-form').show();
	console.log(th)
$.ajax({
                url: "updateremarks",
				  type: "POST",
				  data: {id : th.id,
				  		remarks: th.value	},
            }).done(function(data) {
                $('.loading-form').hide();
                console.log("done")
            }).fail(function(data) {
                $('.loading-form').hide();
                console.log("failed")

            });
}