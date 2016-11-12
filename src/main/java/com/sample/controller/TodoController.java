package com.sample.controller;

import com.sample.entity.TodoEntity;
import com.sample.model.Todo;
import com.sample.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vshan on 11/11/16.
 */
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAllTodos() {
        List<TodoEntity> todoEntities = todoRepository.findAll();
        List<Todo> todos = new ArrayList<>(todoEntities.size());
        for (TodoEntity todoEntity : todoEntities) {
            Todo tempTodo = convert(todoEntity);
            todos.add(tempTodo);
        }
        return todos;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo getTodoById(@PathVariable Long id) {
        return convert(todoRepository.findOne(id));
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody Todo todo) {
        TodoEntity todoEntity = convertToEntity(todo);
        TodoEntity entity = todoRepository.save(todoEntity);
        return convert(entity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodo(@RequestBody Todo todo) {
        TodoEntity todoEntity = convertToEntity(todo);
        todoRepository.save(todoEntity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeTodo(@PathVariable Long id) {
        TodoEntity todoEntity = todoRepository.findOne(id);
        todoEntity.setCompleted(true);
        todoRepository.save(todoEntity);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.delete(id);
    }

    private Todo convert(TodoEntity todoEntity) {
        Todo tempTodo = new Todo();
        tempTodo.setId(todoEntity.getId());
        tempTodo.setUserId(todoEntity.getUserId());
        tempTodo.setTitle(todoEntity.getTitle());
        tempTodo.setCompleted(todoEntity.isCompleted());
        return tempTodo;
    }

    private TodoEntity convertToEntity(Todo todo) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(todo.getId());
        todoEntity.setUserId(todo.getUserId());
        todoEntity.setTitle(todo.getTitle());
        todoEntity.setCompleted(todo.isCompleted());
        return todoEntity;
    }

}