/**
 * Created by jakub on 02.01.17.
 */
(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
            .state('driverRanking', {
                parent: 'app',
                url: '/driverRanking',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Driver Ranking'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/driverRanking/driverRanking.html',
                        controller: 'DriverController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
    }
})();
