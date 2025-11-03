package org.workflowCore.task.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.workflowCore.task.dto.TaskCreateDTO;
import org.workflowCore.task.dto.TaskDTO;
import org.workflowCore.task.dto.TaskUpdateDTO;
import org.workflowCore.task.entity.Task;
import org.workflowCore.task.entity.TaskPriority;
import org.workflowCore.task.entity.TaskStatus;
import org.workflowCore.task.service.TaskService;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Path("/api/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    @Inject
    TaskService taskService;

    @POST
    public Response createTask(@Valid TaskCreateDTO createDTO){
        TaskDTO created = taskService.createTask(createDTO);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") Long id){
        TaskDTO taskDTO = taskService.getTaskById(id);
        return Response.ok(taskDTO).build();
    }

    @GET
    public Response getAllTasks(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size){
        List<TaskDTO> tasks = taskService.getAllTasks(page,size);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/status/{status}")
    public Response getTasksByStatus(
            @PathParam("status") TaskStatus status,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size){
        List<TaskDTO> tasks = taskService.getTasksByStatus(status, page, size);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/priority/{priority}")
    public Response getTasksByPriority(
            @PathParam("priority") TaskPriority priority,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size){
        List<TaskDTO> tasks = taskService.getTasksByPriority(priority, page, size);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/search")
    public Response searchTasksByKeyword(
            @QueryParam("keyword") String keyword,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        List<TaskDTO> tasks = taskService.searchTasks(keyword, page, size);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/statistics")
    public Response getTaskStatistics() {
        Map<TaskStatus, Long> stats = taskService.getTaskStatistics();
        return Response.ok(stats).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(
            @PathParam("id") Long id,
            @Valid TaskUpdateDTO updateDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, updateDTO);
        return Response.ok(updatedTask).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        taskService.deleteTask(id);
        return Response.noContent().build();
    }
}



