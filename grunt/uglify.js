module.exports = {
    components: {
        src:[
            '<%= package.folder %>js/compiled.js'
        ],
        dest:'<%= package.folder %>js/compiled.min.js'
    }
}