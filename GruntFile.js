module.exports = function(grunt) {
    var gtx = require('gruntfile-gtx').wrap(grunt);

    gtx.loadAuto();

    var gruntConfig = require('./grunt');
    gruntConfig.package = require('./package.json');

    gtx.config(gruntConfig);

    // We need our bower components in order to develop
    gtx.alias('build:app',  [
        'clean:components',
        'bower-install-simple',
        'concat:components',
        'uglify:components',
        'concat:landing',
        'uglify:landing',
        'recess:components',
        'recess:landing',
        'pleeease:custom',
        'jade:compile',
        'copy:fonts']);
    gtx.alias('build:js',  ['concat:components','uglify:components','concat:landing','uglify:landing']);
    gtx.alias('build:css',  ['recess:components','recess:landing','pleeease:custom']);
    gtx.alias('build:html',  ['jade:compile']);
    gtx.alias('build:files',  ['concat:components','uglify:components','concat:landing','uglify:landing','recess:components','recess:landing','pleeease:custom','copy:fonts','jade:compile']);
    gtx.finalise();
}