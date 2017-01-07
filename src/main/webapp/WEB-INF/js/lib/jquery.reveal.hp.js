/*
 * This is our own version of the ZURB reveal.js jquery plugin.
 * It's been modified to work decently on IE8 - or any browser that jQuery would detect as lacking support for opacity.
 */

/* jQuery Reveal Plugin 1.0
 * www.ZURB.com
 * Copyright 2010, ZURB
 * Free to use under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 */

(function($) {

    /*---------------------------
     Defaults for Reveal
     ----------------------------*/

    /*---------------------------
     Listener for data-reveal-id attributes
     ----------------------------*/

    $('a[data-reveal-id]').live('click', function(e) {
        e.preventDefault();
        var modalLocation = $(this).attr('data-reveal-id');
        $('#' + modalLocation).reveal($(this).data());
    });

    /*---------------------------
     Extend and Execute
     ----------------------------*/
    $.fn.reveal = function(optionsIn) {
        var defaults = {
                animation : 'fade', //fade, fadeAndPop, none
                animationspeed : 190, //how fast animations are
                closeonbackgroundclick : true, //if you click background will modal close?
                dismissmodalclass : 'close-reveal-modal', //the class of a button or element that will close an open modal
                opacity : $.support.opacity, // i8ie8
                targetOpacity : ($.support.opacity ? 1 : 70),
                closeHandler : function(){}
            },
            options,
            opacityFix = function(){};

        //Extend dem' options
        options = $.extend({}, defaults, optionsIn);
        
        return this.each(function() {
            /*---------------------------
             Global Variables
             ----------------------------*/
            var modal = $(this),
                closeButton,
                topMeasure = parseInt(modal.css('top')), 
                topOffset = modal.height() + topMeasure, 
                locked = false, 
                modalHtml = '<div class="reveal-modal-bg" />', 
                modalBG = $('.reveal-modal-bg');


            /*---------------------------
             Create Modal BG
             ----------------------------*/
            if (modalBG.length === 0) {
                if (options.opacity) {
                    $(modalHtml).insertAfter(modal);
                } else {
                    $(modal).after($(modalHtml));
                }
                modalBG = $('.reveal-modal-bg');
            }
            
            if (!options.opacity) {
                opacityFix = function(alpha) {
                    if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
                        alpha = 40;
                    }
                    modalBG.css({
                        "display" : "inline-block",
                        "filter" : "alpha(opacity="+((alpha<1)?alpha*100:alpha)+")"
                    });
                };
            }
            
            /************************************************
             *              Util functions
             *************************************************/
            
            /*---------------------------
            Open and add Closing Listeners
            ----------------------------*/      
            
            function close(e) {
                  modal.trigger('reveal:close');
                  if (typeof options.closeHandler === "function") {
                      options.closeHandler(e);
                  }
            }   
               
            function registerKeys() {
                $('.' + options.dismissmodalclass).bind('click.modalEvent', close);

                if (options.closeonbackgroundclick) {
                    modalBG.css({
                        "cursor" : "pointer"
                    });
                    modalBG.bind('click.modalEvent', close);
                }
                
                $("body").keyup(function(e) {
                    if (e.which === 27) {
                        close();
                    } // 27 is the key-code for the Escape key
                });
            }

            function wrapup() {
                unlockModal();
                opacityFix(options.targetOpacity);
                //registerKeys();
            }
            
            /*---------------------------
             Animations Locks
             ----------------------------*/
            function unlockModal() {
                locked = false;
            }

            function lockModal() {
                locked = true;
            }            


            /*
             * Setup starting, wiring events....
             */
            /*---------------------------
            Open & Close Animations
            ----------------------------*/
            //Expose the registerKeys() function
            modal.bind('reveal:rewire', registerKeys );
            
            //Entrance Animations            
            modal.bind('reveal:open', function() {
                modalBG.unbind('click.modalEvent');
                $('.' + options.dismissmodalclass).unbind('click.modalEvent');
                if (!locked) {
                    lockModal();
                    if (options.animation === "fadeAndPop") {
                        modal.css({
                            'top' : $(document).scrollTop() - topOffset,
                            'opacity' : 0,
                            'visibility' : 'visible'
                        });
                        opacityFix(0);
                        modalBG.fadeIn(options.animationspeed / 2);
                        modal.delay(options.animationspeed / 2).animate({
                            "top" : $(document).scrollTop() + topMeasure + 'px',
                            "opacity" : options.targetOpacity
                            }, 
                            options.animationspeed,
                            wrapup
                        );
                    } else
                    if (options.animation === "fade") {
                        modal.css({
                            'opacity' : 0,
                            'visibility' : 'visible',
                            'top' : $(document).scrollTop() + topMeasure
                        });
                        opacityFix(0);
                        modalBG.fadeIn(options.animationspeed / 2);
                        modal.delay(options.animationspeed / 2).animate(
                                    { "opacity" : options.targetOpacity }, 
                                    options.animationspeed, 
                                    wrapup);
                    } else
                    if (options.animation === "none") {
                        modal.css({
                            'visibility' : 'visible',
                            'top' : $(document).scrollTop() + topMeasure
                        });
                        modalBG.css({
                            "display" : "block"
                        });
                        wrapup();
                    }
                }
                modal.unbind('reveal:open');
            });

            //Closing Animation
            modal.bind('reveal:close', function() {
                if (!locked) {
                    lockModal();
                    if (options.animation === "fadeAndPop") {
                        modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
                        modal.animate(
                            {   "top" : $(document).scrollTop() - topOffset + 'px',
                                "opacity" : 0
                            }, 
                            options.animationspeed / 2,
                            function() {
                                modal.css({
                                    'top' : topMeasure,
                                    'opacity' : 1,
                                    'visibility' : 'hidden'
                                });
                                unlockModal();
                            });
                    } 
                    if (options.animation === "fade") {
                        modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
                        modal.animate(
                            {"opacity" : 0 }, 
                            options.animationspeed, 
                            function() {
                                modal.css({
                                    'opacity' : 1,
                                    'visibility' : 'hidden',
                                    'top' : topMeasure
                                });
                                unlockModal();
                            });
                    }
                    if (options.animation === "none") {
                        modal.css({
                            'visibility' : 'hidden',
                            'top' : topMeasure
                        });
                        modalBG.css({
                            'display' : 'none'
                        });
                    }
                }
                modal.unbind('reveal:close');
            });

            /*********************************************
             *       Finally, Open Modal!
             */
            modal.trigger('reveal:open');
            registerKeys();
        });
        //each call
    };//orbit plug-in call
})(jQuery);
