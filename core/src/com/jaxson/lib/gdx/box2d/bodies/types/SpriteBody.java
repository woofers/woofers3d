package com.jaxson.lib.gdx.box2d.bodies.types;

import static com.jaxson.lib.gdx.box2d.simulation.Box2DWorld.METERS_TO_PIXELS;
import static com.jaxson.lib.gdx.box2d.simulation.Box2DWorld.PIXELS_TO_METERS;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jaxson.lib.gdx.box2d.simulation.Box2DWorld;
import com.jaxson.lib.gdx.graphics.g2d.entities.types.SpriteActor;
import com.jaxson.lib.math.MyMath;
import com.jaxson.lib.util.Unwrapable;
import com.badlogic.gdx.physics.box2d.Shape;

public class SpriteBody extends SpriteActor
{
    private Fixture fixture;
    private PolygonShape shape;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Hitbox hitbox;

    public SpriteBody(Texture texture, BodyType type, float density)
    {
        super(texture);

        setOrigin();

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
        if (!hasFixture()) return null;
        return fixture().getBody();
    }

    public void createBody(Box2DWorld world)
    {
        this.bodyDef.fixedRotation = true;

        this.hitbox = new Hitbox(width(), height());
        hitbox.apply(bodyDef, x(), y());
        hitbox.apply(fixtureDef, rotation());

        Body body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        hitbox.dispose();
    }


    @Override
    public void dispose()
    {
        super.dispose();
        if (shape != null) shape.dispose();
    }

    public boolean hasBody()
    {
        return body() != null;
    }

    public Fixture fixture()
    {
        return fixture;
    }

    public boolean hasFixture()
    {
        return fixture() != null;
    }

    public boolean hasPhysics()
    {
        return hasFixture() && hasBody();
    }

    @Override
    public float height()
    {
        return heightPixels() * PIXELS_TO_METERS;
    }

    public float heightPixels()
    {
        return super.height();
    }

    @Override
    public Vector2 location()
    {
        return super.location().scl(PIXELS_TO_METERS);
    }

    @Override
    public void moveCenterTo(Vector2 center)
    {
        super.moveCenterTo(center.scl(METERS_TO_PIXELS));
    }

    @Override
    public void moveTo(Vector2 location)
    {
        super.moveTo(location.scl(METERS_TO_PIXELS));
        if (hasBody()) body().setTransform(
                location.x * PIXELS_TO_METERS, location.y * PIXELS_TO_METERS,
                rotation() * MyMath.DEGREES_TO_RADIANS);
    }

    @Override
    public Vector2 origin()
    {
        return super.origin().scl(PIXELS_TO_METERS);
    }

    public void resetVelocity()
    {
        if (!hasBody()) return;
        body().setLinearVelocity(new Vector2());
        body().setAngularVelocity(0f);
    }

    @Override
    public void scale(Vector2 scale)
    {
        super.scale(scale);
    }

    @Override
    public void setOrigin(Vector2 origin)
    {
        super.setOrigin(origin.scl(METERS_TO_PIXELS));
    }

    @Override
    public void setRotation(float roll)
    {
        super.setRotation(roll);
        if (!hasBody()) return;
        body().setTransform(
                body().getPosition(), roll * MyMath.DEGREES_TO_RADIANS);
    }

    @Override
    public void setSize(float width, float height)
    {
        super.setSize(width * METERS_TO_PIXELS, height * METERS_TO_PIXELS);
    }

    @Override
    public void translate(Vector2 translation)
    {
        translation.scl(METERS_TO_PIXELS);
        super.translate(translation);
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
        if (!hasBody()) return;
        super.moveTo(
                body().getPosition()
                        .add(
                                -originalWidth() * PIXELS_TO_METERS / 2,
                                -originalHeight() * PIXELS_TO_METERS / 2)
                        .scl(METERS_TO_PIXELS));
        setRotation(body().getAngle() * MyMath.RADIANS_TO_DEGREES);
    }

    @Override
    public float width()
    {
        return widthPixels() * PIXELS_TO_METERS;
    }

    public float widthPixels()
    {
        return super.width();
    }

    @Override
    public float x()
    {
        return xPixels() * PIXELS_TO_METERS;
    }

    public float xPixels()
    {
        return super.x();
    }

    @Override
    public float y()
    {
        return yPixels() * PIXELS_TO_METERS;
    }

    public float yPixels()
    {
        return super.y();
    }
}
