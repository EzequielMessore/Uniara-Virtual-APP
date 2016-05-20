package br.com.uniaravirtual.model.service;


import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.entity.Student;
import br.com.uniaravirtual.model.entity.StudentFiles;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UniaraClient {

    @GET("/student")
    void getStudent(Callback<Student> callback);

    @POST("/login")
    Response postLogin(@Body() Student student);

    @GET("/grades")
    List<Grades> getGrades();

    @GET("/files")
    List<StudentFiles> getFiles();

    @GET("/files/{id}")
    Response download(@Path("id") String id);

    @GET("/absences")
    List<Absence> getAbsences();
}
