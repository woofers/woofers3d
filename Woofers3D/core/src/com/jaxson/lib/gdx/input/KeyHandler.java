package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Input.Orientation;
import com.jaxson.lib.gdx.util.GdxMath;

public class KeyHandler extends Keys implements InputProcessor
{
	public static final int MOVE_UP = W;
	public static final int MOVE_FORWARD = W;
	public static final int MOVE_DOWN = S;
	public static final int MOVE_BACK = S;
	public static final int MOVE_BACKWARD = S;
	public static final int MOVE_LEFT = A;
	public static final int MOVE_RIGHT = D;
	public static final int UP_ARROW = UP;
	public static final int DOWN_ARROW = DOWN;
	public static final int LEFT_ARROW = LEFT;
	public static final int RIGHT_ARROW = RIGHT;
	public static final int PAUSE = ESCAPE;

	public static final int MOUSE_LEFT = Buttons.LEFT;
	public static final int MOUSE_RIGHT = Buttons.RIGHT;
	public static final int MOUSE_MIDDLE = Buttons.MIDDLE;
	public static final int MOUSE_BACK = Buttons.BACK;
	public static final int MOUSE_FORWARD = Buttons.FORWARD;

	public static final int[] ANY_RIGHT = { MOVE_RIGHT, RIGHT_ARROW };
	public static final int[] ANY_LEFT = { MOVE_LEFT, LEFT_ARROW };
	public static final int[] ANY_UP = { MOVE_UP, UP_ARROW };
	public static final int[] ANY_DOWN = { MOVE_DOWN, DOWN_ARROW };
	public static final int[] ANY_MOUSE = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE, MOUSE_BACK, MOUSE_FORWARD };
	public static final int[] PRIMARY_MOUSE = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE };
	public static final int[] ALT = { ALT_LEFT, ALT_RIGHT };
	public static final KeyList FULLSCREEN = new KeyList(F11, BACKSLASH, new KeyCombination(ALT_LEFT, ENTER));

	private static final float MOUSE_SCALE = 1f / 5f;
	private static final float TOUCH_MOUSE_SCALE = MOUSE_SCALE;
	private static final float SENSITIVITY = 1.3f;
	private static final boolean INVERT_MOUSE_X = true;
	private static final boolean INVERT_MOUSE_Y = true;
	private static final float ACCELEROMETER_FORWARD_SCALE = 70f / 100f;
	private static final float ACCELEROMETER_BACK_SCALE = 20f / 100f;
	private static final float ACCELEROMETER_NULL_SCALE = 100f - ACCELEROMETER_FORWARD_SCALE - ACCELEROMETER_BACK_SCALE;
	private static final float ACCELEROMETER_MAX = 10f;
	private static final float ACCELEROMETER_MIN = -ACCELEROMETER_MAX;
	private static final float ACCELEROMETER_RANGE = ACCELEROMETER_MAX - ACCELEROMETER_MIN;
	private static final int KEY_SIZE = 256;
	private static boolean[] keys, prevKeys;
	private static Vector2 mouse, deltaMouse, invertMouse, sensitivity;
	private static float scrollWheel;

	public KeyHandler()
	{
		KeyHandler.keys = new boolean[KEY_SIZE];
		KeyHandler.prevKeys = new boolean[KEY_SIZE];
		KeyHandler.mouse = new Vector2();
		KeyHandler.deltaMouse = new Vector2();
		KeyHandler.invertMouse = new Vector2(1f, 1f);
		KeyHandler.sensitivity = new Vector2(1f, 1f);
		setInvertMouse(INVERT_MOUSE_X, INVERT_MOUSE_Y);
		setSensitivity(SENSITIVITY);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		keys[keycode] = true;
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		keys[keycode] = false;
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		scrollWheel = amount;
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
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return true;
	}

	public static boolean anyKey()
	{
		if (isButtonPressed(ANY_MOUSE)) return true;
		if (isDown(ANY_KEY)) return true;
		return false;
	}

	public static boolean isAccelerometerForward()
	{
		return getAccelerometerForward() != 0;
	}

	public static boolean isAccelerometerBack()
	{
		return getAccelerometerBack() != 0;
	}

	public static float getAccelerometerMaxY()
	{
		return ACCELEROMETER_MAX - getAccelerometerNullMaxY();
	}

	public static float getAccelerometerMinY()
	{
		return getAccelerometerNullMinY() - ACCELEROMETER_MIN;
	}

	public static float getAccelerometerNullMaxY()
	{
		return ACCELEROMETER_MAX - getScaledAccelerometerRange(ACCELEROMETER_BACK_SCALE);
	}

	public static float getAccelerometerNullMinY()
	{
		return ACCELEROMETER_MIN + getScaledAccelerometerRange(ACCELEROMETER_FORWARD_SCALE);
	}

	public static float getAccelerometerBack()
	{
		// Y > 6
		// 6 to 10
		float y = getAccelerometerY();
		if (y > getAccelerometerNullMaxY()) return GdxMath.abs((y - getAccelerometerNullMaxY()) / getAccelerometerMaxY());
		return 0;
	}

	public static float getAccelerometerForward()
	{
		// Y < 4
		// 4 to -10
		float y = getAccelerometerY();
		if (y < getAccelerometerNullMinY()) return GdxMath.abs((y - getAccelerometerNullMinY()) / getAccelerometerMinY());
		return 0;
	}

	private static float getScaledAccelerometerRange(float scale)
	{
		return scale * ACCELEROMETER_RANGE;
	}

	public static Vector3 getAccelerometer()
	{
		return new Vector3(getAccelerometerX(), getAccelerometerY(), getAccelerometerZ());
	}

	public static float getAccelerometerX()
	{
		if (isLandscape()) return getAbsouluteAccelerometerY();
		return getAbsouluteAccelerometerX();
	}

	public static float getAccelerometerY()
	{
		if (isLandscape()) return getAbsouluteAccelerometerX();
		return getAbsouluteAccelerometerY();
	}

	public static float getAccelerometerZ()
	{
		return getAbsouluteAccelerometerZ();
	}

	private static Vector3 getAbsouluteAccelerometer()
	{
		return new Vector3(getAbsouluteAccelerometerX(), getAbsouluteAccelerometerY(), getAbsouluteAccelerometerZ());
	}

	private static float getAbsouluteAccelerometerX()
	{
		return getInput().getAccelerometerX();
	}

	private static float getAbsouluteAccelerometerY()
	{
		return getInput().getAccelerometerY();
	}

	private static float getAbsouluteAccelerometerZ()
	{
		return getInput().getAccelerometerZ();
	}

	public static Vector2 getDeltaMouse()
	{
		return deltaMouse.set(getDeltaMouseX(), getDeltaMouseY());
	}

	public static int getDeltaMouseX()
	{
		return getInput().getDeltaX();
	}

	public static int getDeltaMouseY()
	{
		return getInput().getDeltaY();
	}

	private static Input getInput()
	{
		return Gdx.input;
	}

	public static Vector2 getInvertMouse()
	{
		return invertMouse;
	}

	public static Vector2 getMouse()
	{
		return mouse.set(getMouseX(), getMouseY());
	}

	public static int getMouseX()
	{
		return getInput().getX();
	}

	public static int getMouseY()
	{
		return getInput().getY();
	}

	public static Orientation getNativeOrientation()
	{
		return getInput().getNativeOrientation();
	}

	public static float getScrollWheel()
	{
		return scrollWheel;
	}

	public static boolean isPortrait()
	{
		return getOrientation() == Orientation.Portrait;
	}

	public static boolean isLandscape()
	{
		return getOrientation() == Orientation.Landscape;
	}

	public static Orientation getOrientation()
	{
		int rotation = getRotation();
		if (getNativeOrientation() == Orientation.Portrait)
		{
			if (rotation == 180 || rotation == 0) return Orientation.Portrait;
			return Orientation.Landscape;
		}
		if (rotation == 180 || rotation == 0) return Orientation.Landscape;
		return Orientation.Portrait;
	}

	public static int getRotation()
	{
		return getInput().getRotation();
	}

	public static Vector2 getScaledMouse()
	{
		if (!isCursorCatched() && !hasTouchScreen()) return Vector2.Zero;
		Vector2 mouse = getDeltaMouse();
		mouse.scl(MOUSE_SCALE);
		mouse.scl(sensitivity);
		mouse.scl(invertMouse);
		if (hasTouchScreen())
		{
			mouse.scl(1f, -1f);
			mouse.scl(TOUCH_MOUSE_SCALE);
		}
		return mouse;
	}

	public static boolean isButtonPressed(int button)
	{
		return getInput().isButtonPressed(button);
	}

	public static boolean isButtonPressed(int[] buttons)
	{
		for (int button: buttons)
		{
			if (isButtonPressed(button)) return true;
		}
		return false;
	}

	public static boolean isClicked()
	{
		return isButtonPressed(PRIMARY_MOUSE);
	}

	public static boolean isCursorCatched()
	{
		return getInput().isCursorCatched();
	}

	public static boolean isDown(int keycode)
	{
		if (keycode == ANY_KEY) return true;
		return keys[keycode];
	}

	public static boolean isDown(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isDown(keycode)) return true;
		}
		return false;
	}

	public static boolean isDown(KeyCombination keyCombination)
	{
		for (int keycode: keyCombination.getKeys())
		{
			if (!isDown(keycode)) return false;
		}
		return true;
	}

	public static boolean isDown(KeyCombination[] keyCombinations)
	{
		for (KeyCombination keyCombination: keyCombinations)
		{
			if (isDown(keyCombination)) return true;
		}
		return false;
	}

	public static boolean isDown(KeyList keyList)
	{
		if (isDown(keyList.getKeys())) return true;
		if (isDown(keyList.getKeyCombinations())) return true;
		return false;
	}

	public static boolean isPressed(int keycode)
	{
		return keys[keycode] && !prevKeys[keycode];
	}

	public static boolean isPressed(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isPressed(keycode)) return true;
		}
		return false;
	}

	public static boolean isPressed(KeyCombination keyCombination)
	{
		boolean wasPressed = false;
		for (int keycode: keyCombination.getKeys())
		{
			if (!isDown(keycode)) return false;
			if (isPressed(keycode)) wasPressed = true;
		}
		return wasPressed;
	}

	public static boolean isPressed(KeyCombination[] keyCombinations)
	{
		for (KeyCombination keyCombination: keyCombinations)
		{
			if (isPressed(keyCombination)) return true;
		}
		return false;
	}

	public static boolean isPressed(KeyList keyList)
	{
		if (isPressed(keyList.getKeys())) return true;
		if (isPressed(keyList.getKeyCombinations())) return true;
		return false;
	}

	public static boolean isReleased(int keycode)
	{
		return !keys[keycode] && prevKeys[keycode];
	}

	public static boolean isReleased(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isReleased(keycode)) return true;
		}
		return false;
	}

	public static boolean isReleased(KeyCombination keyCombination)
	{
		for (int keycode: keyCombination.getKeys())
		{
			if (!isReleased(keycode)) return false;
		}
		return true;
	}

	public static boolean isReleased(KeyCombination[] keyCombinations)
	{
		for (KeyCombination keyCombination: keyCombinations)
		{
			if (isReleased(keyCombination)) return true;
		}
		return false;
	}

	public static boolean isReleased(KeyList keyList)
	{
		if (isReleased(keyList.getKeys())) return true;
		if (isReleased(keyList.getKeyCombinations())) return true;
		return false;
	}

	public static boolean justTouched()
	{
		return getInput().justTouched();
	}


	public static void flipInvertMouse(boolean flipX, boolean flipY)
	{
		if (flipX) getInvertMouse().scl(-1f, 1f);
		if (flipY) getInvertMouse().scl(1f, -1f);
	}

	public static void setInvertMouse(boolean invertX, boolean invertY)
	{
		setInvertMouse(1, 1);
		flipInvertMouse(invertX, invertY);
	}

	public static void setInvertMouse(int x, int y)
	{
		getInvertMouse().set(x, y);
	}

	public static void setInvertMouse(Vector2 invertMouse)
	{
		KeyHandler.invertMouse = invertMouse;
	}

	public static Vector2 getSensitivity()
	{
		return sensitivity;
	}

	public static void setSensitivity(Vector2 sensitivity)
	{
		KeyHandler.sensitivity = sensitivity;
	}

	public static void setSensitivity(float sensitivity)
	{
		getSensitivity().set(sensitivity, sensitivity);
	}

	public static void reset()
	{
		for (int i = 0; i < KEY_SIZE; i++)
		{
			keys[i] = false;
			prevKeys[i] = false;
		}
	}

	public static void update(float dt)
	{
		for (int i = 0; i < KEY_SIZE; i++)
		{
			prevKeys[i] = keys[i];
		}
	}

	public static boolean isPeripheralAvailable(Peripheral peripheral)
	{
		return getInput().isPeripheralAvailable(peripheral);
	}

	public static boolean hasAccelerometer()
	{
		return isPeripheralAvailable(Peripheral.Accelerometer);
	}

	public static boolean hasTouchScreen()
	{
		return isPeripheralAvailable(Peripheral.MultitouchScreen);
	}

	public static boolean hasVibrator()
	{
		return isPeripheralAvailable(Peripheral.Vibrator);
	}

	public static boolean hasTouchKeyboard()
	{
		return isPeripheralAvailable(Peripheral.OnscreenKeyboard);
	}

	public static boolean hasHardwareKeyboard()
	{
		return isPeripheralAvailable(Peripheral.HardwareKeyboard);
	}

	public static boolean hasKeyboard()
	{
		return hasHardwareKeyboard() || hasTouchKeyboard();
	}

	public static boolean hasCompass()
	{
		return isPeripheralAvailable(Peripheral.Compass);
	}
}
