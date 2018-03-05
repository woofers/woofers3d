package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

class MyContactListener extends ContactListener
{
    @Override
    public boolean onContactAdded(btCollisionObject body1,
                                  int part1,
                                  int index1,
                                  btCollisionObject body2,
                                  int part2,
                                  int index2)
    {
        return true;
    }
}
