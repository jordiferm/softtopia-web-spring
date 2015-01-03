'use strict';

softtopiawebApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/technology', {
                    templateUrl: 'views/technologys.html',
                    controller: 'TechnologyController',
                    resolve:{
                        resolvedTechnology: ['Technology', function (Technology) {
                            return Technology.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
