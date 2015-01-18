'use strict';

var module = angular.module('kheoApp');

module.controller('ServerListCtrl', ['$scope', '$resource', function ($scope, $resource) {
    $scope.servers = [];

    $scope.init = function() {
        $scope.servers = $resource('http://localhost:8080/servers').query();
    }
    
    $scope.init(); 
}]);
