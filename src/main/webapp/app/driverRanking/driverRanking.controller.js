/**
 * Created by jakub on 02.01.17.
 */
(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('DriverRankingController', DriverRankingController);

    DriverRankingController.$inject = ['$scope', '$state'];
    function DriverRankingController ($scope, $state) {
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
