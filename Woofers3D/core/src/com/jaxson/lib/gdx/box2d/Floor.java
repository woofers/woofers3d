package com.jaxson.lib.gdx.box2d;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.Inputs;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;

public class Floor extends SpriteActor
{
	private static final float METERS_TO_PIXELS = 0.11f;

	private static final String PATH = "icon.png";
	private static final float SCALE = 0.6f;
	private static final int SPEED = 2;

	private Body body;
	private Fixture fixture;

	private	PolygonShape shape;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;

	private Keyboard keyboard;
	private KeyboardKey forwardKey;
	private KeyboardKey backwardKey;
	private KeyboardKey leftKey;
	private KeyboardKey rightKey;
	private KeyboardKey jumpKey;

	public Floor(float x, float y)
	{
		super(getTex());
		setLocation(x, y);

		this.bodyDef = new BodyDef();
		this.bodyDef.type = BodyDef.BodyType.StaticBody;
		this.bodyDef.position.set(x() + width() / 2, y() + height() / 2);

		this.shape = new PolygonShape();
		this.shape.setAsBox(width() / 2, height() / 2);

		this.fixtureDef = new FixtureDef();
		this.fixtureDef.shape = shape;
		this.fixtureDef.density = 1f;
	}

	public void createBodies(World world)
	{
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		shape.dispose();
		shape = null;
	}

	public Body body()
	{
		return body;
	}

	public boolean hasFixture()
	{
		return fixture != null;
	}

	public boolean hasBody()
	{
		return body != null;
	}

	public boolean hasPhysics()
	{
		return hasFixture() && hasBody();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		if (shape != null) shape.dispose();
	}

	@Override
	protected void input(float dt)
	{
		super.input(dt);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		input(dt);
		//setBodyLocation(body().getPosition());
	}

	@Override
	public void setLocation(float x, float y)
	{
		super.setLocation(x, y);
		setBodyLocation(x, y);
	}

	protected void setBodyLocation(Vector3 location)
	{
		setBodyLocation(location.x, location.y);
	}

	protected void setBodyLocation(Vector2 location)
	{
		setBodyLocation(location.x, location.y);
	}

	protected void setBodyLocation(float x, float y)
	{
		if (hasBody()) body().setTransform(x, y, 0);
	}

	public static Texture getTex()
	{
		Pixmap pixmap = new Pixmap(512, 64, Pixmap.Format.RGBA8888);
		pixmap.setColor(1f, 1f, 1f, 1f);
		pixmap.fillRectangle(0, 0, 512, 64);
		return new Texture(pixmap);
	}
}
