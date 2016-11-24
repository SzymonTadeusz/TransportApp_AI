(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('DriverDetailController', DriverDetailController);

    DriverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Driver'];

    function DriverDetailController($scope, $rootScope, $stateParams, previousState, entity, Driver) {
        var vm = this;

        vm.driver = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aiProjektApp:driverUpdate', function(event, result) {
            vm.driver = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
