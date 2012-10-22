package minesweeper;

import javax.swing.JButton;

public class mineCell extends JButton{
  public final int xCoord;
  public final int yCoord;
  
  mineCell(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }
}
