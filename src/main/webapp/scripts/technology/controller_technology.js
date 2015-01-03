'use strict';

softtopiawebApp.controller('TechnologyController', function ($scope, resolvedTechnology, Technology) {

        $scope.technologys = resolvedTechnology;

        $scope.create = function () {
            Technology.save($scope.technology,
                function () {
                    $scope.technologys = Technology.query();
                    $('#saveTechnologyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.technology = Technology.get({id: id});
            $('#saveTechnologyModal').modal('show');
        };

        $scope.delete = function (id) {
            Technology.delete({id: id},
                function () {
                    $scope.technologys = Technology.query();
                });
        };

        $scope.clear = function () {
            $scope.technology = {description: null, postId: null, id: null, url: null};
        };
    });
