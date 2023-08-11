package com.bytesab.roadworks.road;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bytesab.roadworks.ui.CameraComponent;

public class RoadRenderSystem extends EntitySystem {
	private static final float LANE_WIDTH = 30, LINE_THICKNESS = 1.5f;

	private ShapeRenderer shapeRenderer;
	private OrthographicCamera orthographicCamera;
	private ImmutableArray<Entity> roadEntities;
	private ComponentMapper<RoadComponent> roadMapper;

	public RoadRenderSystem() {
		shapeRenderer = new ShapeRenderer();
		roadMapper = ComponentMapper.getFor(RoadComponent.class);
	}

	@Override
	public void addedToEngine(Engine engine) {
		roadEntities = engine.getEntitiesFor(Family.all(RoadComponent.class).get());
		// should b fineee only should have a singular camera comp anyway
		orthographicCamera = roadEntities.first().getComponent(CameraComponent.class).camera;
	}

	@Override
	public void update(float deltaTime) {
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
		for (int i = 0; i < roadEntities.size(); ++i) {
			Entity entity = roadEntities.get(i);
			RoadComponent r = roadMapper.get(entity);

			/*
			 * shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			 * shapeRenderer.circle(r.start.x, r.start.y, 10);
			 * shapeRenderer.circle(r.ctrl.x, r.ctrl.y, 10); shapeRenderer.circle(r.end.x,
			 * r.end.y, 10); shapeRenderer.end();
			 */

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			// draw background
			shapeRenderer.setColor(Color.DARK_GRAY);
			drawBezierCurve(r.start, r.ctrl, r.end, 0, LANE_WIDTH * r.getNumLanes(), false);
			// draw lines
			shapeRenderer.setColor(Color.WHITE);
			drawParallelBezierCurves(r.start, r.ctrl, r.end, r.getNumLanes(), r.getLaneDirections(), LANE_WIDTH);
			shapeRenderer.end();
		}
	}

	private void drawBezierCurve(Vector2 P0, Vector2 P1, Vector2 P2, float offsetDistance, float thickness,
			boolean isDashed) {
		float t = 0;
		Vector2 lastPoint = null;

		// TODO: make divis a quality setting?
		for (int j = 0; j <= 100; j++) {
			t = (float) j / 100;
			Vector2 point = evaluateBezierPoint(P0, P1, P2, t, offsetDistance);

			if (lastPoint != null && (!isDashed || j % 4 == 0 || j % 4 == 1)) {
				shapeRenderer.rectLine(lastPoint.x, lastPoint.y, point.x, point.y, thickness);
			}

			lastPoint = point;
		}
	}

	private void drawParallelBezierCurves(Vector2 P0, Vector2 P1, Vector2 P2, float numberOfCurves,
			int[] laneDirections, float laneWidth) {
		int lastDirection = -1;
		int k = 0;
		for (float i = -numberOfCurves / 2; i <= numberOfCurves / 2; ++i) {
			// if last lane direction is different from current solid otherwise dashed
			drawBezierCurve(P0, P1, P2, laneWidth * i, LINE_THICKNESS, laneDirections[k] == lastDirection);
			lastDirection = laneDirections[k];
			if (k < laneDirections.length - 1) {
				k++;
			} else {
				lastDirection = -1;
			}
		}
	}

	private Vector2 evaluateBezierPoint(Vector2 P0, Vector2 P1, Vector2 P2, float t, float offset) {
		Vector2 T0 = P1.cpy().sub(P0);
		Vector2 T2 = P2.cpy().sub(P1);

		// Calculate normal vectors (perpendicular to tangents)
		Vector2 N0 = new Vector2(-T0.y, T0.x).nor();
		Vector2 N1 = new Vector2(-P1.y, P1.x).nor();
		Vector2 N2 = new Vector2(-T2.y, T2.x).nor();

		// TODO: next time figure out how to adjust these offsets
		// Offset control points perpendicularly
		Vector2 P0Prime = P0.cpy().add(N0.scl(offset * 0.9f));
		Vector2 P1Prime = P1.cpy().add(N1.scl(offset));
		Vector2 P2Prime = P2.cpy().add(N2.scl(offset * 0.9f));

		// Calculate new control points
		// Vector2 P1Prime = P0Prime;//.cpy().lerp(P1, 1);

		float u = 1 - t;
		return P0Prime.cpy().scl(u * u).add(P1Prime.cpy().scl(2 * u * t)).add(P2Prime.cpy().scl(t * t));
	}
}
