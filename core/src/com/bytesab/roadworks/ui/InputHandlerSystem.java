package com.bytesab.roadworks.ui;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class InputHandlerSystem extends EntitySystem {
	public static final float 
	CAMERA_MOVE_SPEED = 300f, 
	CAMERA_ZOOM_SPEED = 0.5f,
	CAMERA_ZOOM_MIN = 0.5f,
	CAMERA_ZOOM_MAX = 3.0f;
	
    private OrthographicCamera camera;

    public InputHandlerSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
    	if (Gdx.input.isKeyPressed(Input.Keys.Q) && camera.zoom <= CAMERA_ZOOM_MAX) {
    		camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.E) && camera.zoom >= CAMERA_ZOOM_MIN) {
    		camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.W)) {
    		camera.translate(0, CAMERA_MOVE_SPEED * deltaTime);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.S)) {
    		camera.translate(0, -CAMERA_MOVE_SPEED * deltaTime);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.A)) {
    		camera.translate(-CAMERA_MOVE_SPEED * deltaTime, 0);
    	}
    	if (Gdx.input.isKeyPressed(Input.Keys.D)) {
    		camera.translate(CAMERA_MOVE_SPEED * deltaTime, 0);
    	}
    	camera.update();
    }
}
