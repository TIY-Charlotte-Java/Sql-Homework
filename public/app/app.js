'use strict';

angular.module('restaurants', ['ngRoute', 'restaurants.services', 'restaurants.controllers']) //'restaurants.filters', 'restaurants.directives'
    .config(['$routeProvider', '$httpProvider', 
        function($routeProvider, $httpProvider) {
            $routeProvider.when('/restaurants',             {templateUrl: 'views/restaurants.html',       controller: 'RestaurantListController'});
            $routeProvider.when('/restaurant-detail/:id',   {templateUrl: 'views/restaurant-detail.html', controller: 'RestaurantDetailController'});
            $routeProvider.when('/restaurant-create',       {templateUrl: 'views/restaurant-create.html', controller: 'RestaurantCreationController'});
            $routeProvider.otherwise({redirectTo:'/restaurants'});

            $httpProvider.defaults.useXDomain = true;
            delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }]); 