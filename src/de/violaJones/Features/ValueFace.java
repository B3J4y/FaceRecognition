package de.violaJones.Features;

public class ValueFace implements Comparable<ValueFace> {
	public int value;
	public boolean face;
	public double weight;
	
	public ValueFace(int value, boolean face){
		this.value = value;
		this.face = face;
	}

	@Override
	public int compareTo(ValueFace o) {
		return this.value - ((ValueFace) o).value;
	}
}
