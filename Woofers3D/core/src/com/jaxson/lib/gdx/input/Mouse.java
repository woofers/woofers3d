package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Mouse implements Iterable<MouseButton>
{
	public static final int MOUSE_LEFT = Buttons.LEFT;
	public static final int MOUSE_RIGHT = Buttons.RIGHT;
	public static final int MOUSE_MIDDLE = Buttons.MIDDLE;
	public static final int MOUSE_BACK = Buttons.BACK;
	public static final int MOUSE_FORWARD = Buttons.FORWARD;

	public static final int[] MOUSE_BUTTONS = { MOUSE_LEFT,
												MOUSE_RIGHT,
												MOUSE_MIDDLE,
												MOUSE_BACK,
												MOUSE_FORWARD };

	public static final int[] PRIMARY_MOUSE = { MOUSE_LEFT,
												MOUSE_RIGHT,
												MOUSE_MIDDLE };

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
		setSensitivity(game.config().getSensitivity());
		setInvertX(INVERT_MOUSE_X);
		setInvertY(INVERT_MOUSE_X);
		for (int button: MOUSE_BUTTONS)
		{
			buttons.put(button, new MouseButton(button, input()));
		}
	}

	public MouseButton button(int button)
	{
		return buttons.get(button);
	}

	public Collection<MouseButton> buttons()
	{
		return buttons.values();
	}

	public int deltaX()
	{
		return input().getDeltaX();
	}

	public int deltaY()
	{
		return input().getDeltaY();
	}

	public Vector2 location()
	{
		return location.set(x(), y());
	}

	public Vector2 locationDelta()
	{
		return delta.set(deltaX(), deltaY());
	}

	public Vector2 scaledLocation()
	{
		if (!isCatched() && !touchScreen.exists()) return Vector2.Zero;
		Vector2 mouse = locationDelta();
		mouse.scl(MOUSE_SCALE);
		mouse.scl(sensitivity);
		if (touchScreen.exists())
		{
			mouse.scl(1f, -1f);
			mouse.scl(TOUCH_MOUSE_SCALE);
		}
		return mouse;
	}

	public int scrollWheel()
	{
		return scrollWheel;
	}

	public Vector2 sensitivity()
	{
		return sensitivity;
	}

	public float sensitivityX()
	{
		return sensitivity().x;
	}

	public float sensitivityY()
	{
		return sensitivity().y;
	}

	public int x()
	{
		return input().getX();
	}

	public int y()
	{
		return input().getY();
	}

	public boolean isCatched()
	{
		return input().isCursorCatched() || isMobile();
	}

	public boolean isClicked()
	{
		return isLeftClicked() || isRightClicked();
	}

	public boolean isInvertX()
	{
		return sensitivityX() < 0f;
	}

	public boolean isInvertY()
	{
		return sensitivityY() > 0f;
	}

	public boolean isLeftClicked()
	{
		return button(MOUSE_LEFT).isPressed();
	}

	public boolean isRightClicked()
	{
		return button(MOUSE_RIGHT).isPressed();
	}

	@Override
	public Iterator<MouseButton> iterator()
	{
		return buttons().iterator();
	}

	/**
	 * Sets whether the {@link Mouse} is catched.
	 * @param catched Whether the {@link Mouse} should be catched
	 */
	public void setCatched(boolean catched)
	{
		if (catched == isCatched()) return;
		input().setCursorCatched(catched);
		if (!catched) setLocation(display().center());
	}

	public void setInvert(boolean invert)
	{
		setInvertX(invert);
		setInvertY(invert);
	}

	public void setInvertX(boolean invertX)
	{
		if (invertX != isInvertX()) sensitivity().scl(-1f, 1f);
	}

	public void setInvertY(boolean invertY)
	{
		if (invertY != isInvertY()) sensitivity().scl(1f, -1f);
	}

	/**
	 * Sets the location of the {@link Mouse}.
	 * @param x The x location of the {@link Mouse}
	 * @param y The y location of the {@link Mouse}
	 */
	public void setLocation(int x, int y)
	{
		input().setCursorPosition(x, y);
	}

	/**
	 * Sets the location of the {@link Mouse}.
	 * @param location The location of the {@link Mouse}
	 */
	public void setLocation(Vector2 location)
	{
		setLocation((int) location.x, (int) location.y);
	}

	public void setSensitivity(float sensitivity)
	{
		sensitivity().set(sensitivity, sensitivity);
	}

	public void setSensitivity(Vector2 sensitivity)
	{
		sensitivity().scl(sensitivity);
	}

	public void setSensitivityX(float x)
	{
		sensitivity().set(x, sensitivityY());
	}

	public void setSensitivityY(float y)
	{
		sensitivity().set(sensitivityX(), y);
	}

	void addScrollWheel(int scrollWheel)
	{
		this.scrollWheel += scrollWheel;
	}

	private Display display()
	{
		return game.display();
	}

	private Input input()
	{
		return game.input();
	}

	private boolean isMobile()
	{
		return game.isMobile();
	}
}
