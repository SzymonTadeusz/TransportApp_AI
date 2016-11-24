(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('DriverDialogController', DriverDialogController);

    DriverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Driver'];

    function DriverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Driver) {
        var vm = this;

        vm.driver = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.driver.id !== null) {
                Driver.update(vm.driver, onSaveSuccess, onSaveError);
            } else {
                Driver.save(vm.driver, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aiProjektApp:driverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
