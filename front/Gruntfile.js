/**
 * Seed Project for the development of front-end javascript applications using bower and grunt.
 * The project is pre-configured with the following grunt modules:
 *
 * 1) connect
 * 2) open
 * 3) sass
 * 4) copy
 * 5) uglify
 * 6) concat
 * 7) remove
 * 8) string-replace
 * 9) usebanner
 * 10) watch
 */
module.exports = function (grunt) {
    var modRewrite = require('connect-modrewrite');
    require("matchdep").filterDev("grunt-*").forEach(grunt.loadNpmTasks);
    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        app: {
            dist: "dist",
            src: "src",
            host: "0.0.0.0",
            vendors: "node_modules",
            css_files: "css",
            port: "8888",
            apiHost: "localhost",
            apiPort: "8080"
        },

        /**
         * Connect is a static web server for development.
         */
        connect: {
            server: {
                options: {
                    hostname: '<%=app.host%>',
                    port: '<%=app.port%>',
                    base: "<%=app.dist%>",
                    livereload: 12345,
                    middleware: function (connect, options, defaultMiddleware) {
                        var proxy = require('grunt-connect-proxy/lib/utils').proxyRequest;
                        var marray = [proxy];
                        return marray.concat(defaultMiddleware);
                    }
                },
                /**
                 * Grunt Connect support for proxying API calls during development.
                 * Create a proxy between the application and a REST api to prevent CORS.
                 */
                proxies: [{
                    context: '/cte-api/api',
                    host: '<%=app.apiHost%>',
                    port: '<%=app.apiPort%>'
                }]
            }
        },



        /**
         * Open urls and files from a grunt task.
         */
        open: {
            all: {
                path: 'http://<%=app.host%>:<%=app.port%>'
            }
        },

        /**
         *  Compile Sass to CSS.
         */
        sass: {
            main: {
                files: [{
                        cwd: '<%=app.css_files%>',
                        src: '**/*',
                        dest: '<%=app.dist%>/css',
                        expand: true
                    }
                ]
            },
            dist: {
                options: {
                    style: 'expanded',
                    sourcemap: false
                },
                files: {
                    '<%=app.dist%>/css/main.css': '<%=app.src%>/scss/main.scss'
                }
            }
        },

        /**
         * Copy static files and folders to the static web server document root.
         */
        copy: {
            main: {
                files: [{
                        cwd: '<%=app.vendors%>/bootstrap-sass/assets/fonts',
                        src: '**/*',
                        dest: '<%=app.dist%>/fonts',
                        expand: true
                    }, {
                        cwd: '<%=app.vendors%>/font-awesome/fonts',
                        src: '**/*',
                        dest: '<%=app.dist%>/fonts',
                        expand: true
                    }, {
                        cwd: 'fonts',
                        src: '**/*',
                        dest: '<%=app.dist%>/fonts',
                        expand: true
                    }, {
                        cwd: '<%=app.src%>/images',
                        src: '**/*',
                        dest: '<%=app.dist%>/images',
                        expand: true
                    }, {
                        cwd: '<%=app.src%>/',
                        src: '*.html',
                        dest: '<%=app.dist%>/',
                        expand: true
                    }, {
                        cwd: '<%=app.src%>/partials',
                        src: '**/*.html',
                        dest: '<%=app.dist%>/partials',
                        expand: true
                    }, {
                        cwd: '<%=app.src%>/js/directives',
                        src: '**/*.html',
                        dest: '<%=app.dist%>/partials/directives',
                        expand: true
                    }, {
                        cwd: '<%=app.vendors%>/ksass/src/partials/templates/',
                        src: '**/*',
                        dest: '<%=app.dist%>/partials/templates',
                        expand: true
                    }
                ]
            },
            war: {
                files: [{
                    cwd: '<%=app.dist%>/',
                    src: '**/*',
                    dest: '../cte-api/src/main/webapp/',
                    expand: true
                }]
            }
        },

        /**
         * Minify files with UglifyJS.
         */
        uglify: {
            options: {
                mangle: false,
                sourceMap: false
            },
            build: {
                files: [{
                    expand: true,
                    cwd: '<%=app.src%>/js',
                    src: '**/*js',
                    dest: '<%=app.dist%>/js',
                    ext: '.js',
                    extDot: 'last'
                }]
            }

        },

        /**
         * Concatenate files.
         */
        concat: {
            options: {
                separator: ';'
            },
            libs: {
                src: [
                //vendors js
                '<%=app.vendors%>/keycloak/dist/keycloak.min.js',
                '<%=app.vendors%>/jquery/dist/jquery.min.js',
                '<%=app.vendors%>/select2/dist/js/select2.min.js',
                '<%=app.vendors%>/bootstrap-sass/assets/javascripts/bootstrap.min.js',
                //angular
                '<%=app.vendors%>/angular/angular.min.js',
                '<%=app.vendors%>/angular-route/angular-route.min.js',
                '<%=app.vendors%>/angular-locale/angular-locale_es-py.js',
                "<%=app.vendors%>/ng-tasty/ng-tasty-tpls.min.js",
                "<%=app.vendors%>/ng-file-upload/dist/ng-file-upload-shim.min.js",
                "<%=app.vendors%>/ng-file-upload/dist/ng-file-upload.min.js",
                '<%=app.vendors%>/securityjs/securityjs.js',
                '<%=app.vendors%>/angular-sanitize/angular-sanitize.min.js',
                '<%=app.vendors%>/ng-csv/build/ng-csv.min.js',
                '<%=app.vendors%>/d3/dist/d3.min.js',
                '<%=app.vendors%>/highcharts/highcharts.js',
                '<%=app.vendors%>/html2canvas/dist/html2canvas.min.js',
                '<%=app.vendors%>/tableexport/dist/js/tableexport.min.js',
                /**
                 * Rercursos de la aplicación
                 */
                //app.js
                '<%=app.dist%>/js/common/**/*.js',
                '<%=app.dist%>/js/module.js',
                '<%=app.dist%>/js/app.js',
                // se importan los componetes que son reutilizables.
                '<%=app.vendors%>/ksass/src/js/common/**/*.js',
                '<%=app.vendors%>/ksass/src/js/directives/sidebar-directive.js',
                '<%=app.vendors%>/ksass/src/js/directives/base-directive.js',
                '<%=app.vendors%>/ksass/src/js/directives/util/tabs-directive.js',
                '<%=app.vendors%>/ksass/src/js/filters/**/*.js',
                '<%=app.vendors%>/ksass/src/js/controllers/**/*.js',
                //factory
                '<%=app.dist%>/js/factory/**/*.js',
                //factory
                '<%=app.dist%>/js/filters/**/*.js',
                //services
                '<%=app.dist%>/js/services/**/*.js',
                //directives
                '<%=app.dist%>/js/directives/**/*.js',
                //controllers
                '<%=app.dist%>/js/controllers/**/*.js'
                ],
                dest: '<%=app.dist%>/libs/app.min.js'
            }
        },

        /**
         * Remove directory and files.
         */
        remove: {
            default_options: {
                trace: true,
                dirList: ['<%=app.dist%>/js']
            }
        },

        /**
         * Replace strings on files by using string or regex patters.
         */
        'string-replace': {
            inline: {
                files: {
                    '<%=app.dist%>/': ['<%=app.dist%>/*.html', '<%=app.dist%>/libs/*.js', '<%=app.dist%>/css/*.css'],
                },
                options: {
                    replacements: [{
                        pattern: /{{VERSION}}/g,
                        replacement: '<%= pkg.version %>&t=<%=grunt.template.today("yyyymmdd")%>'
                    }, {
                        pattern:'https://fonts.googleapis.com/css?family=Open+Sans|Roboto',
                        replacement: 'OpenSans.css'
                    }, {
                        pattern:'https://fonts.gstatic.com/s/roboto/v18/KFOmCnqEu92Fr1Mu4mxK.woff2',
                        replacement: '/fonts/KFOmCnqEu92Fr1Mu4mxK.woff2'
                    }, {
                        pattern:'https://fonts.gstatic.com/s/roboto/v18/KFOkCnqEu92Fr1MmgVxIIzI.woff2',
                        replacement: '/fonts/KFOkCnqEu92Fr1MmgVxIIzI.woff2'
                    }]
                }
            }
        },

        /**
         * Adds a simple banner to files.
         */
        usebanner: {
            taskName: {
                options: {
                    position: 'top',
                    banner: '/*!\n' +
                        '  * <%=pkg.name%> : <%=pkg.description%>\n' +
                        '  * @version <%=pkg.version%>\n' +
                        '  * @author <%=pkg.author%>\n' +
                        '  * @date <%=grunt.template.today("yyyy-mm-dd")%>\n' +
                        '  */\n',
                    linebreak: true
                },
                files: {
                    src: ['<%=app.dist%>/**/*.js']
                }
            }
        },
        /**
         * Include angular static partials in the sorce files.
         */
        nginclude: {
            options: {
                discardReferencedFiles: true,
                parserOptions: {
                    decodeEntities: false,
                    removeComments: true,
                    collapseWhitespace: true
                },
            },
            targets: {
                files: [{
                    //expand: true,
                    cwd: '<%=app.dist%>/',
                    src: '**/*.html',
                    dest: '<%=app.dist%>/'
                }]
            }
        },
        /**
         * Minimize html files
         */
        htmlmin: {
            dist: {
                options: {
                    removeComments: true,
                    collapseWhitespace: true
                },
                files: [{
                    expand: true,
                    cwd: '<%=app.dist%>/',
                    src: '**/*.html',
                    dest: '<%=app.dist%>/'
                }]
            }
        },

        /**
         * Run tasks whenever watched files change.
         */
        watch: {
            img: {
                options: {
                    livereload: 12345
                },
                files: ['<%=app.src%>/**/*.png',
                        '<%=app.src%>/**/*.jpg',
                        '<%=app.src%>/**/*.gif',
                        '<%=app.src%>/**/*.eot',
                        '<%=app.src%>/**/*.svg',
                        '<%=app.src%>/**/*.ttf',
                        '<%=app.src%>/**/*.woff',
                        '<%=app.src%>/**/*.woff2'
                    ],
                tasks: ['copy:main', 'string-replace', 'nginclude', 'htmlmin']
            },
            html: {
                options: {
                    livereload: 12345
                },
                files: ['<%=app.src%>/*.html', '<%=app.src%>/**/*.html'],
                tasks: ['copy:main', 'string-replace', 'nginclude', 'htmlmin']
            },
            js: {
                options: {
                    livereload: 12345
                },
                files: ['<%=app.src%>/js/**/*.js'],
                tasks: ['uglify', 'concat', 'string-replace', 'remove', 'usebanner']
            },
            sass: {
                options: {
                    livereload: 12345
                },
                files: ['<%=app.src%>/**/*.scss'],
                tasks: ['copy:main', 'sass', 'string-replace', 'nginclude', 'htmlmin']
            }
        }

    });

    grunt.registerTask('dev', ['uglify', 'sass', "copy:main", 'string-replace', 'concat', 'remove', 'usebanner']);
    grunt.registerTask('default', ['dev', 'htmlmin']);
    grunt.registerTask('serve', ['default', 'configureProxies:server', "open", 'connect:server', 'watch']);
    grunt.registerTask('build', ['default', 'copy:war']);
};
