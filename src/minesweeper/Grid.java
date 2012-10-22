package minesweeper;

import java.util.Random;
import java.lang.Math;

public class Grid {
  private static final int NOMINE = 0;
  private static final int MINE = 1;
  private static final int UNCLICKED = -1;
  
  public final int xDim;
  public final int yDim;
  private final int[][] array;
  public int totalMines;
  
  Grid(int xDim, int yDim) {
    this.xDim = xDim;
    this.yDim = yDim;
    this.array = new int[xDim][yDim];
  }
  
  Grid(int xDim, int yDim, int totalMines) {
    this.xDim = xDim;
    this.yDim = yDim;
    this.totalMines = totalMines;
    this.array = new int[xDim][yDim];
  }
  
  public void resetMineGrid() {
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++){
        this.array[i][j] = NOMINE;
      }
    }
  }
  
  public void resetClickedGrid() {
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++){
        this.array[i][j] = UNCLICKED;
      }
    }
  }
  
  public void resetMinesOnGrid() {
    Random randomGenerator = new Random();
    int minesLaid = 0;
    while (minesLaid < totalMines) {
      int randomX = randomGenerator.nextInt(this.xDim);
      int randomY = randomGenerator.nextInt(this.yDim);
      if (this.array[randomX][randomY] == NOMINE) {
        this.array[randomX][randomY] = MINE;
        minesLaid++;
      }
    }
  }
  
  public void setMine(int x, int y) {
    assert (x >= 0 && x < this.xDim);
    assert (y >= 0 && y < this.yDim);
    this.array[x][y] = MINE; 
  }

  public boolean isMine(int x, int y) {
    assert (x >= 0 && x < this.xDim);
    assert (y >= 0 && y < this.yDim);
    if(this.array[x][y] == MINE) return true;
    else return false;
  }
  
  public void setNumberOfNearbyMines(int x, int y) {
    assert (x >= 0 && x < this.xDim);
    assert (y >= 0 && y < this.yDim);
    this.array[x][y] = numMinesTouching(xDim, yDim);
  }
  
  public boolean haveClicked(int x, int y) {
    if (this.array[x][y] == UNCLICKED) return false;
    else return true;
  }
  
  
  public int numMinesTouching(int x, int y) {
    int count = 0;
    for (int i = Math.max(0,x-1); i <= Math.min(x+1,7); i++) {
      for (int j = Math.max(0,y-1); j <= Math.min(y+1,7); j++) {
        if (this.isMine(i, j)) {
          count++; //ok to count the cell we clicked - if it was a bomb, it already exploded
        }
      }
    }
    return count;
  }
  
}
