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
@ToServerCommand({"addTodo", "updateStatus"})
public class TodoVM {

	private ArrayList<Todo> todoList = new ArrayList<Todo>();

	@Init
	public void init() {
		todoList.add(new Todo("testing todo"));
	}
	
	@Command
	public void addTodo(@BindingParam("text") String text){
		//TODO pass a Todo object
		Todo todo = new Todo(text);
		todoList.add(todo);
	}
	
	/**
	 * update a todo's done status.
	 * @param todo
	 */
	@Command
	public void updateStatus(@BindingParam("index") int index, @BindingParam("done") boolean done){
		todoList.get(index).setDone(done);
	}
	
	//TODO archive todo
	
	public ArrayList<Todo> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<Todo> todoList) {
		this.todoList = todoList;
	}
}
