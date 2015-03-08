'use strict';

var module = angular.module('kheoApp');

module.controller('ServerDetailCtrl', ['$scope', '$resource', '$routeParams', 'configuration', '_', function ($scope, $resource, $routeParams, configuration, _) {
    
    $scope.privateCount = 0;
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();
    $scope.plugins = $resource(configuration.backend + '/plugins').query();
    $scope.pluginProperties = [];

    $scope.filterPluginProperties = function(plugin) {
        $scope.pluginProperties = _.filter($scope.server.serverProperties, function(property) {
          return property.type === plugin.propertyName;
        });
    };

    $scope.getKeys = function(property) {
        console.log(property)
        return _.reject(_.keys(property), function(element) {            
            return element === 'type' || element === '$$hashKey' || element === 'key' || element === '@kheo-type';
        });
    };

    $scope.stringValue = function(obj) {
        if(obj === null) {
            return 'no value';
        }                        
        var value = _.map(_.filter(_.keys(obj), function(item) {
            return item !== 'type' && item !== '@kheo-type';
        }), function(item) {
            return item + '=' + obj[item];
        });        
        return value.join(', ');
    };

    $scope.getEventAlignment = function() {
        console.log($scope.privateCount);
        $scope.privateCount = $scope.privateCount+1;
        return $scope.privateCount % 2 == 0 ? 'left' : 'right';
    }
}]);
