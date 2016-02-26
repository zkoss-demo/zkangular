angular.module('todoApp', [])
.controller('TodoListController', function($scope, $element) {
	var todoList = this;
	todoList.todos = [{text:'learn angular', done:true},
	                  {text:'build an angular app', done:false}];

	//communicate with ZK VM
	var binder = zkbind.$($element);
  	binder.after('doUpdate', function (updatedTodoList) {
  		$scope.$apply(function() {
  			$scope.todoList.todos = updatedTodoList;
  		});
  	});
	
	todoList.addTodo = function() {
		var newTodo = {text:todoList.todoText, done:false};
		todoList.todos.push(newTodo);
		todoList.todoText = '';
		//send to ZK VM
		binder.command('addTodo', newTodo);
	};

	todoList.remaining = function() {
		var count = 0;
		angular.forEach(todoList.todos, function(todo) {
			count += todo.done ? 0 : 1;
		});
		return count;
	};

	todoList.archive = function() {
		var oldTodos = todoList.todos;
		todoList.todos = [];
		angular.forEach(oldTodos, function(todo) {
			if (!todo.done) todoList.todos.push(todo);
		});
	};
});