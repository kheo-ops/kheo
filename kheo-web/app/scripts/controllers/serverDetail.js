'use strict';

var module = angular.module('kheoApp');

module.controller('ServerDetailCtrl', ['$scope', '$resource', '$routeParams', 'configuration', '_', function ($scope, $resource, $routeParams, configuration, _) {
    
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();
    $scope.plugins = $resource(configuration.backend + '/plugins').query();
    $scope.pluginProperties = [];

    $scope.filterPluginProperties = function(plugin) {
        $scope.pluginProperties = _.filter($scope.server.serverProperties, function(property) {
          return property.type === plugin.propertyName;
        });
    };

    $scope.getKeys = function(property) {
        return _.reject(_.keys(property), function(element) {
            return element === 'type' || element === '$$hashKey' || element === 'key';
        });
    };

    $scope.stringValue = function(obj) {
        if(obj === null) {
            return 'no value';
        }                        
        var value = _.map(_.filter(_.keys(obj), function(item) {
            return item !== 'type';
        }), function(item) {
            return item + '=' + obj[item];
        });        
        return value.join(', ');
    }
}]);
