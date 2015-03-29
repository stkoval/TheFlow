module.exports = {
    fonts: {
        nonull: true,
        files: [
            {src: "**", dest: "<%= package.folder %>fonts", cwd: 'bower_components/bootstrap/fonts', expand : true},
            {src: "**", dest: "<%= package.folder %>fonts", cwd: 'bower_components/font-awesome/fonts', expand : true}
        ]
    }
};