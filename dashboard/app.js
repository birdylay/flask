(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);



    var accountSid = "SK6598372da437d3b91b312d481f6ecc45";
    var authToken = "RNn7dW991R20u2PQyNLWEBqDRFICMXVh";
    var client = require('node_modules/twilio')(accountSid, authToken);

client.messages.create({
    body: "Don’t forget to finish project 2!",
    to: "+16475398669", // Change to your verified phone number
    from: "+16135192924" // Change to Twilio phone number
}, function(err, message) {
    process.stdout.write(message.sid);
});

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home/home.view.html',
                controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register/register.view.html',
                controllerAs: 'vm'
            })

            .when('/quiz', {
                controller: 'QuizController',
                templateUrl: 'quiz/quiz.view.html',
                controllerAs: 'vm'
            })

            .when('/marked', {
                controller: 'MarkedController',
                templateUrl: 'marked/marked.view.html',
                controllerAs: 'vm'
            })

            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();