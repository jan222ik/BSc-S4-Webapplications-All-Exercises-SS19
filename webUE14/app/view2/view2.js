'use strict';

angular.module('myApp.view2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'view2/view2.htm',
    controller: 'View2Ctrl'
  });
}])

.controller('View2Ctrl', [function() {
}])

.controller('studentController', function($scope) {
  $scope.student = {
    firstName: "Mahesh",
    lastName: "Parashar",

    fullName: function() {
      var studentObject;
      studentObject = $scope.student;
      return studentObject.firstName + " " + studentObject.lastName;
    }
  };
});