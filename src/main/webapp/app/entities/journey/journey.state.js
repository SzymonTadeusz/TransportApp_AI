(function() {
    'use strict';

    angular
        .module('aiProjektApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('journey', {
            parent: 'entity',
            url: '/journey',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Journeys'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/journey/journeys.html',
                    controller: 'JourneyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('journey-order', {
            parent: 'app',
            url: '/order',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/journey/journey-order.html',
                    controller: 'JourneyOrderController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                }).result.then(function() {
                    $state.go('home');
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('journey-detail', {
            parent: 'entity',
            url: '/journey/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Journey'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/journey/journey-detail.html',
                    controller: 'JourneyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Journey', function($stateParams, Journey) {
                    return Journey.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'journey',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('journey-detail.edit', {
            parent: 'journey-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/journey/journey-dialog.html',
                    controller: 'JourneyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Journey', function(Journey) {
                            return Journey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('journey.new', {
            parent: 'journey',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/journey/journey-dialog.html',
                    controller: 'JourneyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                rating: null,
                                taxi: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('journey', null, { reload: 'journey' });
                }, function() {
                    $state.go('journey');
                });
            }]
        })
        .state('journey.edit', {
            parent: 'journey',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/journey/journey-dialog.html',
                    controller: 'JourneyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Journey', function(Journey) {
                            return Journey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('journey', null, { reload: 'journey' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('journey.delete', {
            parent: 'journey',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/journey/journey-delete-dialog.html',
                    controller: 'JourneyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Journey', function(Journey) {
                            return Journey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('journey', null, { reload: 'journey' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
