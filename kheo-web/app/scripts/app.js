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
      .when('/contact', {
        templateUrl: 'views/contact.html',
        controller: 'ContactCtrl'
      })
      .when('/servers', {
        templateUrl: 'views/servers.html',
        controller: 'ServersCtrl'
      })
      .when('/servers/:hostname', {
        templateUrl: 'views/server.html',
        controller: 'ServerCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
