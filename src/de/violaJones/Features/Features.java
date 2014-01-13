package de.violaJones.Features;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;


public class Features {
	public int numbFaces = 0;
	public int numbNonFaces = 0;
	public final String FACES = "./res/png/faces";
	public final String NONFACES = "./res/png/nonfaces";
	public int[][][] integralFaces;
	public int[][][] integralNonFaces;
	public final int SIZE = 24;
	public List<SimpleEntry<Integer, List<Feat>>> kvp;
	
	public List<Feat> result;
	private int countMyFeat;
	
	public Features(){
		//numbFaces = new File(FACES).listFiles().length;
		//numbNonFaces = new File(NONFACES).listFiles().length;
		numbFaces = 50;
		numbNonFaces = 50;
		integralFaces = new int[numbFaces][SIZE][SIZE];
		integralNonFaces = new int[numbNonFaces][SIZE][SIZE];
		kvp = new ArrayList<SimpleEntry<Integer, List<Feat>>>();
		result = new ArrayList<Feat>();
	}
	
	public void integralImages(String path, int max, int[][][] data) throws IOException{
		for(int i = 0; i < max; i++){
			
			File[] filesList = new File(path).listFiles();
			Arrays.sort(filesList);
			filesList = Arrays.copyOfRange(filesList, 0, max);
			BufferedImage buffIm = ImageIO.read(filesList[i]);
			//System.out.println(filesList[i].getName());
			String str = "";
			String integ = "";
			for(int n = 0; n < SIZE; n++){
				str = "";
				integ = "";
				for(int m = 0; m < SIZE; m++){
					int pixel = buffIm.getRaster().getPixel(m, n, (int[]) null)[0];
					if(m == 0){
						if(n == 0){
							data[i][n][m] = pixel;
						} else {
							data[i][n][m] = pixel 
									+data[i][n - 1][m];
						}
					} else {
						if(n == 0){
							data[i][n][m] = data[i][n][m - 1] + pixel;
						} else {
							data[i][n][m] = data[i][n][m - 1] 
									+ pixel
									+ data[i][n -1][m] 
											- data[i][n -1][m - 1];
						}
					}
					integ += data[i][n][m] + " ";
				}
			}
		}
	}
	
