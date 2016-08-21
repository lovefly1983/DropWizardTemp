'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [])
    .controller('IndexCtrl', function ($scope, $http) {
    $scope.indexes = [];

    $scope.initIndexes = function () {
        $http.get('/comp/status').success(function (data) {
            data.name = data.status;
            $scope.indexes = [data];
        });
    };
});
