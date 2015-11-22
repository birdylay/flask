(function () {
    'use strict';

    angular
    	.module('app')
    	.controller('QuizController', function($scope) {

  $scope.choices = [{id: 'quest1'}, {id: 'quest2'}];
  $scope.addNewChoice = function() {
    var newItemNo = $scope.choices.length+1;
    $scope.choices.push({'id':'quest'+newItemNo});
  };
    
  $scope.removeChoice = function() {
    var lastItem = $scope.choices.length-1;
    $scope.choices.splice(lastItem);
  };
  
});
  



})();
