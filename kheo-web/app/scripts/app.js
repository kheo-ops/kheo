'use strict';

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
        templateUrl: 'views/server-list.html',
        controller: 'ServerListCtrl'
      })
      .when('/servers/new', {
        templateUrl: 'views/server-new.html',
        controller: 'ServerNewCtrl'
      })
      .when('/servers/:hostname/detail', {
        templateUrl: 'views/server-detail.html',
        controller: 'ServerDetailCtrl'
      })
      .when('/servers/:hostname/edit', {
        templateUrl: 'views/server-edit.html',
        controller: 'ServerEditCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
