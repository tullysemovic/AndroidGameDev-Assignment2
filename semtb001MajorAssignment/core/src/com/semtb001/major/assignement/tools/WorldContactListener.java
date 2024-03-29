package com.semtb001.major.assignement.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.semtb001.major.assignement.sprites.Player;
import com.semtb001.major.assignement.sprites.Sheep;

// Class for handling contact in the world (Box2d)
public class WorldContactListener implements ContactListener {

    private Box2DWorldCreator creator;

    // World contact listener (listens for Box2D contact in the world)
    public WorldContactListener(Box2DWorldCreator creator) {
        this.creator = creator;
    }

    @Override
    public void beginContact(Contact contact) {

        // Contact fixtures setup
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // This code detects if a sheep and a player has collided
        // If fixture A is a player
        if(fixA.getUserData() instanceof Player){

            // If fixture B is a sheep
            if(fixB.getUserData() instanceof Sheep){

                // Call the hitSheep method
                ((Sheep) fixB.getUserData()).sheepHit();
            }
        }

        // If fixture B is a player
        if(fixB.getUserData() instanceof Player){

            // If fixture A is a sheep
            if(fixA.getUserData() instanceof Sheep){

                // Call the hitSheep method
                ((Sheep) fixA.getUserData()).sheepHit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
