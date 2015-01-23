'use strict';

var module = angular.module('kheoApp');

module.controller('ServerListCtrl', ['$scope', '$resource', function ($scope, $resource) {
    $scope.servers = [];

    $scope.init = function() {
        $scope.servers = $resource('http://localhost:8080/servers').query();
    }

    $scope.delete = function(serverHostname) {
        $resource('http://localhost:8080/servers/' + serverHostname).delete().$promise.then(function() { $scope.init(); })
    }
    
    $scope.init(); 
}]);
