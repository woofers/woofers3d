package com.jaxson;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class AboutDialog extends JDialog implements ActionListener {
  public AboutDialog(JFrame parent, String title, String message) {
    super(parent, title, true);
    //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //pack();
    setVisible(true);
  }
  public void actionPerformed(ActionEvent e) {
    setVisible(false);
    dispose();
  }
}