package com.jaxson.lib.ui;

import java.awt.LayoutManager;
import java.awt.BorderLayout;

public interface AdvancedWindow
{
	public static final double MIN_SIZE       = 0.4;
	public static final LayoutManager LAYOUT = new BorderLayout();

	public void center();

	public void draw();

	public int getWindowHeight();

	public int getWindowWidth();

	public void setScreenRatio(double scale);

	public void setWindowHeight(int height);

	public void setWindowWidth(int width);

	public void setWindowSize(int width, int height);

	public void showWindow();
}
