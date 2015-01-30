'use strict';
var module = angular.module('kheoApp');

module.controller('ServerEditCtrl', ['$scope', '$resource', '$routeParams', function ($scope, $resource, $routeParams) {
    $scope.server = $resource('http://localhost:8080/servers/' + $routeParams.hostname).get();

    $scope.save = function() {
        $resource('http://localhost:8080/servers/:host', 
                  {host: '@host'}, 
                  {'update': { method: 'PUT'}})
        .update($scope.server);
    };
    
}]);
