package com.bytesab.roadworks.road;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class RoadComponent implements Component {
	public static final int FORWARDS = 0, BACKWARDS = 1;
	
	public Vector2 start, ctrl, end;
	private int[] lanes;
	
	/**
	 * 
	 * @param start
	 * @param ctrl
	 * @param end
	 * @param lanes an array of each lanes direction
	 */
	public RoadComponent(Vector2 start, Vector2 ctrl, Vector2 end, int[] lanes) {
		this.start = start;
		this.ctrl = ctrl;
		this.end = end;
		this.lanes = lanes;
	}
	
	public int getNumLanes() {
		return lanes.length;
	}
	
	public int getLaneDirection(int laneNum) {
		return lanes[laneNum];
	}
	
	public int[] getLaneDirections() {
		return lanes;
	}
}
