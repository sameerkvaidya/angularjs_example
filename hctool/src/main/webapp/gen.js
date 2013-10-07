/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var gen = angular.module('gen', ["ngResource"]).
        config(function($routeProvider){
            $routeProvider.
                    when('/', {controller: ListCntrl, templateUrl: 'list.html'}).
                    when('/new', {controller: SaveCntrl, templateUrl: 'addnew.html'}).
                    when('/modify/:id', {controller: EditCntrl, templateUrl: 'modify.html'}).
                    otherwise({redirectTo: '/'});
        });
        
        
gen.factory('Env', function($resource){
   return $resource('form/:id',{id: '@id'}, { update: { method: 'PUT' }} ); 
});
            
    
var ListCntrl = function($scope, $location, Env){
    $scope.items = Env.query();
    
    
    $scope.delete = function(){
        var id = this.item.id;
        console.log("Deleting item :"+id);
        Env.delete({id: id}, function(){
            $scope.items = Env.query();
        });
        
    
    };
    
    $scope.mySplit = function(string, nb) {
        $scope.array = string.split('*');
        return $scope.result = $scope.array[nb];
    };
};   

var EditCntrl = function ($scope, $routeParams, $location, Env){
    $scope.item = Env.get({id: $routeParams.id});
    
    $scope.save = function(){
        Env.update({id: $scope.item.id}, $scope.item, function(){
            $location.path('/');
        });
    };
};


var SaveCntrl = function($scope, $location, Env){
    $scope.save = function(){
        Env.save($scope.item, function(){
           $location.path('/');
        });
    };
};