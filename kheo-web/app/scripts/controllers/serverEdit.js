'use strict';
var module = angular.module('kheoApp');

module.controller('ServerEditCtrl', ['$scope', '$resource', '$routeParams', 'configuration', function ($scope, $resource, $routeParams, configuration) {
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();

    $scope.save = function() {
        $resource(configuration.backend + '/servers/:host', 
                  {host: '@host'}, 
                  {'update': { method: 'PUT'}})
        .update($scope.server);
    };
    
}]);