	public void generateFeat(int[][][] data, boolean face){
		
		int numFeat = 0;
		for(int pic = 0; pic < data.length; pic++){
			countMyFeat = 0;
			numFeat = 0;
			List<Feat> feats = new ArrayList<Feat>();
			//HOR2
			//vert for vertical scale
			for(int vert = 0; vert <= SIZE; vert++){
				//scale for horizontal scale
				for(int scale = 0; scale <= SIZE/2; scale++){
					for(int n = 0; n < SIZE - vert; n++){
						for(int m = 0; m < SIZE-(1+2*scale); m++){
							int white = 0;
							int grey = 0;
							if(n==0){
								if(m == 0){
									white = data[pic][vert][scale];
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+scale),  new Pair(n+vert,m+1+2*scale));								
								} else {
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m-1),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+scale),  new Pair(n+vert,m+1+2*scale));	
								}
							}else {
								if(m == 0){
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n-1,m+scale),  new Pair(n+vert,m+scale));	
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));		
								} else {
									white = getRectangel(data, pic, new Pair(n-1,m-1), 
											new Pair(n-1,m+scale), 
											new Pair(n+vert,m-1), 
											new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));
								}
							}
							addFeature(numFeat,
									Feat.Shape.HOR2,
									new Pair(n,m), 
									new Pair(vert,scale),
									(grey - white),
									face);
							numFeat++;
						}
					}
				}
				
			}
			//HOR3
			//vert for vertical scale
			for(int vert = 0; vert < data[pic].length; vert++){
				//scale for horizontal scale
				for(int scale = 0; scale < data[pic].length/2; scale++){
					for(int n = 0; n < SIZE - vert; n++){
						for(int m = 0; m < SIZE-(2+3*scale); m++){
							int white = 0;
							int grey = 0;
							int white2 = 0;
							if(n==0){
								if(m == 0){
									white = data[pic][vert][scale];
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+scale),  new Pair(n+vert,m+1+2*scale));	
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+1+2*scale),  new Pair(n+vert,m+2+3*scale));	
								} else {
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m-1),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+scale),  new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+1+2*scale),  new Pair(n+vert,m+2+3*scale));	
								}
							}else {
								if(m == 0){
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n-1,m+scale),  new Pair(n+vert,m+scale));	
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, new Pair(n-1,m+1+2*scale), 
											new Pair(n-1,m+2+3*scale), 
											new Pair(n+vert,m+1+2*scale),  
											new Pair(n+vert,m+2+3*scale));
								} else {
									white = getRectangel(data, pic, new Pair(n-1,m-1), 
											new Pair(n-1,m+scale), 
											new Pair(n+vert,m-1), 
											new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, new Pair(n-1,m+1+2*scale), 
											new Pair(n-1,m+2+3*scale), 
											new Pair(n+vert,m+1+2*scale),  
											new Pair(n+vert,m+2+3*scale));
								}
							}
							addFeature(numFeat,
									Feat.Shape.HOR3,
									new Pair(n,m), 
									new Pair(vert,scale),
									(grey - white - white2),
									face);
							numFeat++;
						}
					}
				}
				
			}
			//Vert2
			//vert for vertical scale
			for(int vert = 0; vert < data[pic].length/2; vert++){
				//scale for horizontal scale
				for(int scale = 0; scale < data[pic].length; scale++){
					for(int n = 0; n < SIZE - (1+2*vert); n++){
						for(int m = 0; m < SIZE-(scale); m++){
							int white = 0;
							int grey = 0;
							if(m==0){
								if(n == 0){
									white = data[pic][vert][scale];
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+scale),  new Pair(n+1+2*vert,m+scale));								
								} else {
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n-1,m+scale),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+scale),  new Pair(n+1+2*vert,m+scale));	
								}
							}else {
								if(n == 0){
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m-1),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n+vert,m-1), 
											new Pair(n+1+2*vert,m-1), 
											new Pair(n+vert,m+scale),  
											new Pair(n+1+2*vert,m+scale));		
								} else {
									white = getRectangel(data, pic, new Pair(n-1,m-1), 
											new Pair(n+vert,m-1), 
											new Pair(n-1,m+scale), 
											new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n+vert,m-1), 
											new Pair(n+1+2*vert,m-1), 
											new Pair(n+vert,m+scale),  
											new Pair(n+1+2*vert,m+scale));
								}
							}
							addFeature(numFeat,
									Feat.Shape.VERT2,
									new Pair(n,m), 
									new Pair(vert,scale),
									(grey - white),
									face);
							numFeat++;
						}
					}
				}
				
			}
			//Vert3
			//vert for vertical scale
			for(int vert = 0; vert < data[pic].length/2; vert++){
				//scale for horizontal scale
				for(int scale = 0; scale < data[pic].length; scale++){
					for(int n = 0; n < SIZE - (2+3*vert); n++){
						for(int m = 0; m < SIZE-(scale); m++){
							int white = 0;
							int grey = 0;
							int white2 = 0;
							if(m==0){
								if(n == 0){
									white = data[pic][vert][scale];
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+scale),  new Pair(n+1+2*vert,m+scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m+scale),  new Pair(n+2+3*vert,m+scale));
								} else {
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n-1,m+scale),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+scale),  new Pair(n+1+2*vert,m+scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m+scale),  new Pair(n+2+3*vert,m+scale));	
								}
							}else {
								if(n == 0){
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m-1),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n+vert,m-1), 
											new Pair(n+1+2*vert,m-1), 
											new Pair(n+vert,m+scale),  
											new Pair(n+1+2*vert,m+scale));
									white2 = getRectangel(data,pic, new Pair(n+1+2*vert,m-1), 
											new Pair(n+2+3*vert,m-1), 
											new Pair(n+1+2*vert,m+scale),  
											new Pair(n+2+3*vert,m+scale));	
								} else {
									white = getRectangel(data, pic, new Pair(n-1,m-1), 
											new Pair(n+vert,m-1), 
											new Pair(n-1,m+scale), 
											new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n+vert,m-1), 
											new Pair(n+1+2*vert,m-1), 
											new Pair(n+vert,m+scale),  
											new Pair(n+1+2*vert,m+scale));
									white2 = getRectangel(data,pic, new Pair(n+1+2*vert,m-1), 
											new Pair(n+2+3*vert,m-1), 
											new Pair(n+1+2*vert,m+scale),  
											new Pair(n+2+3*vert,m+scale));
								}
							}
							addFeature(numFeat,
									Feat.Shape.VERT3,
									new Pair(n,m), 
									new Pair(vert,scale),
									(grey - white - white2),
									face);
							numFeat++;
						}
					}
				}
				
			}
			//QUAD
			//vert for vertical scale
			for(int vert = 0; vert < data[pic].length/2; vert++){
				//scale for horizontal scale
				for(int scale = 0; scale < data[pic].length/2; scale++){
					for(int n = 0; n < SIZE - (1+2*vert); n++){
						for(int m = 0; m < SIZE-(1+2*scale); m++){
							int white = 0;
							int grey = 0;
							int white2 = 0;
							int grey2 = 0;
							if(n==0){
								if(m == 0){
									white = data[pic][vert][scale];
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n + vert,m+scale),  new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m+scale),  new Pair(n+1+2*vert,m+scale));
									grey2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m+scale),  new Pair(n+1+2*vert,m+1+2*scale));	
								} else {
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m-1),  new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, 0, 0, 
											new Pair(n+vert,m+scale),  new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m-1),  new Pair(n+1+2*vert,m+scale));
									grey2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+1+2*vert,m+scale),  new Pair(n+1+2*vert,m+1+2*scale));	
								}
							}else {
								if(m == 0){
									white = getRectangel(data,pic, 0, 0, 
											new Pair(n-1,m+scale),  new Pair(n+vert,m+scale));	
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data,pic, 0, 0, 
											new Pair(n+2*vert,m+scale),  new Pair(n+1+2*vert,m+scale));	
									grey2 = getRectangel(data,pic, new Pair(n+2*vert,m+scale), 
											new Pair(n+2*vert,m+1+2*scale), 
											new Pair(n+1+2*vert,m+scale),  
											new Pair(n+1+2*vert,m+1+2*scale));	
								} else {
									white = getRectangel(data, pic, new Pair(n-1,m-1), 
											new Pair(n-1,m+scale), 
											new Pair(n+vert,m-1), 
											new Pair(n+vert,m+scale));
									grey = getRectangel(data,pic, new Pair(n-1,m+scale), 
											new Pair(n-1,m+1+2*scale), 
											new Pair(n+vert,m+scale),  
											new Pair(n+vert,m+1+2*scale));
									white2 = getRectangel(data, pic, new Pair(n+2*vert,m-1), 
											new Pair(n+2*vert,m+scale), 
											new Pair(n+1+2*vert,m-1), 
											new Pair(n+1+2*vert,m+scale));
									grey2 = getRectangel(data,pic, new Pair(n+2*vert,m+scale), 
											new Pair(n+2*vert,m+1+2*scale), 
											new Pair(n+1+2*vert,m+scale),  
											new Pair(n+1+2*vert,m+1+2*scale));
								}
							}
							addFeature(numFeat,
									Feat.Shape.QUAD,
									new Pair(n,m), 
									new Pair(vert,scale),
									(grey + grey2 - white -white2),
									face);
							numFeat++;
						}
						
					}
				}
				
			}
		}
	}
	
	
	private void addFeature(int count, Feat.Shape shape, Pair points, Pair scale, int value, boolean face){
		if(result.size() == count){
			countMyFeat++;
			Feat f = new Feat(shape, 
					points, 
					scale);
			f.valuesFaces.add(new ValueFace(value, face));
			result.add(f);
		}else {
			result.get(count).valuesFaces.add(new ValueFace(value, face));
		}
	}
	private int getRectangel(int[][][] data, int numb, Pair a, Pair b, Pair c, Pair d){
		int result = 0;
		result = data[numb][d.getFirst()][d.getSecond()] + data[numb][a.getFirst()][a.getSecond()]
				- data[numb][b.getFirst()][b.getSecond()] - data[numb][c.getFirst()][c.getSecond()];
		return result;
	}
	private int getRectangel(int[][][] data, int numb, int a, int b, Pair c, Pair d){
		int result = 0;
		result = data[numb][d.getFirst()][d.getSecond()] + a
				- b - data[numb][c.getFirst()][c.getSecond()];
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(new Date());
		Features feat = new Features();
		feat.integralImages(feat.FACES, feat.numbFaces, feat.integralFaces);
		feat.integralImages(feat.NONFACES, feat.numbNonFaces, feat.integralNonFaces);
		feat.generateFeat(feat.integralFaces, true);
		feat.generateFeat(feat.integralNonFaces, false);
		List<Feat> myfeats = feat.result;
		System.out.println(new Date());
		System.out.println(myfeats.size());
	}
}
