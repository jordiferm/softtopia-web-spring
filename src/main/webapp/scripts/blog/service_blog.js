'use strict';

softtopiawebApp.factory('Blog', function ($resource) {
        return $resource('app/rest/blogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

softtopiawebApp.factory('BlogBody', function($resource) {
    return $resource('app/rest/blog-body/:id', {}, {
        'get' : { method: 'GET'}
    });

});