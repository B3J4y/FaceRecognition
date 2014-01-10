
public class Feat {
	int value;
	boolean face;
	Shape shape;
	Pair position;
	Pair size;
	
	
	
	public Feat(int value, boolean face, Shape shape, Pair position, Pair size){
		this.value = value;
		this.face = face;
		this.shape = shape;
		this.position = position;
		this.size = size;
	}
	
	@Override
	public String toString(){
		
		return shape.name() + ";" + value + ";" + position.getFirst() + ";" 
				+ position.getSecond() + ";" + size.getFirst() + ";" + size.getSecond() + ";"
				+ face;
	}
	
	enum Shape{
		HOOR2,
		VERT2,
		HOOR3,
		VERT3,
		QUAD;
	}
}
