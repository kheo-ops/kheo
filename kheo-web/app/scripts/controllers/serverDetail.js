'use strict';
var module = angular.module('kheoApp');

module.controller('ServerDetailCtrl', ['$scope', '$resource', '$routeParams', 'configuration', function ($scope, $resource, $routeParams, configuration) {
    $scope.server = $resource(configuration.backend + '/servers/' + $routeParams.hostname).get();   
}]);
