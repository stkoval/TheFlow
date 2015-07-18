module.exports = {
    compile: {
        expand: true,
        cwd: 'jade/views/',
        src: ['**/*.jade'],
        dest: '<%= package.views %>',
        ext: '.html',
        options: {
            client: false,
            pretty: false
        }
    }
}