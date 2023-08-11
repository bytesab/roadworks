package com.bytesab.roadworks;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bytesab.roadworks.road.RoadComponent;
import com.bytesab.roadworks.road.RoadRenderSystem;
import com.bytesab.roadworks.ui.CameraComponent;
import com.bytesab.roadworks.ui.InputHandlerSystem;

public class Roadworks extends ApplicationAdapter {
	private Engine engine;

	@Override
	public void create() {
		engine = new Engine();

		Entity road = engine.createEntity();
		road.add(new RoadComponent(new Vector2(100, 100), new Vector2(250, 150), new Vector2(300, 300),
				new int[] { RoadComponent.FORWARDS, RoadComponent.FORWARDS, 
						RoadComponent.BACKWARDS }));
		CameraComponent camComp = new CameraComponent(1280, 720);
		road.add(camComp);
		engine.addEntity(road);

		engine.addSystem(new RoadRenderSystem());
		
		engine.addSystem(new InputHandlerSystem(camComp.camera));
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.5f, 0.5f, 1);
		engine.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {

	}
}
