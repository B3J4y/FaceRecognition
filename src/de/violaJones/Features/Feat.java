package de.violaJones.Features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Feat {
	boolean face;
	Shape shape;
	Pair position;
	Pair size;
	int treshold;
	int polarity;
	public List<ValueFace> valuesFaces;
	
	
	
	public Feat(Shape shape, Pair position, Pair size){
		this.valuesFaces = new ArrayList<ValueFace>();
		this.shape = shape;
		this.position = position;
		this.size = size;
	}
	
	public int generateTreshold(){
		Collections.sort(valuesFaces);
		int[] treshold = new int[2];
		int countDownToUp = 0;
		int countUpToDown = 0;
		for(int i = 0; i < valuesFaces.size(); i++){
			if(valuesFaces.get(i).face){
				countDownToUp++;
			} else {
				if(i > 0){
					treshold[0] = (valuesFaces.get(i).value - valuesFaces.get(i - 1).value) /2 + valuesFaces.get(i - 1).value;
				}
				break;
			}
		}
		for(int i = valuesFaces.size() - 1; i >=0; i--){
			if(valuesFaces.get(i).face){
				countUpToDown++;
			} else {
				if(i < valuesFaces.size() - 1){
					treshold[1] = (valuesFaces.get(i + 1).value - valuesFaces.get(i).value) /2 + valuesFaces.get(i).value;
				}
				break;
			}
		}
		if(countDownToUp > countUpToDown){
			this.polarity = 1;
			this.treshold = treshold[0];
		} else {
			this.polarity = -1;
			this.treshold = treshold[1];
		}
		return this.treshold;
	}
	
	public int h(int value){
		if(value * this.polarity < this.treshold * this.polarity){
			return 1;
		} else {
			return 0;
		}
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
