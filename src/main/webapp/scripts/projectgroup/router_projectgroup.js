'use strict';

softtopiawebApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/projectgroup', {
                    templateUrl: 'views/projectgroups.html',
                    controller: 'ProjectGroupController',
                    resolve:{
                        resolvedProjectGroup: ['ProjectGroup', function (ProjectGroup) {
                            return ProjectGroup.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                })
        });
