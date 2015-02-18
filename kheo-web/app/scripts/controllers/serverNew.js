'use strict';

var module = angular.module('kheoApp');

module.controller('ServerNewCtrl', ['$scope', '$resource', '$location', 'configuration', function ($scope, $resource, $location, configuration) {    
    $scope.plugins = $resource(configuration.backend + '/plugins').query();
    $scope.server = {};

    $scope.save = function() {
        if($scope.server.host === undefined) {
            return;
        }
        $resource(configuration.backend + '/servers').save($scope.server, function() { $location.path('servers'); });
    };

    $scope.getKeys = function(property) {
        return _.reject(_.keys(property), function(element) {
            return element === 'type' || element === '$$hashKey' || element === 'key';
        });
    }

    $scope.initSettings = function() {
        if($scope.server[discoverySettings] === undefined) {
            $scope.server.discoverySettings = {};
            for(var plugin in $scope.plugins) {            	
                $scope.discoverySettings[plugin.name] = true;
            }
        }
    };
}]);