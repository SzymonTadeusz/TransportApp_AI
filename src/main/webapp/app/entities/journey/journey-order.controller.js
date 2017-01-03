/**
 * Created by jakub on 02.01.17.
 */
(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('JourneyOrderController', JourneyOrderController)
        .directive('starRating', starRating);

    JourneyOrderController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'Journey', 'Car'];
    function JourneyOrderController ($timeout, $scope, $stateParams, $uibModalInstance, Journey, Car) {
        var vm = this;
        vm.clear = clear;

        vm.location = "";
        $scope.counter = Math.floor(Math.random() * 60 + 60);
        vm.rating = 3;
        vm.car = {};
        vm.order = order;
        var stopped;
        $scope.showCounter = false;
        $scope.showRating = false;

        $scope.countdown = function() {
            stopped = $timeout(function() {
                $scope.counter--;

                if($scope.counter == 0) {
                    $scope.counter = "Koniec czasu";
                    $scope.showCounter = false;
                    $scope.showRating = true;
                }
                else {
                    $scope.countdown();
                }

            }, 1000);
        };

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function order() {
            if(!$scope.showCounter){
                $.ajax({
                    url: 'api/cars/',
                    type: 'GET',
                    success: function (data) {
                        vm.car = data[Math.floor(Math.random() * (data.length - 1))];
                        $scope.showCounter = true;
                        $scope.countdown();
                    }
                });
            }
        }

    }
    function starRating() {

        return {
            restrict : 'A',
            template : '<ul class="rating">'
            + '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
            + '\u2605'
            + '</li>'
            + '</ul>',
            scope : {
                ratingValue : '=',
                max : '=',
                onRatingSelected : '&'
            },
            link : function(scope, elem, attrs) {
                var updateStars = function() {
                    scope.stars = [];
                    for ( var i = 0; i < scope.max; i++) {
                        scope.stars.push({
                            filled : i < scope.ratingValue
                        });
                    }
                };

                scope.toggle = function(index) {
                    scope.ratingValue = index + 1;
                    scope.onRatingSelected({
                        rating : index + 1
                    });
                };

                scope.$watch('ratingValue',
                    function(oldVal, newVal) {
                        if (newVal) {
                            updateStars();
                        }
                    }
                );
            }
        };
    }
})();
