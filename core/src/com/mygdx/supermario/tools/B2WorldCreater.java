package com.mygdx.supermario.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.supermario.SuperMario;
import com.mygdx.supermario.sprites.Brick;
import com.mygdx.supermario.sprites.Coin;

/**
 * Created by jjiang on 8/11/2017.
 */

public class B2WorldCreater {

    public B2WorldCreater(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SuperMario.PPM, (rect.getY() + rect.getHeight() / 2) / SuperMario.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SuperMario.PPM, rect.getHeight() / 2 / SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SuperMario.PPM, (rect.getY() + rect.getHeight() / 2) / SuperMario.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SuperMario.PPM, rect.getHeight() / 2 / SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Brick(world, map, rect);
        }

        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Coin(world, map, rect);

        }

    }
}
