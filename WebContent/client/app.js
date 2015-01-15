/* global angular */
'use strict'; // jshint ignore:line


var app = angular.module('gdg', ['lumx']);
var is_demo = true;

app.controller('AppController', function($scope, Layout, LxDialogService, LxNotificationService, $http)
{   
    $scope.Layout = Layout;

    $scope.data = {
        filter: undefined
    };


    //http://localhost:8080/TweetFetcher/api/get/user?action=listusers 
    //http://localhost:8080/TweetFetcher/api/get/tweet?action=listtweets&userid=1 
    //http://localhost:8080/TweetFetcher/api/get/tweet?action=listall 
    //http://localhost:8080/TweetFetcher/api/get/data?action=updatedata

    var fetchTweets = function(){
        $http.get(is_demo ? 'demo/listtweets' : ('TweetFetcher/api/get/tweet?action=listtweets&userid=' + parseInt($scope.selectedFile.id)))
	  .success(function(data){
	      $scope.tweets = data;
	  });
    };

    $http.get(is_demo ? 'demo/listusers' : '/TweetFetcher/api/get/user?action=listusers').
        success(function(data) {
	    
            //$scope.greeting = data;
            console.log(data);

            $scope.files = data;
	    
	    $scope.selectFile = function(file)
            {
                $scope.selectedFile = file;
		fetchTweets();
                Layout.openPanel();
            };

        });
});

app.service('Layout', function()
{
    var service = {
        sidebarIsOpened: false,
        panelIsOpened: false
    };

    /*
     * Toggle visibility of the sidebar.
     */
    service.toggleSidebar = function()
    {
        service.sidebarIsOpened = !service.sidebarIsOpened;
    };

    /*
     * Open sidebar.
     */
    service.openSidebar = function()
    {
        service.sidebarIsOpened = true;
    };

    /*
     * Close sidebar.
     */
    service.closeSidebar = function()
    {
        service.sidebarIsOpened = false;
    };

    /*
     * Toggle visibility of the panel.
     */
    service.togglePanel = function()
    {
        service.panelIsOpened = !service.panelIsOpened;
    };

    /*
     * Open panel.
     */
    service.openPanel = function()
    {
        service.panelIsOpened = true;
    };

    /*
     * Close panel.
     */
    service.closePanel = function()
    {
        service.panelIsOpened = false;
    };

    return service;
});