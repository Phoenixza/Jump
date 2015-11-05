package com.welsermichael.supermario.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.welsermichael.supermario.Scenes.Hud;
import com.welsermichael.supermario.Sprites.Mario;
import com.welsermichael.supermario.SuperMario;
import com.welsermichael.supermario.Tools.B2WorldCreator;

/**
 * Created by user on 24.10.2015.
 */
public class PlayScreen implements Screen {

    private SuperMario game;
    private TextureAtlas atlas;


    private Mario player;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(SuperMario game) {

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        gamecam = new OrthographicCamera();
        //gamePort = new ScreenViewport(gamecam);
        gamePort = new FitViewport(SuperMario.V_WIDHT / SuperMario.PPM, SuperMario.V_HEIGHT / SuperMario.PPM, gamecam);
        //gamePort = new FitViewport(200,120, gamecam);
        //gamePort = new StretchViewport(800,480,gamecam);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / SuperMario.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);

        player = new Mario(world, this);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //if(Gdx.input.isTouched())
          //  gamecam.position.x += 100 * dt;
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2dody.applyLinearImpulse(new Vector2(0, 4f), player.b2dody.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)  && player.b2dody.getLinearVelocity().x <= 2){
            player.b2dody.applyLinearImpulse(new Vector2(0.1f, 0), player.b2dody.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  && player.b2dody.getLinearVelocity().x >= -2){
            player.b2dody.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2dody.getWorldCenter(),true);
        }

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        gamecam.position.x = player.b2dody.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // Main Cam when we running around
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();;
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
