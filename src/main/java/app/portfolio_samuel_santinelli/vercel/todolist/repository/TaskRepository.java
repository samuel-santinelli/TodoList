package app.portfolio_samuel_santinelli.vercel.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.portfolio_samuel_santinelli.vercel.todolist.model.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID>{
    
}
