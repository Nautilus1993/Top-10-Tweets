var app = angular.module('tweetapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
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

app.controller('UserCtrl', function ($scope, $http, $location) {
    $scope.tweet = {
        done: false
    };

    $scope.inputUsername = function (tweet) {
        console.log($scope.tweet);
        $http.get('/tweet/' + $scope.tweet.uname, $scope.tweet).success(function (data) {
            $scope.tweets = data;
            $location.path('/display');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('DisplayCtrl', function ($scope, $http) {

    // $http.get('/tweet/' + $scope.tweet.uname).success(function (data) {
    //     $scope.tweets = data;
    // }).error(function (data, status) {
    //     console.log('Error ' + data)
    // })
    // $http.get('/tweet/' + $scope.tweet.uname, $scope.tweet).success(function (data) {
    //     $scope.tweet = data;
    // }).error(function (data, status) {
    //     console.log('Error ' + data)
    // })
    // $scope.todoStatusChanged = function (tweet) {
    //     console.log(tweet);
    //     $http.put('/api/v1/todos/' + tweet.id, tweet).success(function (data) {
    //         console.log('status changed');
    //     }).error(function (data, status) {
    //         console.log('Error ' + data)
    //     })
    // }
});