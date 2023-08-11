package com.bytesab.roadworks.ui;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component {
	public OrthographicCamera camera;
	
	public CameraComponent(int width, int height) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
	}
}
