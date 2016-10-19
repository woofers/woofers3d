package com.jaxson.lib.gdx.graphics.g3d.entities.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;

public abstract class AnimatedEntity extends Entity
{
	private AnimationController animationController;

	public AnimatedEntity(Model model)
	{
		this(new ModelInstance(model));
	}

	public AnimatedEntity(ModelInstance modelInstance)
	{
		super(modelInstance);
		this.animationController = new AnimationController(modelInstance());
	}

	public void action(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().action(id, offset, duration,
				loopCount, speed, listener,
				transitionTime);
	}

	public void action(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().action(id, loopCount, speed, listener,
				transitionTime);
	}

	public void animate(String id,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().animate(id, listener, transitionTime);
	}

	public void animate(String id, float transitionTime)
	{
		animationController().animate(id, transitionTime);
	}

	public void animate(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().animate(id, offset, duration,
				loopCount, speed, listener,
				transitionTime);
	}

	public void animate(String id,
			int loopCount,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().animate(id, loopCount, listener,
				transitionTime);
	}

	public void animate(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().animate(id, loopCount, speed, listener,
				transitionTime);
	}

	public Animation animation()
	{
		return animationDescription().animation;
	}

	public AnimationController animationController()
	{
		return animationController;
	}

	public AnimationDesc animationDescription()
	{
		return animationController().current;
	}

	public String animationId()
	{
		return animation().id;
	}

	public AnimationListener animationListener()
	{
		return animationDescription().listener;
	}

	public boolean isPaused()
	{
		return animationController().paused;
	}

	public void queue(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().queue(id, offset, duration, loopCount,
				speed, listener, transitionTime);
	}

	public void queue(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		animationController().queue(id, loopCount, speed, listener,
				transitionTime);
	}

	public void setAnimation(String id)
	{
		animationController().setAnimation(id);
	}

	public void setAnimation(String id, AnimationListener listener)
	{
		animationController().setAnimation(id, listener);
	}

	public void setAnimation(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener)
	{
		animationController().setAnimation(id, offset, duration, loopCount,
				speed, listener);
	}

	public void setAnimation(String id, int loopCount)
	{
		animationController().setAnimation(id, loopCount);
	}

	public void setAnimation(String id,
			int loopCount,
			AnimationListener listener)
	{
		animationController().setAnimation(id, loopCount, listener);
	}

	public void setAnimation(String id,
			int loopCount,
			float speed,
			AnimationListener listener)
	{
		animationController().setAnimation(id, loopCount, speed, listener);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		animationController().update(dt);
	}
}
