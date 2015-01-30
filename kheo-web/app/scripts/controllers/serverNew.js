'use strict';

var module = angular.module('kheoApp');

module.controller('ServerNewCtrl', ['$scope', '$resource', '$location', 'configuration', function ($scope, $resource, $location, configuration) {
    $scope.server = {};

    $scope.save = function() {
        if($scope.server.host === undefined) {
            return;
        }

        $resource(configuration.backend + '/servers').save($scope.server, function() { $location.path('servers'); });
    };  
}]);