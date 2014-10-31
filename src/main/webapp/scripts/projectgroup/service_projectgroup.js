'use strict';

softtopiawebApp.factory('ProjectGroup', function ($resource) {
        return $resource('app/rest/projectgroups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
