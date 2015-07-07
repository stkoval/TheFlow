module.exports = {
    scripts: {
        files: ['jade/views/**/*.jade'],
        tasks: ['jade']
    },
    styles: {
        files: ['<%= package.resources %>css/*.less'],
        tasks: ['recess']
    },
    javascripts: {
        files: ['<%= package.resources %>js/*.js'],
        tasks: ['concat', 'uglify']
    }
}