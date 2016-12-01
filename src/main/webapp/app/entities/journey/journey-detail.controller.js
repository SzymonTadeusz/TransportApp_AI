(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('JourneyDetailController', JourneyDetailController);

    JourneyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Journey'];

    function JourneyDetailController($scope, $rootScope, $stateParams, previousState, entity, Journey) {
        var vm = this;

        vm.journey = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aiProjektApp:journeyUpdate', function(event, result) {
            vm.journey = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
