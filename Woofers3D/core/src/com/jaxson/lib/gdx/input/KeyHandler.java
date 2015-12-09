package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyHandler implements InputProcessor
{
	public static final int UP      = Keys.W;
	public static final int FORWARD = Keys.W;
	public static final int DOWN    = Keys.S;
	public static final int BACK    = Keys.S;
	public static final int LEFT    = Keys.A;
	public static final int RIGHT   = Keys.D;
	public static final int SPACE   = Keys.SPACE;
	public static final int PAUSE   = Keys.ESCAPE;

	private static final float MOUSE_SCALE      = 1f / 10f;
	private static final float SENSITIVITY      = 1.05f;
	private static final boolean INVERT_MOUSE   = true;

	private static final int KEY_SIZE  = 256;
	private static boolean[] keys, prevKeys;
	private static Vector2 mouse, prevMouse;

	public KeyHandler()
	{
		keys = new boolean[KEY_SIZE];
		prevKeys = new boolean[KEY_SIZE];
		mouse = new Vector2();
		prevMouse = new Vector2();
	}

	public static Vector2 getDeltaMouse()
	{
		return getMouse().sub(prevMouse);
	}

	public static Vector2 getMouse()
	{
		return mouse.cpy();
	}

	public static Vector2 getScaledMouse()
	{
		if (!Gdx.input.isCursorCatched()) return Vector2.Zero;
		final float scale = MOUSE_SCALE * SENSITIVITY;
		Vector2 mouse = getDeltaMouse();
		mouse.scl(scale, -scale);
		if (INVERT_MOUSE) mouse.scl(-1f, -1f);
		return mouse;
	}

	public static Vector2 getPrevMouse()
	{
		return prevMouse.cpy();
	}

	public static boolean isDown(int keycode)
	{
		return keys[keycode];
	}

	public static boolean isPressed(int keycode)
	{
		return keys[keycode] && !prevKeys[keycode];
	}

	public static boolean isReleased(int keycode)
	{
		return !keys[keycode] && prevKeys[keycode];
	}

	@Override
	public boolean keyDown(int keycode)
	{
		keys[keycode] = true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		keys[keycode] = false;
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{
		mouse.x = x;
		mouse.y = y;
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		mouse.x = x;
		mouse.y = y;
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return true;
	}

	public static void update(float dt)
	{
		for (int i = 0; i < KEY_SIZE; i ++)
		{
			prevKeys[i] = keys[i];
		}
		prevMouse = mouse.cpy();
	}
}
