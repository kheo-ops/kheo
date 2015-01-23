'use strict';

var module = angular.module('kheoApp');

module.controller('ServerNewCtrl', ['$scope', '$resource', '$location', function ($scope, $resource, $location) {
    $scope.server = {};

    $scope.save = function() {
        if($scope.server.host === undefined) {
            return;
        }

        $resource('http://localhost:8080/servers').save($scope.server, function() { $location.path('servers'); });
    };  
}]);