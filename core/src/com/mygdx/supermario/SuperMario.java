package com.mygdx.supermario;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.supermario.screens.PlayScreen;

public class SuperMario extends Game {
	public SpriteBatch batch; // container holds images and textures etc
	Texture img;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
