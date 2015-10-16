package com.jaxson.ui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;

public class Dialog<T extends Window> extends JDialog
{
	public Dialog(int width, int height, T window)
	{
		super(window);
		setDialogSize(width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setLayout(new BorderLayout());
		center();
		draw();
	}

	public void center()
	{
		setLocationRelativeTo(null);
	}

	public void draw()
	{
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public void setDialogSize(int width, int height)
	{
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(getScreenSize());
	}

	private Dimension getScreenSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
