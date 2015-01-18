'use strict';

describe('Controller: ServernewCtrl', function () {

  // load the controller's module
  beforeEach(module('kheoApp'));

  var ServernewCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ServernewCtrl = $controller('ServernewCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
