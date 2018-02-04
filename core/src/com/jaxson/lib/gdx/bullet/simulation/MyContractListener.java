package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

class MyContactListener extends ContactListener
{
    @Override
    public boolean onContactAdded(btCollisionObject colObj0,
                                  int partId0,
                                  int index0,
                                  btCollisionObject colObj1,
                                  int partId1,
                                  int index1)
    {


        return true;
    }
}
