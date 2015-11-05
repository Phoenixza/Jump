package com.welsermichael.supermario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.welsermichael.supermario.Screens.PlayScreen;

public class SuperMario extends Game {
	public static final int V_WIDHT = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	public SpriteBatch batch;

	
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
