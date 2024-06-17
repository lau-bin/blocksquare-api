package com.freelance.lautarocutri.application;

import com.freelance.lautarocutri.entity.Problem;
import com.freelance.lautarocutri.entity.Solution;
import com.freelance.lautarocutri.entity.SolutionStep;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProblemCreator {

 @Inject SolutionProvider solutionProvider;

 @Transactional
 public Problem createProblem(Problem problem, boolean fullScan){
  // validation
  if (problem.getBlocks().length % problem.getWidth() != 0){
   throw new WebApplicationException("Invalid problem size", 422); // 422 HTTP STATUS UNPROCESSABLE ENTITY
  }
  if (problem.getBlocks().length > 255){
   throw new WebApplicationException("Problem size too large, max 255", 422); // 422 HTTP STATUS UNPROCESSABLE ENTITY
  }
  // solve problem
  var step = solutionProvider.findSolutionStep(problem, fullScan);

  if (step == null){
   return null; // unsolvable problem
  }
  problem.persist();
  step.forEach(s->s.persist());
  var sol = new Solution(problem,  step.toArray(SolutionStep[]::new));
  sol.persistAndFlush();

  return problem;
 }
}
