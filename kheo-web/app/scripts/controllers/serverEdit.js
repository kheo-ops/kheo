'use strict';
var module = angular.module('kheoApp');

module.controller('ServerEditCtrl', ['$scope', '$resource', '$routeParams', function ($scope, $resource, $routeParams) {
  $scope.server = $resource('http://localhost:8080/servers/' + $routeParams.hostname).get();

  $scope.save = function() {
    $resource('http://localhost:8080/servers/:hostname', null, {'update': { method: 'PUT' }}).update({hostname: $scope.server.hostname}, $scope.server);
  }
}]);
