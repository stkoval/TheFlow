module.exports = {
    components: {
        files: {
            '<%= package.resources %>css/compiled.min.css': [
                'bower_components/bootstrap/dist/css/bootstrap.css',
                'bower_components/font-awesome/css/font-awesome.css',
                'bower_components/bootstrap-multiselect/dist/css/bootstrap-multiselect.css',
                'bower_components/datatables-bootstrap3/BS3/assets/css/datatables.css',
                'bower_components/bootstrap-fileinput/css/fileinput.css',
                'bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                'bower_components/bootstrap-multiselect/dist/css/bootstrap-multiselect.css',
                'bower_components/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.css',
                'bower_components/bootstrap-select/dist/css/bootstrap-select.css',
                '<%= package.resources %>css/fancy-buttons.css',
                '<%= package.resources %>css/styles.less'
            ]
        }
    },
    landing: {
        files: {
            '<%= package.resources %>css/compiled.landing.min.css': [
                'bower_components/bootstrap/dist/css/bootstrap.css',
                'bower_components/font-awesome/css/font-awesome.css',
                '<%= package.resources %>css/fancy-buttons.css',
                '<%= package.resources %>css/styles-landing.less'
            ]
        }
    },
    options: {
        compile: true,
        compress: true
    }
}