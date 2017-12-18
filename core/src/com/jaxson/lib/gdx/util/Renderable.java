package com.jaxson.lib.gdx.util;

import com.jaxson.lib.gdx.graphics.views.View;

/**
 * Interface for renderable {@link Object}s.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public interface Renderable extends Updateable
{
	/**
	 * Called when the {@link Object} should render itself.
	 * @param view View
	 */
	public void render(View view);
}
