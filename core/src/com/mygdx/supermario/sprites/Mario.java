package com.mygdx.supermario.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.supermario.SuperMario;
import com.mygdx.supermario.screens.PlayScreen;

/**
 * Created by jjiang on 8/11/2017.
 */

public class Mario extends Sprite {
    public enum State{

        FAILING, JUMPING, STANDING, RUNNING
    };

    public State curentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private boolean runningRight;
    private float stateTimer;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        this.curentState = State.STANDING;
        this.previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 4; i++){

            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 11, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; i++){

            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 11, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();

        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / SuperMario.PPM, 16 / SuperMario.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2); // set position to the center of box2d (fixture)
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {

        curentState = getState();

        TextureRegion region;
        switch(curentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FAILING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if((b2body.getLinearVelocity().x <0 || !runningRight) && !region.isFlipX()){

            region.flip(true, false);
            runningRight = false;
        }else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){

            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = curentState == previousState ? stateTimer + dt : 0;
        previousState = curentState;
        return region;
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32  / SuperMario.PPM, 32 / SuperMario.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperMario.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        FixtureDef fdef2 = new FixtureDef();
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-2 / SuperMario.PPM, -6 / SuperMario.PPM), new Vector2(2 / SuperMario.PPM, -6 / SuperMario.PPM));
        fdef2.shape = feet;
        b2body.createFixture(fdef2);
    }


    public State getState() {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        }else if(b2body.getLinearVelocity().y < 0){
            return  State.FAILING;
        }else if(b2body.getLinearVelocity().x !=0){
            return State.RUNNING;
        }else
            return State.STANDING;
    }
}
