package com.bytesab.roadworks.road.junction;

import com.badlogic.ashley.core.Component;

public class JunctionsComponent implements Component {
	public Junction start, end;

	public JunctionsComponent(Junction start, Junction end) {
		this.start = start;
		this.end = end;
	}
}
