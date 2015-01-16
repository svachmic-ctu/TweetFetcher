/* global angular */
'use strict'; // jshint ignore:line


var app = angular.module('gdg', ['lumx']);
var is_demo = false;

app.controller('AppController', function($scope, Layout, LxDialogService, LxNotificationService, $http)
{   
    $scope.Layout = Layout;

    $scope.data = {
        filter: undefined
    };
    $scope.selectedFile = null;

    var tweet_fetcher_api = 'api/';
    
    $scope.updateTweets = function(){
      $http.get(is_demo ? 'demo/updatadata' : (tweet_fetcher_api + 'get/data?action=updatedata'))
	.success(function(data){
	  if($scope.selectedFile) fetchTweets();
	  LxNotificationService.info('Tweets updated, ' + data.cnt + " tweets added.");
	});;
    };

    var fetchTweets = function(){
        var url = is_demo ? 'demo/listall' : (tweet_fetcher_api + 'get/tweet?action=listall');
	if($scope.selectedFile && $scope.selectedFile.id != -1) 
	  url = is_demo ? 'demo/listtweets' : 
          (tweet_fetcher_api + 'get/tweet?action=listtweets&userid=' + parseInt($scope.selectedFile.id));
	
        $http.get(url)
	  .success(function(data){
	      $scope.tweets = data;
	  });
    };

    $http.get(is_demo ? 'demo/listusers' : (tweet_fetcher_api + 'get/user?action=listusers')).
        success(function(data) {
            $scope.files = [{
	      id : -1,
	      name: 'All tweets',
	    }].concat(data);
	    
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