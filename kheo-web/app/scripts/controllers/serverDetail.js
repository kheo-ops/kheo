'use strict';
var module = angular.module('kheoApp');

module.controller('ServerDetailCtrl', ['$scope', '$resource', '$routeParams', 'configuration', function ($scope, $resource, $routeParams, configuration) {
    $scope.plugins = $resource(configuration.backend + '/plugins').query();
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();
    $scope.pluginProperties = [];

    $scope.filterPluginProperties = function(plugin) {    	
    	$scope.pluginProperties = _.filter($scope.server.serverProperties, function(property) {
          return property.type == plugin.propertyName;
    	});    	    	
    }

    $scope.extractKeyValue = function(property) {
    	var res = [];
    	for(var key in property) {            
            var keyValue = { 
              key: key,
              value: property[key]
            };
            res.push(keyValue);
    	}
    	return res;
    }
}]);
