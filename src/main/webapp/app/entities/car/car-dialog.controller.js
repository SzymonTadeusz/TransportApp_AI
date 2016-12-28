(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car', 'Driver'];

    function CarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car, Driver) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.save = save;
        vm.drivers = [];

        getDrivers();
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aiProjektApp:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        function getDrivers() {
            Driver.query(function(result) {
                vm.drivers = result;
            });
        }
    }
})();
