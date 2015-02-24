module.exports = function(grunt) {
    var gtx = require('gruntfile-gtx').wrap(grunt);

    gtx.loadAuto();

    var gruntConfig = require('./grunt');
    gruntConfig.package = require('./package.json');

    gtx.config(gruntConfig);

    // We need our bower components in order to develop
    gtx.alias('build:app',  ['clean:components','bower-install-simple','concat:components','uglify:components','recess:components', 'copy:fonts']);
    gtx.alias('build:js',  ['concat:components','uglify:components']);
    gtx.alias('build:css',  ['recess:components']);
    gtx.finalise();
}