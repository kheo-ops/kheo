'use strict';
var module = angular.module('kheoApp');

module.controller('ServerEditCtrl', ['$scope', '$resource', '$routeParams', 'configuration', function ($scope, $resource, $routeParams, configuration) {
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();

    $scope.save = function() {
    	$scope.server.serverProperties = [];
    	$scope.server.eventLog = [];

        $resource(configuration.backend + '/servers/:host', 
                  {host: '@host'}, 
                  {'update': { method: 'PUT'}})
        .update($scope.server);
    };

    $scope.getKeys = function(property) {
        return _.reject(_.keys(property), function(element) {
            return element === 'type' || element === '$$hashKey' || element === 'key';
        });
    }
    
}]);
