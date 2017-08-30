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

		System.out.println(x());

		this.shape = new PolygonShape();
		this.shape.setAsBox(
				width() / 2, height() / 2,
				new Vector2(originalWidth() * PIXELS_TO_METERS / 2, originalHeight() * PIXELS_TO_METERS / 2),
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
		super.setLocation(x * METERS_TO_PIXELS, y * METERS_TO_PIXELS);
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
		super.setLocation(body().getPosition().x * METERS_TO_PIXELS, body.getPosition().y * METERS_TO_PIXELS);
	}

	public float xPixels()
	{
		return super.x();
	}

	public float x()
	{
		return xPixels() * PIXELS_TO_METERS;
	}

	public float yPixels()
	{
		return super.y();
	}

	public float y()
	{
		return yPixels() * PIXELS_TO_METERS;
	}

	public float widthPixels()
	{
		return super.width();
	}

	public float width()
	{
		return widthPixels() * PIXELS_TO_METERS;
	}

	public float heightPixels()
	{
		return super.height();
	}

	public float height()
	{
		return heightPixels() * PIXELS_TO_METERS;
	}

	public Vector2 location()
	{
		return super.location().scl(PIXELS_TO_METERS);
	}

	public Vector2 origin()
	{
		return super.origin().scl(PIXELS_TO_METERS);
	}

	@Override
	public float originX()
	{
		return super.originX() * PIXELS_TO_METERS;
	}

	@Override
	public float originY()
	{
		return super.originY() * PIXELS_TO_METERS;
	}

	@Override
	public void setCenter(float x, float y)
	{
		super.setCenter(x * METERS_TO_PIXELS, y * METERS_TO_PIXELS);
	}

	@Override
	public void setOrigin(float originX, float originY)
	{
		super.setOrigin(originX * METERS_TO_PIXELS, originY * METERS_TO_PIXELS);
	}

	@Override
	public void setSize(float width, float height)
	{
		super.setSize(width * METERS_TO_PIXELS, height * METERS_TO_PIXELS);
	}

	@Override
	public void translateX(float x)
	{
		super.translateX(x * METERS_TO_PIXELS);
	}

	@Override
	public void translateY(float y)
	{
		super.translateY(y * METERS_TO_PIXELS);
	}
}
