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

})(jQuery);
