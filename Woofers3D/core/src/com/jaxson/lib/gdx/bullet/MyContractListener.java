package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;

class MyContactListener extends ContactListener
{
	@Override
	public boolean onContactAdded(int userValue0, int partId0, int index0, int userValue1, int partId1, int index1)
	{
		return true;
	}
}
