package com.welsermichael.supermario.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Rectangle;
import com.welsermichael.supermario.SuperMario;


/**
 * Created by user on 25.10.2015.
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMap tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() /2)/ SuperMario.PPM, (bounds.getY() + bounds.getHeight() / 2 )/SuperMario.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2/SuperMario.PPM, bounds.getHeight()/2/SuperMario.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
