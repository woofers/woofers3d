package com.jaxson.lib.box2d.bodies.types;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.Inputs;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.util.Unwrapable;
import com.badlogic.gdx.graphics.Texture;

public class SpriteBody extends SpriteActor
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

	public SpriteBody(Unwrapable<Texture> texture, BodyType type, float density)
	{
		this(texture.unwrap(), type, density);
	}

	public SpriteBody(Texture texture, BodyType type, float density)
	{
		super(texture);

		this.bodyDef = new BodyDef();
		this.bodyDef.type = type;

		this.fixtureDef = new FixtureDef();
		this.fixtureDef.density = density;
	}

	public void createBodies(World world)
	{
		this.bodyDef.linearVelocity.set(50, 0);
		this.bodyDef.position.set(x(), y());

		this.shape = new PolygonShape();
		this.shape.setAsBox(
				width() / 2, height() / 2,
				new Vector2(originalWidth() / 2, originalHeight() / 2),
				0);

		this.fixtureDef.shape = shape;

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
	public void update(float dt)
	{
		super.update(dt);
		super.setLocation(body().getPosition().x, body.getPosition().y);
	}

	@Override
	public void setLocation(float x, float y)
	{
		super.setLocation(x, y);
		if (hasBody()) body().setTransform(x, y, 0);
	}

	@Override
	public void setScale(float scaleX, float scaleY)
	{
		super.setScale(scaleX, scaleY);
	}
}
