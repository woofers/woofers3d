package com.jaxson.lib.gdx.graphics.g2d;

import java.util.Comparator;

public class DepthComparator implements Comparator<GdxSprite>
{
    @Override
    public int compare(GdxSprite a, GdxSprite b)
    {
    	float aDepth = a.getDepth();
    	float bDepth = b.getDepth();
    	if (aDepth > bDepth)
    	{
    		return -1;
    	}
    	else if (aDepth < bDepth)
    	{
    		return 1;
    	}
    	return 0;
    }
}
