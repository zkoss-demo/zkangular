package org.zkoss.zkangular;

import java.util.ArrayList;
import java.util.Iterator;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;

@NotifyCommand(value="updateTodo", onChange="_vm_.todoList")
@ToClientCommand({"updateTodo"})
@ToServerCommand({"addTodo", "updateStatus","archive"})
public class TodoVM {

	private ArrayList<Todo> todoList = new ArrayList<Todo>();

	@Init
	public void init() {
		Todo todo = new Todo("learn ZK");
		todo.setDone(true);
		todoList.add(todo);
		todoList.add(new Todo("build a ZK application"));
	}
	
	/**
	 * ZK can automatically convert a JSON object into your domain object.
	 * @param todo
	 */
	@Command
	public void addTodo(@BindingParam("todo") Todo todo){
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

	/**
	 * Drop those "done" todo.
	 */
	@Command @NotifyChange("todoList")
	public void archive(){
		Iterator<Todo> iterator = todoList.iterator();
		while (iterator.hasNext()){
			Todo todo = iterator.next();
			if (todo.isDone()){
				iterator.remove();
			}
		}
	}	
	
	public ArrayList<Todo> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<Todo> todoList) {
		this.todoList = todoList;
	}
}
