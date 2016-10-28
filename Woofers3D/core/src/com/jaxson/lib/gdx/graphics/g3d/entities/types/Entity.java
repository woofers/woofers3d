package com.jaxson.lib.gdx.graphics.g3d.entities.types;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.math.Circle;

public abstract class Entity extends GameObject
{
	private static final int MATRIX_DIRECTION_X = 8;
	private static final int MATRIX_DIRECTION_Y = 9;
	private static final int MATRIX_DIRECTION_Z = 10;

	private static final int ROOT_NODE_LOCATION = 0;
	private static final float FORWARD_DIRECTION = 0f;
	private static final float BACKWARD_DIRECTION = 180f;

	private ModelInstance modelInstance;

	public Entity(Model model)
	{
		this(new ModelInstance(model));
	}

	public Entity(ModelInstance modelInstance)
	{
		this.modelInstance = modelInstance;
	}

	public Ray backwardRay()
	{
		return ray(BACKWARD_DIRECTION);
	}

	public BoundingBox boundingBox()
	{
		return modelInstance().calculateBoundingBox(new BoundingBox());
	}

	protected void calculateTransforms()
	{
		modelInstance().calculateTransforms();
	}

	public Vector3 center()
	{
		return boundingBox().getCenter(new Vector3());
	}

	public Vector3 centerLocation()
	{
		return center().add(location());
	}

	public float diameter()
	{
		return size().len();
	}

	public Vector3 direction()
	{
		float[] matrix = transformValues();
		return new Vector3(matrix[MATRIX_DIRECTION_X],
				matrix[MATRIX_DIRECTION_Y],
				matrix[MATRIX_DIRECTION_Z]);
	}

	@Override
	public void dispose()
	{
		model().dispose();
	}

	public Vector3 distance(Entity entity)
	{
		return distance(entity.location());
	}

	public Vector3 distance(Vector3 location)
	{
		return location().sub(location);
	}

	public Ray forwardRay()
	{
		return ray(FORWARD_DIRECTION);
	}

	public boolean isVisible(Camera camera)
	{
		return camera.frustum.sphereInFrustum(centerLocation(), radius());
	}

	public Vector3 location()
	{
		return transform().getTranslation(new Vector3());
	}

	public Model model()
	{
		return modelInstance().model;
	}

	public ModelInstance modelInstance()
	{
		return modelInstance;
	}

	public void moveTo(Vector3 location)
	{
		transform().setToTranslation(location);
	}

	public Vector3 originalSize()
	{
		return boundingBox().getDimensions(new Vector3());
	}

	public float radius()
	{
		return diameter() * Circle.DIAMETER_TO_RADIUS;
	}

	public Ray ray(Entity entity)
	{
		return ray(entity.location());
	}

	public Ray ray(float direction)
	{
		return new Ray(location(),
				direction().rotate(Vector3.Y,
						direction));
	}

	public Ray ray(Vector3 location)
	{
		return new Ray(location(), distance(location));
	}

	public Quaternion rotationQuaternion()
	{
		return transform().getRotation(new Quaternion());
	}

	public Node rootNode()
	{
		return modelInstance().nodes.get(ROOT_NODE_LOCATION);
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		rotate(Vector3.X, pitch);
		rotate(Vector3.Y, yaw);
		rotate(Vector3.Z, roll);
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(Vector3 axis, float amount)
	{
		transform().rotate(axis, amount);
	}

	public Vector3 rotation()
	{
		Quaternion rotation = rotationQuaternion();
		return new Vector3(rotation.getYaw(),
				rotation.getPitch(),
				rotation.getRoll());
	}

	public Vector3 scale()
	{
		return rootNode().scale;
	}

	public void scale(float scale)
	{
		scale(new Vector3(scale, scale, scale));
	}

	public void scale(Vector3 scale)
	{
		rootNode().scale.set(scale);
		calculateTransforms();
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		transform().setFromEulerAngles(yaw, pitch, roll);
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(Quaternion quaternion)
	{
		transform().set(quaternion);
	}

	public void setSize(Vector3 size)
	{
		scale(GdxMath.divideVector(size, originalSize()));
	}

	public Vector3 size()
	{
		return originalSize().scl(scale());
	}

	public Matrix4 transform()
	{
		return modelInstance().transform;
	}

	public float[] transformValues()
	{
		return transform().getValues();
	}

	public void translate(Vector3 translation)
	{
		transform().translate(translation);
	}

	public void translateABS(Vector3 translation)
	{
		transform().trn(translation);
	}

	protected static Model readModel(String modelPath)
	{
		return new GdxFile(modelPath).readObject();
	}
}
