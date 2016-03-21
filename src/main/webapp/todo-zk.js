/**
 * $scope - Current scope associated with the element
 * $element - Current element
 * Ref: injectable objects - https://docs.angularjs.org/api/ng/service/$compile
 */
angular.module('todoApp', [])
.controller('TodoListController', function($scope, $element) {
	$scope.todoList.todos = []; //data model

	//communicate with ZK VM
	var binder = zkbind.$($element); //the binder is used to invoke a command, register a command callback
	//register command "doUpdate" callback
  	binder.after('updateTodo', function (updatedTodoList) {
  		$scope.$apply(function() {
  			$scope.todoList.todos = updatedTodoList;
  		});
  	});
	
  	/**
  	 * Add one todo item.
  	 * the controller's method.
  	 */
  	$scope.todoList.addTodo = function() {
		var newTodo = {text:$scope.todoList.todoText, done:false};
		$scope.todoList.todos.push(newTodo);
		$scope.todoList.todoText = '';
		//send to ZK VM
		/*
		 * Angular adds $$hashKey in a newTodo to keep track of its changes, so it knows when it needs to update the DOM.
		 * We can remove that hash key with angular.toJson(newTodo) before passing to the server-side.
		 * Because $$hashKey will prevent ZK from converting newTodo (JSON) into its Java object (Todo) correctly.  
		 * http://stackoverflow.com/questions/18826320/what-is-the-hashkey-added-to-my-json-stringify-result
		 */
		binder.command('addTodo', {todo:angular.toJson(newTodo)});
	};

	/**
	 * count undone todo items
	 */
	$scope.todoList.remaining = function() {
		var count = 0;
		angular.forEach($scope.todoList.todos, function(todo) {
			count += todo.done ? 0 : 1;
		});
		return count;
	};

	$scope.todoList.updateStatus = function(todo) {
		//send to ZK VM
		binder.command('updateStatus', {index:$scope.todoList.todos.indexOf(todo), done:todo.done});
	};
	/**
	 * archive (drop) those done todo items
	 */
	$scope.todoList.archive = function() {
		var oldTodos = $scope.todoList.todos;
		$scope.todoList.todos = [];
		angular.forEach(oldTodos, function(todo) {
			if (!todo.done){
				$scope.todoList.todos.push(todo);
			}
		});
		binder.command('archive');
	};
	
//  	binder.after('archive', function (updatedTodoList) {
//  		$scope.$apply(function() {
//  			$scope.todoList.todos = updatedTodoList;
//  		});
//  	});	
});