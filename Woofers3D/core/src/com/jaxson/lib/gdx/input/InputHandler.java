package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.DisplayOrientation;
import com.jaxson.lib.math.MyMath;

public class InputHandler extends Keys implements InputProcessor, GestureListener
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
	private static final float TOUCH_MOUSE_SCALE = 1f / 3f;
	private static final float SENSITIVITY = 1.3f;

	private static final boolean INVERT_MOUSE_X = true;
	private static final boolean INVERT_MOUSE_Y = true;

	private static final float ACCELEROMETER_FORWARD_SCALE = 70f / 100f;
	private static final float ACCELEROMETER_BACK_SCALE = 20f / 100f;
	private static final float ACCELEROMETER_NULL_SCALE = 100f - ACCELEROMETER_FORWARD_SCALE - ACCELEROMETER_BACK_SCALE;
	private static final float ACCELEROMETER_MAX = 10f;
	private static final float ACCELEROMETER_MIN = -ACCELEROMETER_MAX;
	private static final float ACCELEROMETER_RANGE = ACCELEROMETER_MAX - ACCELEROMETER_MIN;

	private static final int KEY_MIN = 0;
	private static final int KEY_MAX = 256;
	private static final int MULTITOUCH_SUPPORT = 20;

	private static final int NATIVE_ROTATION_OFFSET = 90;

	private static boolean[] keys, prevKeys, touches, prevTouches;
	private static Vector2 mouse, deltaMouse, invertMouse, sensitivity;
	private static int scrollWheel;
	private static DisplayOrientation orientation, prevOrientation;
	private static InputMultiplexer inputProcessor;
	private static GestureDetector gestureDetector;

	public static boolean anyKey()
	{
		if (isButtonPressed(ANY_MOUSE)) return true;
		if (isDown(ANY_KEY)) return true;
		return false;
	}

	public static boolean areTouched(int amount)
	{
		int touched = 0;
		for (int i = 0; i < MULTITOUCH_SUPPORT; i ++)
		{
			if (isTouched(i)) touched ++;
			if (touched >= amount) return true;
		}
		return false;
	}

	public static boolean areTouchedPressed(int amount)
	{
		int touched = 0;
		boolean wasTouched = false;
		for (int i = 0; i < MULTITOUCH_SUPPORT; i ++)
		{
			if (isTouched(i)) touched ++;
			if (isTouchPressed(i)) wasTouched = true;
			if (touched >= amount && wasTouched) return true;
		}
		return false;
	}

	public static void flipInvertMouse(boolean flipX, boolean flipY)
	{
		if (flipX) getInvertMouse().scl(-1f, 1f);
		if (flipY) getInvertMouse().scl(1f, -1f);
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

	public static Vector3 getAccelerometer()
	{
		return new Vector3(getAccelerometerX(), getAccelerometerY(), getAccelerometerZ());
	}

	public static float getAccelerometerBack()
	{
		float value = 0;
		float y = getAccelerometerY();
		if (y > getAccelerometerNullMaxY()) value = MyMath.abs((y - getAccelerometerNullMaxY()) / getAccelerometerMinRangeY());
		return MyMath.min(value, 1f);
	}

	public static float getAccelerometerBackRangeY()
	{
		return ACCELEROMETER_MAX - getAccelerometerNullMaxY();
	}

	public static float getAccelerometerForward()
	{
		float value = 0;
		float y = getAccelerometerY();
		if (y < getAccelerometerNullMinY()) value = MyMath.abs((y - getAccelerometerNullMinY()) / getAccelerometerMinRangeY());
		return MyMath.min(value, 1f);
	}

	public static float getAccelerometerForwardRangeY()
	{
		return getAccelerometerNullMinY() - ACCELEROMETER_MIN;
	}

	public static float getAccelerometerMinRangeY()
	{
		float back = getAccelerometerBackRangeY();
		float forward = getAccelerometerForwardRangeY();
		if (back > forward) return forward;
		return back;
	}

	public static float getAccelerometerNullMaxY()
	{
		return ACCELEROMETER_MAX - getScaledAccelerometerRange(ACCELEROMETER_BACK_SCALE);
	}

	public static float getAccelerometerNullMinY()
	{
		return ACCELEROMETER_MIN + getScaledAccelerometerRange(ACCELEROMETER_FORWARD_SCALE);
	}

	public static float getAccelerometerX()
	{
		float x;
		if (isLandscape())
		{
			x = getAbsouluteAccelerometerY();
			if (isReverseLandscape()) x *= -1f;

		}
		else
		{
			x = getAbsouluteAccelerometerX();
			if (isReversePortrait()) x *= -1f;
		}
		return x;
	}

	public static float getAccelerometerY()
	{
		float y;
		if (isLandscape())
		{
			y = getAbsouluteAccelerometerX();
			if (isReverseLandscape()) y *= -1f;

		}
		else
		{
			y = getAbsouluteAccelerometerY();
			if (isReversePortrait()) y *= -1f;
		}
		return y;
	}

	public static float getAccelerometerZ()
	{
		return getAbsouluteAccelerometerZ();
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

	public static InputMultiplexer getInputProcessor()
	{
		return inputProcessor;
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

	public static int getNativeRotation()
	{
		return getInput().getRotation();
	}

	public static DisplayOrientation getOrientation()
	{
		return DisplayOrientation.getOrientation(getRotation());
	}

	public static int getRotation()
	{
		int rotation = getNativeRotation();
		if (getNativeOrientation() == Orientation.Portrait) return rotation;
		return rotation + NATIVE_ROTATION_OFFSET;
	}

	private static float getScaledAccelerometerRange(float scale)
	{
		return scale * ACCELEROMETER_RANGE;
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

	public static int getScrollWheel()
	{
		return scrollWheel;
	}

	public static Vector2 getSensitivity()
	{
		return sensitivity;
	}

	public static int getTouchAmmount()
	{
		return getTouchAmmount(MULTITOUCH_SUPPORT);
	}

	public static int getTouchAmmount(int max)
	{
		return getTouchAmmount(0, max);
	}

	public static int getTouchAmmount(int min, int max)
	{
		if (min < 0 || MULTITOUCH_SUPPORT < max) throw new IllegalArgumentException("Pointers out of range");
		int touches = 0;
		for (int pointer = 0; pointer < max; pointer ++)
		{
			if (isTouched(pointer)) touches ++;
		}
		return touches;
	}

	public static boolean hasAccelerometer()
	{
		return isPeripheralAvailable(Peripheral.Accelerometer);
	}

	public static boolean hasCompass()
	{
		return isPeripheralAvailable(Peripheral.Compass);
	}

	public static boolean hasHardwareKeyboard()
	{
		return isPeripheralAvailable(Peripheral.HardwareKeyboard);
	}

	public static boolean hasKeyboard()
	{
		return hasHardwareKeyboard() || hasTouchKeyboard();
	}

	public static boolean hasTouchKeyboard()
	{
		return isPeripheralAvailable(Peripheral.OnscreenKeyboard);
	}

	public static boolean hasTouchScreen()
	{
		return isPeripheralAvailable(Peripheral.MultitouchScreen);
	}

	public static boolean hasVibrator()
	{
		return isPeripheralAvailable(Peripheral.Vibrator);
	}

	public static boolean isAccelerometerBack()
	{
		return getAccelerometerBack() != 0;
	}

	public static boolean isAccelerometerForward()
	{
		return getAccelerometerForward() != 0;
	}

	public static boolean isButtonPressed(int button)
	{
		return getInput().isButtonPressed(button);
	}

	public static boolean isButtonPressed(int... buttons)
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
		if (!isKeyInRange(keycode)) throw new KeyOutOfRangeException();
		return keys[keycode];
	}

	public static boolean isDown(int... keycodes)
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

	public static boolean isDown(KeyCombination... keyCombinations)
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

	public static boolean isKeyInRange(int keycode)
	{
		return KEY_MIN <= keycode && keycode < KEY_MAX;
	}

	public static boolean isLandscape()
	{
		return getOrientation().isLandscape();
	}

	public static boolean isPeripheralAvailable(Peripheral peripheral)
	{
		return getInput().isPeripheralAvailable(peripheral);
	}

	public static boolean isPortrait()
	{
		return getOrientation().isPortrait();
	}

	public static boolean isPressed(int keycode)
	{
		if (!isKeyInRange(keycode)) throw new KeyOutOfRangeException();
		return keys[keycode] && !prevKeys[keycode];
	}

	public static boolean isPressed(int... keycodes)
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

	public static boolean isPressed(KeyCombination... keyCombinations)
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
		if (!isKeyInRange(keycode)) throw new KeyOutOfRangeException();
		return !keys[keycode] && prevKeys[keycode];
	}

	public static boolean isReleased(int... keycodes)
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

	public static boolean isReleased(KeyCombination... keyCombinations)
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

	public static boolean isReverseLandscape()
	{
		return getOrientation() == DisplayOrientation.ReverseLandscape;
	}

	public static boolean isReversePortrait()
	{
		return getOrientation() == DisplayOrientation.ReversePortrait;
	}

	public static boolean isStandardLandscape()
	{
		return getOrientation() == DisplayOrientation.Landscape;
	}

	public static boolean isStandardPortrait()
	{
		return getOrientation() == DisplayOrientation.Portrait;
	}

	public static boolean isTouchDown(int pointer)
	{
		return touches[pointer];
	}

	public static boolean isTouched()
	{
		return getInput().isTouched();
	}

	public static boolean isTouched(int pointer)
	{
		return getInput().isTouched(pointer);
	}

	public static boolean isTouchPressed(int pointer)
	{
		return touches[pointer] && !prevTouches[pointer];
	}

	public static boolean isTouchReleased(int pointer)
	{
		return !touches[pointer] && prevTouches[pointer];
	}

	public static boolean justTouched()
	{
		return isTouchPressed(0);
	}

	public static boolean oneFingerTouching()
	{
		return getTouchAmmount() >= 1;
	}

	public static boolean orientationHasChanged()
	{
		return orientation != prevOrientation;
	}

	public static void reset()
	{
		for (int i = 0; i < KEY_MAX; i ++)
		{
			keys[i] = false;
			prevKeys[i] = false;
		}
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
		InputHandler.invertMouse = invertMouse;
	}

	public static void setSensitivity(float sensitivity)
	{
		getSensitivity().set(sensitivity, sensitivity);
	}

	public static void setSensitivity(Vector2 sensitivity)
	{
		InputHandler.sensitivity = sensitivity;
	}

	public static boolean threeFingerTouched()
	{
		return areTouchedPressed(3);
	}

	public static boolean threeFingerTouching()
	{
		return getTouchAmmount() >= 3;
	}

	public static boolean twoFingerTouched()
	{
		return areTouchedPressed(2);
	}

	public static boolean twoFingerTouching()
	{
		return getTouchAmmount() >= 2;
	}

	public static void update(float dt)
	{
		for (int i = 0; i < KEY_MAX; i ++)
		{
			prevKeys[i] = keys[i];
		}
		for (int i = 0; i < MULTITOUCH_SUPPORT; i ++)
		{
			prevTouches[i] = touches[i];
		}
		prevOrientation = orientation;
		orientation = getOrientation();
	}

	public InputHandler()
	{
		InputHandler.keys = new boolean[KEY_MAX];
		InputHandler.prevKeys = new boolean[KEY_MAX];
		InputHandler.touches = new boolean[MULTITOUCH_SUPPORT];
		InputHandler.prevTouches = new boolean[MULTITOUCH_SUPPORT];
		InputHandler.mouse = new Vector2();
		InputHandler.deltaMouse = new Vector2();
		InputHandler.invertMouse = new Vector2(1f, 1f);
		InputHandler.sensitivity = new Vector2(1f, 1f);
		InputHandler.orientation = getOrientation();
		InputHandler.prevOrientation = orientation;
		setInvertMouse(INVERT_MOUSE_X, INVERT_MOUSE_Y);
		setSensitivity(SENSITIVITY);
		inputProcessor = new InputMultiplexer();
		gestureDetector = new GestureDetector(this);
		inputProcessor.addProcessor(this);
		inputProcessor.addProcessor(gestureDetector);
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button)
	{
		return true;
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
	public boolean longPress(float x, float y)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY)
	{
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button)
	{
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
	{
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		scrollWheel += amount;
		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button)
	{
		return true;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button)
	{
		touches[pointer] = true;
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return touchDown((float) x, (float) y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		touches[pointer] = false;
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance)
	{
		return false;
	}
}
