package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Inputs extends Input.Keys implements InputProcessor, GestureListener
{
	public static final int ANY_KEY = -1;

	private static Accelerometer accelerometer;
	private static Compass compass;
	private static Keyboard keyboard;
	private static Mouse mouse;
	private static Screen screen;
	private static TouchScreen touchScreen;
	private static Vibrator vibrator;

	private static InputMultiplexer inputProcessor;
	private static GestureDetector gestureDetector;

	public Inputs()
	{
		compass = new Compass(getInput());
		touchScreen = new TouchScreen(getInput());
		keyboard = new Keyboard(getInput());
		mouse = new Mouse(getInput(), touchScreen);
		screen = new Screen(getInput());
		vibrator = new Vibrator(getInput());
		accelerometer = new Accelerometer(getInput(), screen);

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
		keyboard.getKey(keycode).setDown(true);
		keyboard.getKey(ANY_KEY).setDown(true);
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
		keyboard.getKey(keycode).setDown(false);
		keyboard.getKey(ANY_KEY).setDown(false);
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
		mouse.addScrollWheel(amount);
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
		touchScreen.getTouch(pointer).setTouched(true);
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
		touchScreen.getTouch(pointer).setTouched(false);
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance)
	{
		return false;
	}

	public static Accelerometer getAccelerometer()
	{
		return accelerometer;
	}

	public static Compass getCompass()
	{
		return compass;
	}

	public static InputMultiplexer getInputProcessor()
	{
		return inputProcessor;
	}

	public static Keyboard getKeyboard()
	{
		return keyboard;
	}

	public static Mouse getMouse()
	{
		return mouse;
	}

	public static Screen getScreen()
	{
		return screen;
	}

	public static TouchScreen getTouchScreen()
	{
		return touchScreen;
	}

	public static Vibrator getVibrator()
	{
		return vibrator;
	}

	public static void reset()
	{
		for (KeyboardKey key: keyboard.getKeys())
		{
			key.setDown(false);
			key.setWasDown(false);
		}
	}

	public static void update(float dt)
	{
		for (KeyboardKey key: keyboard.getKeys())
		{
			key.transfer();
		}
		for (Touch touch: touchScreen.getTouchs())
		{
			touch.transfer();
		}
	}

	private static Input getInput()
	{
		return Gdx.input;
	}
}
