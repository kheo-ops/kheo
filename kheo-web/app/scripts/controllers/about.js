'use strict';

/**
 * @ngdoc function
 * @name kheoApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the kheoApp
 */
angular.module('kheoApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });