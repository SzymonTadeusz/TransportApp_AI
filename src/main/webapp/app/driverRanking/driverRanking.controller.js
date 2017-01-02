/**
 * Created by jakub on 02.01.17.
 */
(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('DriverController', DriverController);

    DriverController.$inject = ['$scope', '$state'];
    function DriverController ($scope, $state) {
        var vm = this;
        vm.ranking = [];
        getRanking();

        function getRanking() {
            $.ajax({
                url: "api/driverRanking",
                type: "GET",
                success: function (response) {
                    vm.ranking = response;
                }
            })
        }
    }


})();
