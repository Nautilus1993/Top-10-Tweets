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
    }).when('/display1', {
        templateUrl: 'views/display1.html',
        controller: 'DisplayCtrl1'
    }).when('/display2', {
        templateUrl: 'views/display2.html',
        controller: 'DisplayCtrl2'
    }).otherwise({
        redirectTo: '/'
    })
});

app.service('tweetsService', function(){
    var tweetsList = [];

    var addTweets = function(tweets){
        tweetsList = [];
        tweetsList.push(tweets);
    };

    var getTweets = function(){
        return tweetsList;
    };

    return{
        addTweets: addTweets,
        getTweets: getTweets
    };
});

app.controller('UserCtrl', function ($scope, $http, $location, tweetsService) {
    $scope.tweet = {
        done: false
    };

    $scope.ranker1 = function (tweet) {
        console.log(tweet)
        $http.get('/tweet/' + $scope.tweet.uname, $scope.tweet).success(function (data) {
            tweetsService.addTweets(data);
            $location.path('/display1');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    };

    $scope.ranker2 = function (tweet) {
        $http.get('/tweet/rank2/' + $scope.tweet.uname, $scope.tweet).success(function (data) {
            tweetsService.addTweets(data);
            $location.path('/display2');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('DisplayCtrl1', function ($scope, tweetsService) {
    $scope.tweets = tweetsService.getTweets()[0];
    console.log($scope.tweets)
});

app.controller('DisplayCtrl2', function ($scope, tweetsService) {
    $scope.tweets = tweetsService.getTweets()[0];
    console.log($scope.tweets)
});