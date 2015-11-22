(function () {
    'use strict';
    

    angular
    	.module('app')
    	.controller('MarkedController', function ($scope, $http) {
        $scope.rowCollection = [];

   $http.get('/marked/marked.json').success(function(data) {
       $scope.rowCollection = data;
   });
});
  

})();
