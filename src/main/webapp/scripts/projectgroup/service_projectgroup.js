'use strict';

softtopiawebApp.factory('ProjectGroup', function ($resource) {
        return $resource('app/rest/projectgroups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

softtopiawebApp.factory('RestangularProjectGroup', function (Restangular) {

    var service = {};

    service.projectGroups = Restangular.all('projectgroups');

    service.projectGroup = function (projectGroupId) {
        var res = Restangular.one('projectgroups', projectGroupId);
        res.projects = res.all('projects');
        return res;
    }

    return service;
});
