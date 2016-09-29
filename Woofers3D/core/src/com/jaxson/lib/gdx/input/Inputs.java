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
			this.compass = new Compass(input());
			this.touchScreen = new TouchScreen(input());
			this.keyboard = new Keyboard(input());
			this.display = game.display();
			this.mouse = new Mouse(game, touchScreen);
			this.vibrator = new Vibrator(input());
			this.accelerometer = new Accelerometer(game);
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button)
		{
			return true;
		}

		public Accelerometer accelerometer()
		{
			return accelerometer;
		}

		public Compass compass()
		{
			return compass;
		}

		public Display display()
		{
			return display;
		}

		public InputMultiplexer inputProcessor()
		{
			return inputMultiplexer;
		}

		public Keyboard keyboard()
		{
			return keyboard;
		}

		public Mouse mouse()
		{
			return mouse;
		}

		public TouchScreen touchScreen()
		{
			return touchScreen;
		}

		public Vibrator vibrator()
		{
			return vibrator;
		}

		@Override
		public boolean keyDown(int keycode)
		{
			keyboard.key(keycode).setDown(true);
			keyboard.key(Keys.ANY_KEY).setDown(true);
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
			keyboard.key(keycode).setDown(false);
			keyboard.key(Keys.ANY_KEY).setDown(false);
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
			touchScreen.touch(pointer).setTouched(true);
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
			touchScreen.touch(pointer).setTouched(false);
			return true;
		}

		@Override
		public boolean zoom(float initialDistance, float distance)
		{
			return false;
		}

		void reset()
		{
			keyboard.reset();
		}

		void update(float dt)
		{
			keyboard.transfer();
			touchScreen.transfer();
		}

		private Input input()
		{
			return game.input();
		}
	}

	private static InputMultiplexer inputMultiplexer;
	private static InputListener inputListener;

	public Inputs(Game game)
	{
		inputListener = new InputListener(game);
		inputMultiplexer = inputListener.toInputMultiplexer();
		game.setInputProcessor(inputProcessor());
	}

	public static Accelerometer accelerometer()
	{
		return inputListener.accelerometer();
	}

	public static Compass compass()
	{
		return inputListener.compass();
	}

	public static Display display()
	{
		return inputListener.display();
	}

	public static InputMultiplexer inputProcessor()
	{
		return inputMultiplexer;
	}

	public static Keyboard keyboard()
	{
		return inputListener.keyboard();
	}

	public static Mouse mouse()
	{
		return inputListener.mouse();
	}

	public static TouchScreen touchScreen()
	{
		return inputListener.touchScreen();
	}

	public static Vibrator vibrator()
	{
		return inputListener.vibrator();
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
