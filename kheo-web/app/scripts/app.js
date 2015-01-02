'use strict';

/**
 * @ngdoc overview
 * @name kheoApp
 * @description
 * # kheoApp
 *
 * Main module of the application.
 */
angular
  .module('kheoApp', [
    'ngResource',
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
