'use strict';

var services = angular.module('restaurants.services', ['ngResource']);

services.factory('RestaurantFactory', function ($http) {
    return {
        create: function (restaurant) {
            return $http({
                method: 'POST',
                url: '/api/restaurants',
                data: restaurant,
            });
        },
        get: function(id){
            let data = {};
            $http({
                method: 'GET',
                url: '/api/restaurant/'+id
            }).then(function(info) {
                angular.copy(info.data, data);
            })
            return data;
        },
        getAll: function () {
            let spots = [];

            $http({
                method: 'GET',
                url: '/api/restaurants',
            }).then(function (info) {
                // Special function that copies an array and then notifies angular that it needs to re-render
                angular.copy(info.data, spots);
            });

            return spots;
        },
        update: function(restaurant){
            return $http({
                method: 'PUT',
                url: '/api/restaurant/'+restaurant.id,
                data: JSON.stringify(restaurant),
            })
        },
        delete: function(restaurant){
            return $http({
                method: 'DELETE',
                url: '/api/restaurant/'+restaurant.id,
            })
        }
    };
});

// services.factory('RestaurantsFactory', function($http){
//     // return $resource('/api/restaurants', {}, {
//     //     query: {
//     //         method: 'GET',
//     //         isArray: true
//     //     },
//     //     create: {
//     //         method: 'POST'
//     //     }
//     // })
// });

// services.factory('RestaurantFactory', function($http){
//     // return $resource('/api/restaurant/:id', {}, {
//     //     show: {
//     //         method: 'GET'
//     //     },
//     //     update: {
//     //         method: 'PUT',
//     //         params: {
//     //             id: '@id'
//     //         }
//     //     },
//     //     delete: {
//     //         method: 'DELETE',
//     //         params: {
//     //             id: '@id'
//     //         }
//     //     }
//     // })
// });