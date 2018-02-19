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
        // Reset to Zero When Object is In Contact with Another Body
        // Used for Determining if the Object is on the Ground
        body1.setUserPointer(0l);
        body2.setUserPointer(0l);
        return true;
    }
}
