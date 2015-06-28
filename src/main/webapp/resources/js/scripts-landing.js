/*! viewportSize | Author: Tyson Matanich, 2013 | License: MIT */
(function (n) {
    n.viewportSize = {}, n.viewportSize.getHeight = function () {
        return t("Height")
    }, n.viewportSize.getWidth = function () {
        return t("Width")
    };
    var t = function (t) {
        var f, o = t.toLowerCase(), e = n.document, i = e.documentElement, r, u;
        return n["inner" + t] === undefined ? f = i["client" + t] : n["inner" + t] != i["client" + t] ? (r = e.createElement("body"), r.id = "vpw-test-b", r.style.cssText = "overflow:scroll", u = e.createElement("div"), u.id = "vpw-test-d", u.style.cssText = "position:absolute;top:-1000px", u.innerHTML = "<style>@media(" + o + ":" + i["client" + t] + "px){body#vpw-test-b div#vpw-test-d{" + o + ":7px!important}}<\/style>", r.appendChild(u), i.insertBefore(r, e.head), f = u["offset" + t] == 7 ? i["client" + t] : n["inner" + t], i.removeChild(r)) : f = n["inner" + t], f
    }
})(this);

(function ($) {

    // Setup variables
    $window = $(window);
    $slide = $('.homeSlide');
    $body = $('body');
    //FadeIn all sections
    $body.imagesLoaded(function () {
        setTimeout(function () {
            adjustWindow();
            $body.removeClass('loading').addClass('loaded');
        }, 800);
    });

    $('[data-toggle="tooltip"]').tooltip();

    $(window).scroll(function () {
        var cSection = 'slide-1';
        if ($(window).scrollTop() <= $(window).height()) {
            $('a.nav-decks').removeClass('current-nav');
            $('a[href="#slide-1"]').addClass('current-nav');
        } else {
            $('.homeSlide').each(function () {
                if (isScrolledIntoView($(this))) {
                    var cID = $(this).attr('id');
                    if (cID != cSection) {
                        cSection = cID;
                        $('a.nav-decks').removeClass('current-nav');
                        $('a[href="#' + cID + '"]').addClass('current-nav');
                    }
                }
            });
        }
    });

    function adjustWindow() {

        var s = skrollr.init({
            render: function (data) {

            }
        });

        skrollr.menu.init(s, {});

        winH = $window.height();

        if (winH <= 550) {
            winH = 550;
        }

        $slide.height(winH * 2);
        s.refresh($('.homeSlide'));

        var cHash = window.location.hash;

        if ($(cHash).offset()) {
            s.animateTo($(cHash).offset().top);
        }

    }

    function isScrolledIntoView(elem) {
        var docViewTop = $(window).scrollTop() - $(window).height();
        var docViewBottom = docViewTop + $(window).height();
        var elemTop = $(elem).offset().top;
        return ((elemTop <= docViewBottom) && (elemTop >= docViewTop));
    }

    $('.contact-form form').submit(function () {

        var name = $('.contact-form form input#name').val();
        var email = $('.contact-form form input#email').val();
        var message = $('.contact-form form textarea#message').val();

        if(message.length && email.length && name.length) {

            $(this).find('.control-group').fadeOut(0);
            $(this).find('.loader').fadeIn();

            $.ajax({
                type: "POST",
                url: "https://mandrillapp.com/api/1.0/messages/send.json",
                data: {
                    'key': 'Yv6DfvG9RClIzjk1BPUFbw',
                    'message': {
                        'from_email': 'eugenpushkaroff@gmail.com',
                        'to': [
                            {
                                'email': 'skoval.mail@gmail.com',
                                'name': name,
                                'type': 'to'
                            }
                        ],
                        'autotext': 'true',
                        'subject': 'New Inspired Form | Name: '+name+' | Email: ' + email,
                        'html': '<h3>Name: '+name+'<h3>'+'<h4>Email: '+email+'<h4>'+'<p>Message: '+message+'</p>'
                    }
                }
            }).done(function (response) {
                $('.contact-form form').find('.loader').fadeOut();
                $('.contact-form form').find('.status-message-contact').fadeIn();
            }).fail(function (jqXHR, textStatus) {
                $('.contact-form form').find('.loader').fadeOut();
                $('.contact-form form').find('.control-group').fadeIn();
                $('.contact-form form').find('.status-message-contact').html(textStatus);
                $('.contact-form form').find('.status-message-contact').fadeIn();
            });
        }

        return false;

    });

})(jQuery);
