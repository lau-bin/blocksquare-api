package com.freelance.lautarocutri.application;


import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixOperator {
 // Custom operator that increments adjacent elements by a given value
 public static void applyOperator(RealMatrix matrix, int row, int col) {
  int numRows = matrix.getRowDimension();
  int numCols = matrix.getColumnDimension();

  // Apply increment to the specified element
  matrix.addToEntry(row, col, 1);

  // Define adjacent positions (up, down, left, right)
  int[][] adjacentPositions = {
    {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}
  };

  // Apply increment to adjacent elements if within bounds
  for (int[] pos : adjacentPositions) {
   int r = pos[0];
   int c = pos[1];
   if (r >= 0 && r < numRows && c >= 0 && c < numCols) {
    matrix.addToEntry(r, c, 1);
   }
  }
 }
}
