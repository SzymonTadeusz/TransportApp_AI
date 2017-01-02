/**
 * Created by jakub on 02.01.17.
 */
(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('DriverController', DriverController);

    DriverController.$inject = ['$scope', '$state'];
    console.log("test");
    function DriverController ($scope, $state) {
        var vm = this;
        vm.ranking = [];
        getRanking();

        function getRanking() {
            $.ajax({
                url: "api/driverRanking",
                type: "GET",
                data: {howMany: 10},
                success: function (response) {
                    vm.ranking = response;
                }
            })
        }
    }


})();
