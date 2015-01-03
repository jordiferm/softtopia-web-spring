'use strict';

softtopiawebApp.factory('Technology', function ($resource) {
        return $resource('app/rest/technologys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

softtopiawebApp.factory('TechnologyBody', function($resource) {
    return $resource('app/rest/technology-body/:id', {}, {
        'get' : { method: 'GET'}
    });

});