package com.sample.controller;

import com.sample.entity.TodoEntity;
import com.sample.model.Todo;
import com.sample.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vshan on 11/11/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {
    public static final long ID = 1L;
    public static final long USER_ID = 2L;
    public static final String TEST_TITLE = "Test title";
    public static final boolean COMPLETED = false;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoController todoController;

    @Test
    public void getAllTodos() throws Exception {
        List<TodoEntity> todoEntityList = new ArrayList();
        TodoEntity todoEntity1 = getTodoEntity(COMPLETED);
        TodoEntity todoEntity2 = getTodoEntity(COMPLETED);
        todoEntityList.addAll(Arrays.asList(todoEntity1, todoEntity2));

        when(todoRepository.findAll()).thenReturn(todoEntityList);

        List<Todo> todos = todoController.getAllTodos();

        assertThat(todos.size(), is(2));
        assertTodo(todos.get(0));
        assertTodo(todos.get(1));
    }

    @Test
    public void getTodoById() throws Exception {
        TodoEntity todoEntity1 = getTodoEntity(COMPLETED);
        when(todoRepository.findOne(ID)).thenReturn(todoEntity1);

        Todo todo = todoController.getTodoById(ID);

        assertTodo(todo);
    }

    @Test
    public void addTodo() throws Exception {
        Todo todoInput = getTodo();
        todoInput.setId(null);

        when(todoRepository.save(any(TodoEntity.class))).thenReturn(getTodoEntity(COMPLETED));

        Todo todo = todoController.addTodo(todoInput);
        ArgumentCaptor<TodoEntity> argumentCaptor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getId(), nullValue());
        assertTodo(todo);
    }

    @Test
    public void updateTodo() throws Exception {
        Todo todoInput = getTodo();

        todoController.updateTodo(todoInput);
        ArgumentCaptor<TodoEntity> argumentCaptor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getId(), is(todoInput.getId()));
        assertThat(argumentCaptor.getValue().getTitle(), is(todoInput.getTitle()));
        assertThat(argumentCaptor.getValue().getUserId(), is(todoInput.getUserId()));
        assertThat(argumentCaptor.getValue().isCompleted(), is(todoInput.isCompleted()));

    }

    @Test
    public void completeTodo() throws Exception {
        TodoEntity todoEntity = getTodoEntity(false);
        when(todoRepository.findOne(ID)).thenReturn(todoEntity);

        todoController.completeTodo(ID);
        ArgumentCaptor<TodoEntity> argumentCaptor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(argumentCaptor.capture());

        assertTrue(argumentCaptor.getValue().isCompleted());

    }

    @Test
    public void deleteTodo() throws Exception {
        todoController.deleteTodo(ID);
        verify(todoRepository).delete(ID);
    }

    private void assertTodo(Todo todo) {
        assertThat(todo.getId(), is(ID));
        assertThat(todo.getTitle(), is(TEST_TITLE));
        assertThat(todo.isCompleted(), is(COMPLETED));
        assertThat(todo.getUserId(), is(USER_ID));
    }

    private TodoEntity getTodoEntity(boolean completed) {
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setCompleted(completed);
        todoEntity.setTitle(TEST_TITLE);
        todoEntity.setUserId(USER_ID);
        todoEntity.setId(ID);
        return todoEntity;
    }

    private Todo getTodo() {
        Todo todo = new Todo();

        todo.setCompleted(COMPLETED);
        todo.setTitle(TEST_TITLE);
        todo.setUserId(USER_ID);
        todo.setId(ID);
        return todo;
    }

}