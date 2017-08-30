package com.jaxson.lib.gdx.box2d.bodies.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.util.Unwrapable;
import com.jaxson.lib.gdx.box2d.Box2DWorld;
import static com.jaxson.lib.gdx.box2d.Box2DWorld.*;


public class SpriteBody extends SpriteActor
{
	private static final String PATH = "icon.png";
	private static final float SCALE = 0.6f;
	private static final int SPEED = 2;

	private Body body;
	private Fixture fixture;

	private PolygonShape shape;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;

	public SpriteBody(Texture texture, BodyType type, float density)
	{
		super(texture);

		this.bodyDef = new BodyDef();
		this.bodyDef.type = type;

		this.fixtureDef = new FixtureDef();
		this.fixtureDef.density = density;
	}

	public SpriteBody(Unwrapable<Texture> texture, BodyType type, float density)
	{
		this(texture.unwrap(), type, density);
	}

	public Body body()
	{
		return body;
	}

	public void createBody(Box2DWorld world)
	{
		//this.bodyDef.linearVelocity.set(50, 0);
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

	@Override
	public void dispose()
	{
		super.dispose();
		if (shape != null) shape.dispose();
	}

	public boolean hasBody()
	{
		return body != null;
	}

	public boolean hasFixture()
	{
		return fixture != null;
	}

	public boolean hasPhysics()
	{
		return hasFixture() && hasBody();
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

	@Override
	public void update(float dt)
	{
		super.update(dt);
		super.setLocation(body().getPosition().x, body.getPosition().y);
	}

	//public float xPixels()
	//{
	//	return super.x();
	//}
//
	//public float x()
	//{
	//	return xPixels() * PIXELS_TO_METERS;
	//}
//
	//public float yPixels()
	//{
	//	return super.y();
	//}
//
	//public float y()
	//{
	//	return yPixels() * PIXELS_TO_METERS;
	//}
}
