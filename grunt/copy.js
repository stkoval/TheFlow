module.exports = {
    fonts: {
        nonull: true,
        files: [
            {src: "**", dest: "src/main/webapp/resources/fonts", cwd: 'bower_components/bootstrap/fonts', expand : true},
            {src: "**", dest: "src/main/webapp/resources/fonts", cwd: 'bower_components/font-awesome/fonts', expand : true}
        ]
    }
};