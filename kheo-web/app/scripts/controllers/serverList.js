'use strict';

var module = angular.module('kheoApp');

module.controller('ServerListCtrl', ['$scope', '$resource', 'configuration', function ($scope, $resource, configuration) {
    $scope.servers = [];

    $scope.init = function() {
        $scope.servers = $resource(configuration.backend + '/servers').query();
    };

    $scope.delete = function(serverHostname) {
        $resource(configuration.backend + '/servers/' + serverHostname).delete().$promise.then(function() { $scope.init(); });
    };
    
    $scope.init(); 
}]);
