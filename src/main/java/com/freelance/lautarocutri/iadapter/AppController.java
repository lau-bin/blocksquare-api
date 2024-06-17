package com.freelance.lautarocutri.iadapter;

import com.freelance.lautarocutri.AppConfig;
import com.freelance.lautarocutri.application.ProblemCreator;
import com.freelance.lautarocutri.application.SolutionProvider;
import com.freelance.lautarocutri.entity.Problem;
import com.freelance.lautarocutri.entity.Solution;
import com.freelance.lautarocutri.iadapter.dto.CreateProblemRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/")
public class AppController {

    @Inject
    AppConfig appConfig;

    @Inject
    ProblemCreator problemCreator;

    @Inject
    SolutionProvider solutionProvider;

    private final Vertx vertx;

    public AppController(Vertx vertx) {
        this.vertx = vertx;
    }

    @GET
    @Path("/problems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblems() {
        PanacheQuery<Solution> query = Solution.findAll();
        var solutions = query.stream().map(s->s.getProblem()).collect(Collectors.toList());
        return Response.ok(solutions).build();
    }

    @GET
    @Path("/problems/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblem(
      @PathParam("id") String id
    ){
        Optional<Solution> solution = Solution.findByIdOptional(id);

        if (solution.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(solution.get().getProblem()).build();
    }

    @POST
    @Path("/problems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProblem(
      CreateProblemRequest body
    ){
        if (body.problem().getBlocks().length > appConfig.problemMaxLen()){
            return Response.status(
              422,
              String.format("Problem size exceeds max size %s", appConfig.problemMaxLen())
            ).build(); // HTTP STATUS UNPROCESSABLE ENTITY
        }
        return Response.ok(problemCreator.createProblem(body.problem(), body.fullScan() == null ? false : body.fullScan())).build();
    }

    @GET
    @Path("/solutions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSolutions(){
        var solutions = Solution.findAll();
        return Response.ok(solutions.stream().collect(Collectors.toList())).build();
    }

    @GET
    @Path("/solutions/problem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblemSolutions(
      @PathParam("id") String id
    ){
        Optional<Solution> solution = Solution.findByIdOptional(id);

        if (solution.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(solution.get().getSolutionStep()).build();
    }
}
