'use strict';

softtopiawebApp.factory('Project', function ($resource) {
        return $resource('app/rest/projects/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

softtopiawebApp.factory('ProjectBody', function($resource) {
   return $resource('app/rest/project-body/:id', {}, {
       'get' : { method: 'GET'}
   });

});