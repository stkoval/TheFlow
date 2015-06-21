module.exports = {
    components:{
        src:[
            'bower_components/jquery/dist/jquery.js',
            'bower_components/slimScroll/jquery.slimscroll.js',
            'bower_components/moment/moment.js',
            'bower_components/jquery.cookie/jquery.cookie.js',
            'bower_components/bootstrap/dist/js/bootstrap.js',
            'bower_components/bootstrap-fileinput/js/fileinput.js',
            'bower_components/datatables/media/js/jquery.dataTables.js',
            'bower_components/datatables-bootstrap3/BS3/assets/js/datatables.js',
            'bower_components/eonasdan-bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
            'bower_components/bootstrap-multiselect/dist/js/bootstrap-multiselect.js',
            'bower_components/bootstrap-switch/dist/js/bootstrap-switch.js',
            'bower_components/bootstrap-select/js/bootstrap-select.js',
            'bower_components/bootstrap-validator/js/validator.js',
            '<%= package.resources %>js/scripts.js'
        ],
        dest:'<%= package.resources %>js/compiled.js'
    },
    landing:{
        src:[
            'bower_components/jquery/dist/jquery.js',
            'bower_components/bootstrap/dist/js/bootstrap.js',
            'bower_components/eventEmitter/EventEmitter.js',
            'bower_components/eventie/eventie.js',
            'bower_components/imagesloaded/imagesloaded.js',
            'bower_components/skrollr/src/skrollr.js',
            'bower_components/skrollr-menu/src/skrollr.menu.js',
            '<%= package.resources %>js/scripts-landing.js'
        ],
        dest:'<%= package.resources %>js/compiled.landing.js'
    }
}