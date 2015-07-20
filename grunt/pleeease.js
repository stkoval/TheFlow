module.exports = {
    custom: {
        options: {
            "minifier": {preserveHacks: true, removeAllComments: true},
            "mqpacker": true,
            "less": false
        },
        files: {
            '<%= package.resources %>css/compiled.min.css' : '<%= package.resources %>css/compiled.min.css',
            '<%= package.resources %>css/compiled.landing.min.css' : '<%= package.resources %>css/compiled.landing.min.css'
        }
    }
}