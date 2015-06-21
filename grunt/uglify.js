module.exports = {
    components: {
        src:[
            '<%= package.resources %>js/compiled.js'
        ],
        dest:'<%= package.resources %>js/compiled.min.js'
    },
    landing: {
        components: {
            src:[
                '<%= package.resources %>js/compiled.landing.js'
            ],
            dest:'<%= package.resources %>js/compiled.landing.min.js'
        }
    }
}