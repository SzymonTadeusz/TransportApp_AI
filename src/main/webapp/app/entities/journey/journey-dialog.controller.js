(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('JourneyDialogController', JourneyDialogController);

    JourneyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Journey', 'Car'];

    function JourneyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Journey, Car) {
        var vm = this;

        vm.journey = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cars = [];
        getCars();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            console.log(JSON.stringify(vm.journey));
            if (vm.journey.id !== null) {
                Journey.update(vm.journey, onSaveSuccess, onSaveError);
            } else {
                Journey.save(vm.journey, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aiProjektApp:journeyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function getCars() {
            Car.query(function(result) {
                vm.cars = result;
            });
        }
    }
})();
