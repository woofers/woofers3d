package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;

public class Inputs
{
	private static class InputListener
			implements InputProcessor, GestureListener
	{
		private Game game;
		private Accelerometer accelerometer;
		private Compass compass;
		private Keyboard keyboard;
		private Mouse mouse;
		private Display display;
		private TouchScreen touchScreen;
		private Vibrator vibrator;

		private InputListener(Game game)
		{
			this.game = game;
			this.compass = new Compass(getInput());
			this.touchScreen = new TouchScreen(getInput());
			this.keyboard = new Keyboard(getInput());
			this.display = game.getDisplay();
			this.mouse = new Mouse(game, touchScreen);
			this.vibrator = new Vibrator(getInput());
			this.accelerometer = new Accelerometer(game);
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button)
		{
			return true;
		}

		public Accelerometer getAccelerometer()
		{
			return accelerometer;
		}

		public Compass getCompass()
		{
			return compass;
		}

		public Display getDisplay()
		{
			return display;
		}

		public InputMultiplexer getInputProcessor()
		{
			return inputMultiplexer;
		}

		public Keyboard getKeyboard()
		{
			return keyboard;
		}

		public Mouse getMouse()
		{
			return mouse;
		}

		public TouchScreen getTouchScreen()
		{
			return touchScreen;
		}

		public Vibrator getVibrator()
		{
			return vibrator;
		}

		@Override
		public boolean keyDown(int keycode)
		{
			keyboard.getKey(keycode).setDown(true);
			keyboard.getKey(Keys.ANY_KEY).setDown(true);
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
			keyboard.getKey(Keys.ANY_KEY).setDown(false);
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
		public boolean pinch(Vector2 initialPointer1,
				Vector2 initialPointer2,
				Vector2 pointer1,
				Vector2 pointer2)
		{
			return true;
		}

		public void reset()
		{
			for (KeyboardKey key: keyboard.getKeys())
			{
				key.setDown(false);
				key.setWasDown(false);
			}
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

		public GestureDetector toGestureDetector()
		{
			return new GestureDetector(inputListener);
		}

		public InputMultiplexer toInputMultiplexer()
		{
			InputMultiplexer inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(toInputProcessor());
			inputMultiplexer.addProcessor(toGestureDetector());
			return inputMultiplexer;
		}

		public InputProcessor toInputProcessor()
		{
			return this;
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

		public void update(float dt)
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

		@Override
		public boolean zoom(float initialDistance, float distance)
		{
			return false;
		}

		private Input getInput()
		{
			return game.getInput();
		}
	}

	private static InputMultiplexer inputMultiplexer;
	private static InputListener inputListener;

	public Inputs(Game game)
	{
		inputListener = new InputListener(game);
		inputMultiplexer = inputListener.toInputMultiplexer();
		game.setInputProcessor(getInputProcessor());
	}

	public static Accelerometer getAccelerometer()
	{
		return inputListener.getAccelerometer();
	}

	public static Compass getCompass()
	{
		return inputListener.getCompass();
	}

	public static Display getDisplay()
	{
		return inputListener.getDisplay();
	}

	public static InputMultiplexer getInputProcessor()
	{
		return inputMultiplexer;
	}

	public static Keyboard getKeyboard()
	{
		return inputListener.getKeyboard();
	}

	public static Mouse getMouse()
	{
		return inputListener.getMouse();
	}

	public static TouchScreen getTouchScreen()
	{
		return inputListener.getTouchScreen();
	}

	public static Vibrator getVibrator()
	{
		return inputListener.getVibrator();
	}

	public static void reset()
	{
		inputListener.reset();
	}

	public static void update(float dt)
	{
		inputListener.update(dt);
	}
}
