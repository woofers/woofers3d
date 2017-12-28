package com.jaxson.lib.gdx.util;

/**
 * Interface for updating {@link Object}s.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
@FunctionalInterface
public interface Updateable
{
    /**
     * Called when the {@link Object} should update its logic.
     * @param dt The delta time
     */
    public void update(float dt);
}
