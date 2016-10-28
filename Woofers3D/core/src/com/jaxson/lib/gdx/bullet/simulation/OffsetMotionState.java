package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.badlogic.gdx.math.Quaternion;

public class OffsetMotionState extends MyMotionState
{
	private EntityBody body;
	private Vector3 offset;

	public OffsetMotionState(Matrix4 transform, EntityBody body, Vector3 offset)
	{
		super(transform);
		this.body = body;
		this.offset = offset;
	}

	public void setOffset(Vector3 offset)
	{
		this.offset = offset;
	}

	public Vector3 offset()
	{
		return offset;
	}

	@Override
	public void getWorldTransform(Matrix4 worldTransform)
	{
		transform().idt();
		Vector3 position = body.location();
		Quaternion rotation = body.rotationQuaternion();
		Vector3 finalOffset = offset.mul(rotation);

		transform().set(new Vector3(position.x + finalOffset.x,
				position.y + finalOffset.y, position.z + finalOffset.z),
				new Quaternion(rotation.x, rotation.y, rotation.z, rotation.w));

		super.getWorldTransform(transform());
	}

	@Override
	public void setWorldTransform(Matrix4 worldTransform)
	{
		Vector3 btPosition = worldTransform.getTranslation(new Vector3());
		Quaternion btRotation = worldTransform.getRotation(new Quaternion());
		Quaternion rotation = new Quaternion(btRotation.w, btRotation.x, btRotation.y, btRotation.z);
		Vector3 finalOffset = offset.mul(rotation);
		Vector3 position = new Vector3(btPosition.x - finalOffset.x, btPosition.y - finalOffset.y, btPosition.z - finalOffset.z);

		body.moveTo(position);
		body.setRotation(position);

		super.setWorldTransform(worldTransform);
	}
}
