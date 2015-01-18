/* global angular */
'use strict'; // jshint ignore:line


var app = angular.module('gdg', ['lumx']);
var is_demo = false;

app.controller('AppController', function($scope, Layout, LxDialogService, LxNotificationService, $http) {   
    $scope.Layout = Layout;

    $scope.data = {
        filter: undefined
    };

    $scope.selectedUser = null;
    $scope.userDialog = {nick: null}

    var tweet_fetcher_api = 'var/api/';

    var fetchUsers = function() {
    	var url = is_demo ? 'demo/listusers' : (tweet_fetcher_api + 'get/user?action=list');

        $http.get(url).success(function(data) {
        	$scope.users = data;

    	    $scope.selectUser = function(user) {
    	    	$scope.selectedUser = user;
    	    	fetchTweets();
    	    	Layout.openPanel();
    	    };

        });
    };

    var fetchTweets = function() {
    	var url = is_demo ? 'demo/listtweets' : (tweet_fetcher_api + 'get/tweet?action=list&id=' + parseInt($scope.selectedUser.id));

    	$http.get(url).success(function(data) {
    		$scope.tweets = data;
    	});
    };    

    $scope.updateData = function() {
    	var url = is_demo ? 'demo/updatadata' : (tweet_fetcher_api + 'post/data?action=update');
    	$http.post(url).success(function(data) {
    		if($scope.selectedUser) {
    			fetchTweets();
    		}
    		LxNotificationService.info("Tweets updated, " + data.cnt + " tweets added.");
    		fetchUsers();
    	});
    };

    $scope.addUser = function() {
    	var url = (tweet_fetcher_api + 'post/user?action=add&nick=' + $scope.userDialog.nick);
    	$http.post(url).success(function(data) {
    		var ret = parseInt(data.cnt);
    		if(ret == 1) {
    			LxNotificationService.info("User @" + $scope.userDialog.nick + " has been succesfully added.");
    			LxDialogService.close('user-management');
    		    fetchUsers();
    		} else {
    			LxNotificationService.info("User @" + $scope.userDialog.nick + " has not been found on Twitter.");
    		}
    	});
    };

   $scope.deleteUser = function()
    {
        LxNotificationService.confirm('Delete user', 'Are you sure you want to delete this user?', { cancel:'Cancel', ok:'Delete' }, function(answer) {
        	var url = (tweet_fetcher_api + 'delete/user?action=delete&id=' + parseInt($scope.selectedUser.id));
            $http.get(url).success(function(data) {
        		if(data.ret == "OK") {
        			LxNotificationService.info("User @" + $scope.selectedUser.nick + " has been succesfully deleted.");
        		    fetchUsers();
        		} else {
        			LxNotificationService.info("User @" + $scope.selectedUser.nick + " cannot be deleted.");
        		}
        		
        	});
        });
    };

    $scope.userManagement = function() {
        LxDialogService.open('user-management');
    }

    fetchUsers();
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