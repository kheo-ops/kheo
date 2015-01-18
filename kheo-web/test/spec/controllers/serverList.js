'use strict';

describe('Controller: ServersCtrl', function () {

  // load the controller's module
  beforeEach(module('kheoApp'));

  var ServersCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ServersCtrl = $controller('ServersCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
