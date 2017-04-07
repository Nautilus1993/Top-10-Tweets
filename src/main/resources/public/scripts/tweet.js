var tweet = angular.module('tweetapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

tweet.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl'
    }).when('/display', {
        templateUrl: 'views/display.html',
        controller: 'DisplayCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

tweet.controller('UserCtrl', function ($scope, $http, $location) {
    $scope.tweet = {
        done: false
    };

    $scope.inputUsername = function (tweet) {
        console.log($scope.tweet);
        $http.get('/tweet/', $scope.tweet).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

tweet.controller('DisplayCtrl', function ($scope, $http) {
    $http.get('/api/v1/todos').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

    $scope.todoStatusChanged = function (tweet) {
        console.log(tweet);
        $http.put('/api/v1/todos/' + tweet.id, tweet).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});