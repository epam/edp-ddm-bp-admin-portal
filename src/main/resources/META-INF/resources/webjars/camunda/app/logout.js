'use strict';
define('custom-logout', ['angular'], function (angular) {
  var customLogoutModule = angular.module('custom-logout', []).run(
      ['$rootScope', function ($rootScope) {
        $rootScope.$on('$viewContentLoaded', function (event) {
          var div = document.querySelector("[cam-widget-header]");
          var jQueryKey = Object.getOwnPropertyNames(div)[0];
          var $isolateScope = div[jQueryKey]['$isolateScope'];
          $isolateScope.logout = function () {

            window.location.href = "logout"
          }
        })
      }]);
});