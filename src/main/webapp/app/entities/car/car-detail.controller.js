(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('CarDetailController', CarDetailController);

    CarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car'];

    function CarDetailController($scope, $rootScope, $stateParams, previousState, entity, Car) {
        var vm = this;

        vm.car = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aiProjektApp:carUpdate', function(event, result) {
            vm.car = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
