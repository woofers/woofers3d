package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import java.util.HashMap;

public class Mouse
{
	public static final int MOUSE_LEFT = Buttons.LEFT;
	public static final int MOUSE_RIGHT = Buttons.RIGHT;
	public static final int MOUSE_MIDDLE = Buttons.MIDDLE;
	public static final int MOUSE_BACK = Buttons.BACK;
	public static final int MOUSE_FORWARD = Buttons.FORWARD;
	public static final int[] MOUSE_BUTTONS = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE, MOUSE_BACK, MOUSE_FORWARD };
	public static final int[] PRIMARY_MOUSE = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE };

	private static final float MOUSE_SCALE = 1f / 5f;
	private static final float TOUCH_MOUSE_SCALE = 1f / 3f;

	private static final boolean INVERT_MOUSE_X = true;
	private static final boolean INVERT_MOUSE_Y = true;

	private Vector2 location;
	private Vector2 delta;
	private Vector2 sensitivity;
	private int scrollWheel;
	private HashMap<Integer, MouseButton> buttons;
	private Game game;
	private TouchScreen touchScreen;

	Mouse(Game game, TouchScreen touchScreen)
	{
		this.game = game;
		this.touchScreen = touchScreen;
		this.location = new Vector2();
		this.delta = new Vector2();
		this.sensitivity = new Vector2(1f, -1f);
		this.buttons = new HashMap<>();
		setInvertX(INVERT_MOUSE_X);
		setInvertY(INVERT_MOUSE_X);
		for (int button: MOUSE_BUTTONS)
		{
			addButton(button);
		}
	}

	public int getDeltaX()
	{
		return getInput().getDeltaX();
	}

	public int getDeltaY()
	{
		return getInput().getDeltaY();
	}

	public Vector2 getLocation()
	{
		return location.set(getX(), getY());
	}

	public Vector2 getLocationDelta()
	{
		return delta.set(getDeltaX(), getDeltaY());
	}

	public Vector2 getScaledLocation()
	{
		if (!isCatched() && !touchScreen.exists()) return Vector2.Zero;
		Vector2 mouse = getLocationDelta();
		mouse.scl(MOUSE_SCALE);
		mouse.scl(sensitivity);
		if (touchScreen.exists())
		{
			mouse.scl(1f, -1f);
			mouse.scl(TOUCH_MOUSE_SCALE);
		}
		return mouse;
	}

	public int getScrollWheel()
	{
		return scrollWheel;
	}

	public Vector2 getSensitivity()
	{
		return sensitivity;
	}

	public float getSensitivityX()
	{
		return getSensitivity().x;
	}

	public float getSensitivityY()
	{
		return getSensitivity().y;
	}

	public int getX()
	{
		return getInput().getX();
	}

	public int getY()
	{
		return getInput().getY();
	}

	public boolean isCatched()
	{
		return getInput().isCursorCatched();
	}

	public boolean isClicked()
	{
		return isPressed(getButton(MOUSE_LEFT));
	}

	public boolean isInvertX()
	{
		return getSensitivityX() < 0f;
	}

	public boolean isInvertY()
	{
		return getSensitivityY() > 0f;
	}

	public boolean isPressed(MouseButton button)
	{
		return button.isPressed();
	}

	/**
	 * Sets whether the {@link Mouse} is catched.
	 * @param catched Whether the {@link Mouse} should be catched
	 */
	public void setCatched(boolean catched)
	{
		if (catched == isCatched()) return;
		getInput().setCursorCatched(catched);
		if (!catched) setPosition(getDisplay().getCenter());
	}

	public void setInvert(boolean invert)
	{
		setInvertX(invert);
		setInvertY(invert);
	}

	public void setInvertX(boolean invertX)
	{
		if (invertX != isInvertX()) getSensitivity().scl(-1f, 1f);
	}

	public void setInvertY(boolean invertY)
	{
		if (invertY != isInvertY()) getSensitivity().scl(1f, -1f);
	}

	/**
	 * Sets the location of the {@link Mouse}.
	 * @param x The x location of the {@link Mouse}
	 * @param y The y location of the {@link Mouse}
	 */
	public void setPosition(int x, int y)
	{
		getInput().setCursorPosition(x, y);
	}

	/**
	 * Sets the location of the {@link Mouse}.
	 * @param location The location of the {@link Mouse}
	 */
	public void setPosition(Vector2 location)
	{
		setPosition((int) location.x, (int) location.y);
	}

	public void setSensitivity(float sensitivity)
	{
		getSensitivity().set(sensitivity, sensitivity);
	}

	public void setSensitivity(Vector2 sensitivity)
	{
		getSensitivity().scl(sensitivity);
	}

	public void setSensitivityX(float x)
	{
		getSensitivity().set(x, getSensitivityY());
	}

	public void setSensitivityY(float y)
	{
		getSensitivity().set(getSensitivityX(), y);
	}

	protected MouseButton addButton(int buttonCode)
	{
		MouseButton button = new MouseButton(buttonCode, getInput());
		buttons.put(buttonCode, button);
		return button;
	}

	protected MouseButton getButton(int button)
	{
		return buttons.get(button);
	}

	void addScrollWheel(int scrollWheel)
	{
		this.scrollWheel += scrollWheel;
	}

	private Display getDisplay()
	{
		return game.getDisplay();
	}

	private Input getInput()
	{
		return game.getInput();
	}
}
