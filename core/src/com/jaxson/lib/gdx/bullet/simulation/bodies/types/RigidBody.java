package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.bullet.simulation.MotionState;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.util.Resetable;
import com.jaxson.lib.util.Printer;

public class RigidBody extends ShapeBody<btRigidBody, Shape>
        implements Resetable
{
    private MotionState motionState;
    protected boolean movingUp;
    protected float previousVelocity;

    public RigidBody(Model model, Shape shape)
    {
        this(model, shape, MASS);
    }

    public RigidBody(Model model, Shape shape, float mass)
    {
        this(new ModelInstance(model), shape, mass);
    }

    public RigidBody(ModelInstance modelInstance, Shape shape)
    {
        this(modelInstance, shape, MASS);
    }

    public RigidBody(ModelInstance modelInstance, Shape shape, float mass)
    {
        super(modelInstance,
                new btRigidBody(mass, null, null, shape.inertia(mass)),
                shape,
                mass);
        setMotionState(new MotionState(transform()));
    }

    public void clearForces()
    {
        body().clearForces();
    }

    public Vector3 angularVelocity()
    {
        return body().getAngularVelocity();
    }

    public void setAngularVelocity(Vector3 velocity)
    {
        body().setAngularVelocity(velocity);
    }

    public void applyCentralImpulse(Ray ray)
    {
        applyCentralImpulse(ray, 1f);
    }

    public void applyCentralImpulse(Ray ray, float impulse)
    {
        applyCentralImpulse(ray.direction.scl(impulse));
    }

    public void applyCentralImpulse(Vector3 impulse)
    {
        activate();
        body().applyCentralImpulse(impulse);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        motionState.dispose();
    }

    public Vector3 linearVelocity()
    {
        return body().getLinearVelocity();
    }

    public void setLinearVelocity(Vector3 velocity)
    {
        body().setLinearVelocity(velocity);
    }

    public MotionState motionState()
    {
        return motionState;
    }

    public boolean onGround()
    {
        return (long)linearVelocity().y == 0l && !movingUp;
    }

    public boolean isFalling()
    {
        return !onGround();
    }

    private void recalculateInertia()
    {
        body().setMassProps(mass(), inertia());
    }

    @Override
    public String toString()
    {
        return new Printer(getClass(),
                new Printer.Label("Linear Velocity", angularVelocity()),
                new Printer.Label("Angular Velocity", linearVelocity()),
                new Printer.Label("Location", location()),
                new Printer.Label("Scale", scale()),
                new Printer.Label("Size", size()),
                new Printer.Label("Original Size", originalSize())).toString();
    }

    @Override
    public void setCollisionShape(Shape shape)
    {
        super.setCollisionShape(shape);
        recalculateInertia();
    }

    @Override
    public void setMass(float mass)
    {
        super.setMass(mass);
        recalculateInertia();
    }

    public void setMotionState(MotionState motionState)
    {
        this.motionState = motionState;
        body().setMotionState(motionState);
    }

    @Override
    public void reset()
    {
        super.reset();
        setRotation(0f, 0f, 0f);
        setLinearVelocity(Vector3.Zero);
        setAngularVelocity(Vector3.Zero);
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);

        float velocity = linearVelocity().y;

        if (!(Math.signum(velocity) != Math.signum(previousVelocity) && isFalling()))
        {
            if (Math.abs(velocity) > 1f)
            {
                if (velocity > 0f)
                {
                    movingUp = true;
                }
                else if (velocity < 0f)
                {
                    movingUp = false;
                }
            }
        }

        previousVelocity = velocity;
    }
}
