package com.mygdx.supermario.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.supermario.SuperMario;

/**
 * Created by jjiang on 8/11/2017.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){

        this.map = map;
        this.world = world;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / SuperMario.PPM, (bounds.getY() + bounds.getHeight() / 2) / SuperMario.PPM);
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / SuperMario.PPM, bounds.getHeight() / 2 / SuperMario.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }

}
