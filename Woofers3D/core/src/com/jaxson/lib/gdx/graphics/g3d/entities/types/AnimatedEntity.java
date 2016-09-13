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
		this.animationController = new AnimationController(getModelInstance());
	}

	public AnimatedEntity(String modelPath)
	{
		this(readModel(modelPath));
	}

	public void action(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().action(id, offset, duration, loopCount, speed,
				listener, transitionTime);
	}

	public void action(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().action(id, loopCount, speed, listener,
				transitionTime);
	}

	public void animate(String id,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().animate(id, listener, transitionTime);
	}

	public void animate(String id, float transitionTime)
	{
		getAnimationController().animate(id, transitionTime);
	}

	public void animate(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().animate(id, offset, duration, loopCount, speed,
				listener, transitionTime);
	}

	public void animate(String id,
			int loopCount,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().animate(id, loopCount, listener,
				transitionTime);
	}

	public void animate(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().animate(id, loopCount, speed, listener,
				transitionTime);
	}

	public Animation getAnimation()
	{
		return getAnimationDescription().animation;
	}

	public AnimationController getAnimationController()
	{
		return animationController;
	}

	public AnimationDesc getAnimationDescription()
	{
		return getAnimationController().current;
	}

	public String getAnimationId()
	{
		return getAnimation().id;
	}

	public AnimationListener getAnimationListener()
	{
		return getAnimationDescription().listener;
	}

	public boolean isPaused()
	{
		return getAnimationController().paused;
	}

	public void queue(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().queue(id, offset, duration, loopCount, speed,
				listener, transitionTime);
	}

	public void queue(String id,
			int loopCount,
			float speed,
			AnimationListener listener,
			float transitionTime)
	{
		getAnimationController().queue(id, loopCount, speed, listener,
				transitionTime);
	}

	public void setAnimation(String id)
	{
		getAnimationController().setAnimation(id);
	}

	public void setAnimation(String id, AnimationListener listener)
	{
		getAnimationController().setAnimation(id, listener);
	}

	public void setAnimation(String id,
			float offset,
			float duration,
			int loopCount,
			float speed,
			AnimationListener listener)
	{
		getAnimationController().setAnimation(id, offset, duration, loopCount,
				speed, listener);
	}

	public void setAnimation(String id, int loopCount)
	{
		getAnimationController().setAnimation(id, loopCount);
	}

	public void setAnimation(String id,
			int loopCount,
			AnimationListener listener)
	{
		getAnimationController().setAnimation(id, loopCount, listener);
	}

	public void setAnimation(String id,
			int loopCount,
			float speed,
			AnimationListener listener)
	{
		getAnimationController().setAnimation(id, loopCount, speed, listener);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		getAnimationController().update(dt);
	}
}
