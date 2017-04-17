module.exports = function(grunt) {

"use strict";
  
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    compare_size: {
		all: [
			"public/javascripts/my-additional-methods.js",
			"public/javascripts/my-additional-methods.min.js"
		]
	},
    uglify: {
		options: {
			preserveComments: false
		},
		main: {
			files: {
				"public/javascripts/my-additional-methods.min.js": "public/javascripts/my-additional-methods.js"
			}
		},
	},
    jshint: {
        js: {
             options: {
              jshintrc: '.jshintrc'
            },
            files: {
                src: "public/javascripts/my-additional-methods.js"
            }
        },
		grunt: {
			files: {
				src: [
					'Gruntfile.js'
				]
			}
		}
	},
    bowercopy: {
        options: {
            // Bower components folder will be removed or not afterwards
            clean: false
        },
        plugins: {
            options: {
                destPrefix: 'public/javascripts'
            },
            files: {
                // Make dependencies follow your naming conventions
                'additional-methods.js': 'jquery-validation/dist/additional-methods.js',
                'additional-methods.min.js': 'jquery-validation/dist/additional-methods.min.js',
                'jquery.validate.js': 'jquery-validation/dist/jquery.validate.js',
                'jquery.validate.min.js': 'jquery-validation/dist/jquery.validate.min.js',
            }
        },
    }
  });

  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks( "grunt-compare-size" );
  grunt.loadNpmTasks('grunt-bowercopy');

  grunt.registerTask( "js", [ "jshint", "uglify", 'compare_size' ] );
  grunt.registerTask('default', ['bowercopy']);
};