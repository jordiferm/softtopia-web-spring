'use strict';

softtopiawebApp.controller('BlogController', function ($scope, resolvedBlog, Blog) {

        $scope.blogs = resolvedBlog;

        $scope.create = function () {
            Blog.save($scope.blog,
                function () {
                    $scope.blogs = Blog.query();
                    $('#saveBlogModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.blog = Blog.get({id: id});
            $('#saveBlogModal').modal('show');
        };

        $scope.delete = function (id) {
            Blog.delete({id: id},
                function () {
                    $scope.blogs = Blog.query();
                });
        };

        $scope.clear = function () {
            $scope.blog = {description: null, postId: null, id: null};
        };
    });
