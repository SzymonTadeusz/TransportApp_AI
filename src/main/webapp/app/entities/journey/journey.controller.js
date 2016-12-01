(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('JourneyController', JourneyController);

    JourneyController.$inject = ['$scope', '$state', 'Journey'];

    function JourneyController ($scope, $state, Journey) {
        var vm = this;
        
        vm.journeys = [];

        loadAll();

        function loadAll() {
            Journey.query(function(result) {
                vm.journeys = result;
            });
        }
    }
})();
