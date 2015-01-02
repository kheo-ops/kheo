'use strict';

/**
 * @ngdoc function
 * @name kheoApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the kheoApp
 */
angular.module('kheoApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
