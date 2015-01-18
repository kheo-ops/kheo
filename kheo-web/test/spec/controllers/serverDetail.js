'use strict';

describe('Controller: ServerCtrl', function () {

  // load the controller's module
  beforeEach(module('kheoApp'));

  var ServerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ServerCtrl = $controller('ServerCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
