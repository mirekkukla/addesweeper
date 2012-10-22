package minesweeper;
import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Minesweeper {
	  public static void main(String[] args) {
	    Grid mineGrid = new Grid(8,8,10);
	    Grid clickedGrid = new Grid(8,8,10);
	    mineGrid.resetMineGrid();
	    mineGrid.resetMinesOnGrid();
	    clickedGrid.resetClickedGrid();
	    
		  MainFrame frame = new MainFrame(mineGrid, clickedGrid);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("Minesweeper");
		  frame.pack();
		  frame.setVisible(true);
		  }
}
