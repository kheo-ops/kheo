'use strict';

var module = angular.module('kheoApp');

module.controller('ServerNewCtrl', ['$scope', '$resource', function ($scope, $resource) {
  $scope.server = {};

  $scope.save = function() {
  	$resource('http://localhost:8080/servers').save($scope.server);
  };  
}]);