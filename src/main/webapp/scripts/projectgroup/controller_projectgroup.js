'use strict';

softtopiawebApp.controller('ProjectGroupController', function ($scope, resolvedProjectGroup, ProjectGroup) {

        $scope.projectgroups = resolvedProjectGroup;

        $scope.create = function () {
            ProjectGroup.save($scope.projectgroup,
                function () {
                    $scope.projectgroups = ProjectGroup.query();
                    $('#saveProjectGroupModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.projectgroup = ProjectGroup.get({id: id});
            $('#saveProjectGroupModal').modal('show');
        };

        $scope.delete = function (id) {
            ProjectGroup.delete({id: id},
                function () {
                    $scope.projectgroups = ProjectGroup.query();
                });
        };

        $scope.clear = function () {
            $scope.projectgroup = {description: null, id: null};
        };
    });
