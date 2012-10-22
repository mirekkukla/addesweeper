package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
  private static final int WIN = 1;
  private static final int LOSE = 0;
  private int totalNonMines;
  private int numClicked = 0;
  private Grid mineGrid;
  private Grid clickedGrid;
  private JPanel gridPanel;
  
  public MainFrame(Grid mineGrid, Grid clickedGrid) {
    this.mineGrid = mineGrid;
    this.clickedGrid = clickedGrid;
    initUI(mineGrid.xDim, mineGrid.yDim);
    totalNonMines = mineGrid.xDim*mineGrid.yDim - mineGrid.totalMines;
  }

  private final void initUI(int xDim, int yDim) {
    setupGridPanel(xDim, yDim);
    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);

    // setup reset button
    ImageIcon AMP = new ImageIcon("src/addepar_logo_vertical.gif");
    JButton resetButton = new JButton(AMP);
    resetButton.setPreferredSize(new Dimension(200, 50));
    resetButton.setBorder(new EmptyBorder(0,0,0,0)); //hides the shit around the button
    toolbar.add(new JLabel("                   "));
    toolbar.add(resetButton);
    add(toolbar, BorderLayout.NORTH);
    ResetButtonListener listener = new ResetButtonListener();
    resetButton.addActionListener(listener);
  }
  
  private void setupGridPanel(int xDim, int yDim) {
    gridPanel = new JPanel();
    gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    gridPanel.setLayout(new GridLayout(xDim, yDim, 1, 1));
    
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++) {
        mineCell cell = new mineCell(i,j);
        cell.setPreferredSize(new Dimension(42, 42));
        cell.addActionListener(new MineCellListener());
        cell.addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON3) {
              mineCell o = (mineCell) e.getSource();
              if (o.getIcon() != null) {
                o.setIcon(null);
              } else if (o.getIcon() == null && !clickedGrid.haveClicked(o.xCoord,o.yCoord)) {
                ImageIcon flag = new ImageIcon("src/flag2.png");
                o.setIcon(flag);
              }
            }
          }
        });
        gridPanel.add(cell);
      }
    }
    add(gridPanel);
  }
  
  class MineCellListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        mineCell o = (mineCell) e.getSource();
        if(o.getIcon() != null) {
          //do nothing
        } else if(mineGrid.isMine(o.xCoord,o.yCoord)) {
          endGame(LOSE);
        } else {
          o.setText(Integer.toString(mineGrid.numMinesTouching(o.xCoord,o.yCoord)));
          clickedGrid.setNumberOfNearbyMines(o.xCoord, o.yCoord);
          numClicked++;
        }
        if(numClicked == totalNonMines) {
          endGame(WIN);
        }
    }
}
  
  class ResetButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      gridPanel.removeAll();
      gridPanel.revalidate();
      //paint(); // why does this mess sutff up?
      setupGridPanel(mineGrid.xDim, mineGrid.yDim);
      mineGrid.resetMineGrid();
      mineGrid.resetMinesOnGrid();
      clickedGrid.resetClickedGrid();
    }
  }
  
  private void endGame(int outcome) {
    gridPanel.removeAll();
    gridPanel.revalidate();
    for (int i = 0; i < this.mineGrid.xDim; i++) {
      for (int j = 0; j < this.mineGrid.yDim; j++) {
        mineCell cell = new mineCell(i,j);
        cell.setPreferredSize(new Dimension(22, 22));
        if (this.mineGrid.isMine(i, j)) {
          ImageIcon bombIcon;
          if (outcome == WIN) {
            bombIcon = new ImageIcon("src/dollars2.gif");
          } else {
            bombIcon = new ImageIcon("src/addepar_icon_blue.gif");
          }
          cell.setIcon(bombIcon);
        } else if(this.clickedGrid.haveClicked(i, j)){
          cell.setText(Integer.toString(this.mineGrid.numMinesTouching(i, j)));
        }
        gridPanel.add(cell);
      }
    }
    numClicked = 0;
  } 
}