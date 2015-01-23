'use strict';
var module = angular.module('kheoApp');

module.controller('ServerDetailCtrl', ['$scope', '$resource', '$routeParams', function ($scope, $resource, $routeParams) {
    $scope.server = $resource('http://localhost:8080/servers/' + $routeParams.hostname).get();   
}]);
