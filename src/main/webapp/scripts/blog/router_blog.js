'use strict';

softtopiawebApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/blog', {
                    templateUrl: 'views/blogs.html',
                    controller: 'BlogController',
                    resolve:{
                        resolvedBlog: ['Blog', function (Blog) {
                            return Blog.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
