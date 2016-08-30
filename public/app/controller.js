'use strict';

var app = angular.module('restaurants.controllers', []);

app.controller('RestaurantListController', ['$scope', 'RestaurantFactory', '$location', 
    function($scope, RestaurantFactory, $location) {
        //callback for editRestaurant() [ng-click]
        $scope.editRestaurant = function(restaurantId) {
            $location.path('/restaurant-detail/' + restaurantId);
        };

        //callback for deleteRestaurant() [ng-click]
        $scope.deleteRestaurant = function(restaurantId) {
            RestaurantFactory.delete({ id: restaurantId }).then(function() {
                $scope.restaurants = RestaurantFactory.getAll();
            });
        };

        //callback for createRestaurant() [ng-click]
        $scope.createRestaurant = function() {
            $location.path('/restaurant-create');
        };

        $scope.restaurants = RestaurantFactory.getAll();
    }
]);


app.controller('RestaurantDetailController', ['$scope', '$routeParams', 'RestaurantFactory', '$location',
    function($scope, $routeParams, RestaurantFactory, $location) {
        //callback for updateRestaurant [ng-click]
        $scope.updateRestaurant = function() {
            RestaurantFactory.update($scope.restaurant).then(function() {
                $location.path('/restaurants');
            });
        }

        //callback for cancel [ng-click]
        $scope.cancel = function() {
            $location.path('/restaurants');
        }
        $scope.restaurant = RestaurantFactory.get($routeParams.id);//{id: $routeParams.id});
    }
]);


app.controller('RestaurantCreationController', ['$scope', 'RestaurantFactory', '$location', 
    function($scope, RestaurantFactory, $location) {

        //callback for createNewRestaurant() [ng-click]
        $scope.createNewRestaurant = function() {
            RestaurantFactory.create($scope.restaurant).then(function() {      
                $location.path('/restaurants');
            });
        }

}]);