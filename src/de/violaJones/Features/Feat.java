package de.violaJones.Features;

import java.util.ArrayList;
import java.util.List;

public class Feat {
	boolean face;
	Shape shape;
	Pair position;
	Pair size;
	public List<ValueFace> valuesFaces;
	
	
	
	public Feat(Shape shape, Pair position, Pair size){
		this.valuesFaces = new ArrayList<ValueFace>();
		this.shape = shape;
		this.position = position;
		this.size = size;
	}
	
	@Override
	public String toString(){
		
		return shape.name() + ";"+ ";" + position.getFirst() + ";" 
				+ position.getSecond() + ";" + size.getFirst() + ";" + size.getSecond() + ";"
				+ face;
	}
	
	enum Shape{
		HOR2,
		VERT2,
		HOR3,
		VERT3,
		QUAD;
	}
}
