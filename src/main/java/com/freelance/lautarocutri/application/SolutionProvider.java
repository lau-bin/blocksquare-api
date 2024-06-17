package com.freelance.lautarocutri.application;

import com.freelance.lautarocutri.AppConfig;
import com.freelance.lautarocutri.entity.Problem;
import com.freelance.lautarocutri.entity.SolutionStep;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixPreservingVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SolutionProvider {

 @Inject
 AppConfig appConfig;
 private final RealMatrixPreservingVisitor sumOperator = new RealMatrixPreservingVisitor() {
  private double sum = 0;
  @Override
  public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
   sum = 0;
  }
  @Override
  public void visit(int row, int column, double value) {
   sum += value % 2 == 0 ? 0 : 1;
  }
  @Override
  public double end() {
   return sum;
  }
 };
 public List<SolutionStep> findSolutionStep(Problem problem, boolean fullScan){
  List<List<Integer>> solutions;
  if (fullScan){
   solutions = solveProblemFullScan(problem);
  }
  else{
   solutions = List.of(solveProblem(problem));
  }

  if (solutions.isEmpty()){
   return null;
  }
  return solutions.stream().map(el->new SolutionStep(el.stream()
    .mapToInt(Integer::intValue)
    .toArray())).collect(Collectors.toList());
 }

 private List<List<Integer>> solveProblemFullScan(Problem problem){
  RealMatrix matrix = createMatrixFromProblemData(problem.getBlocks(), problem.getWidth());
  var paths = new ArrayList<List<Integer>>();
  solveProblemFullScan(matrix.copy(), paths, List.of());
  return paths;
 }
 private void solveProblemFullScan(RealMatrix matrix, List<List<Integer>> solutions, List<Integer> previousPath){
  if (previousPath.size() / 2 > appConfig.solutionFullMaxDepth()){
   return;
  }
  for (int row = 0; row < matrix.getColumnDimension(); row++)
   for (int col = 0; col < matrix.getRowDimension(); col++){
    var currentPath = new ArrayList<>(previousPath);
    currentPath.add(row);
    currentPath.add(col);
    MatrixOperator.applyOperator(matrix, row, col);
    double val = matrix.walkInOptimizedOrder(sumOperator);

    if (val == 0){
     var sol = new ArrayList<>(currentPath);
     sol.add(col);
     sol.add(row);
     solutions.add(sol);
    }
    solveProblemFullScan(matrix, solutions, currentPath);
   }
 }

 private List<Integer> solveProblem(Problem problem){
  // Greedy Best-First (actually best only) Search
  RealMatrix matrix = createMatrixFromProblemData(problem.getBlocks(), problem.getWidth());
  var path = new ArrayList<Integer>();
  while (path.size() < appConfig.solutionGreedyMaxDepth()){
   int bestRow = 0;
   int bestCol = 0;
   double minVal = Integer.MAX_VALUE;
   for (int row = 0; row < problem.getWidth(); row++)
    for (int col = 0; col < problem.getWidth(); col++){
     var copy = matrix.copy();
     MatrixOperator.applyOperator(copy, row, col);
     double val = copy.walkInOptimizedOrder(sumOperator);

     if (val < minVal){
      minVal = val;
      bestRow = row;
      bestCol = col;
     }
     if (val == 0){
      path.add(bestRow);
      path.add(bestCol);
      return path;
     }
    }
   path.add(bestCol);
   path.add(bestRow);
   MatrixOperator.applyOperator(matrix, bestRow, bestCol);
  }
   return List.of();
 }

 private RealMatrix createMatrixFromProblemData(Boolean[] data, int width){
  int height = data.length/width;
  double[][] m = new double[height][width];
  for (int row = 0; row < height; row++)
   for (int col = 0; col < width; col++){
    m[row][col] = data[row * col + col] ? 1 : 0;
   }
  return MatrixUtils.createRealMatrix(m);
 }

 private double simulate(RealMatrix matrix, int row, int col){
  MatrixOperator.applyOperator(matrix, row, col);
  return matrix.walkInOptimizedOrder(sumOperator);
 }

 public static void printMatrix(RealMatrix matrix) {
  for (int i = 0; i < matrix.getRowDimension(); i++) {
   for (int j = 0; j < matrix.getColumnDimension(); j++) {
    System.out.print(matrix.getEntry(i, j) + "\t");
   }
   System.out.println();
  }
 }
}
