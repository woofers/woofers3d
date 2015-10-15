package com.jaxson;

import javax.swing.*;

public class Test
{
	public Test()
	{


		JFrame frame = new JFrame("Test");
		frame.setSize(300, 300);
		frame.setVisible(true);

		JDialog dialog = new JDialog(frame, true);
		dialog.setSize(100, 100);
		dialog.setVisible(true);


	    //AboutDialog dlg = new AboutDialog(frame, "title", "message");



	}
}
