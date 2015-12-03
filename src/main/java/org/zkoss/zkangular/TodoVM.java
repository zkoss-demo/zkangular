package org.zkoss.zkangular;

import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;

@NotifyCommand(value="doUpdate", onChange="_vm_.todoList")
@ToClientCommand({"doUpdate"})
@ToServerCommand({"addTodo"})
public class TodoVM {

	private ArrayList<Todo> todoList = new ArrayList<Todo>();

	@Init
	public void init() {
		todoList.add(new Todo("testing todo"));
	}
	
	@Command
	@NotifyChange("todoList")
	public void addTodo(@BindingParam("text") String text){
		Todo to = new Todo(text);
		todoList.add(to);
	}
	
	public ArrayList<Todo> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<Todo> todoList) {
		this.todoList = todoList;
	}
}
