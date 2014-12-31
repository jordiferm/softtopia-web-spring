'use strict';

softtopiawebApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/project', {
                    templateUrl: 'views/projects.html',
                    controller: 'ProjectController',
                    resolve:{
                        resolvedProject: ['Project', function (Project) {
                            return Project.query();
                        }],
                        resolvedProjectGroups: ['ProjectGroup', function(ProjectGroup) {
                            return ProjectGroup.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                })
        });
