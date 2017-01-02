(function () {
    'use strict';

    angular
        .module('aiProjektApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController($scope, Principal, LoginService, $state) {
        var vm = this;
        $scope.myInterval = 8000;
        $scope.noWrapSlides = false;
        $scope.active = 0;
        var slides = $scope.slides = [];
        var currIndex = 0;

        slides.push({
                image: "../../content/images/slider/bg1.jpg",
                title: "Sample title 1",
                text: "Sample text 1",
                id: currIndex++
            },
            {
                image: "../../content/images/slider/bg2.jpg",
                title: "Sample title 2",
                text: "Sample text 2",
                id: currIndex++
            });

        console.log(slides);

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }
    }
})();
