'use strict';

softtopiawebApp.controller('ProjectGroupController', function ($scope, resolvedProjectGroup, ProjectGroup, RestangularProjectGroup) {

        $scope.projectgroups = resolvedProjectGroup;

        init();

        function init() {

            RestangularProjectGroup.projectGroups.getList().then(function(groups) {
               groups.map(function(group) {
                  var res = group;
                   res.projects = RestangularProjectGroup.projectGroup(group.id).projects.getList().$object;
               });
                $scope.projecGroupsWithProjects = groups;
            });

        }

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
