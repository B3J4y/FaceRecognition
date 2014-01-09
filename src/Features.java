import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class Features {
	public int numbFaces = 0;
	public int numbNonFaces = 0;
	public final String FACES = "./res/png/faces";
	public final String NONFACES = "./res/png/nonfaces";
	public int[][][] integralFaces;
	public int[][][] integralNonFaces;
	
	public Features(){
		numbFaces = new File(FACES).listFiles().length;
		numbNonFaces = new File(NONFACES).listFiles().length;
		integralFaces = new int[numbFaces][24][24];
		integralNonFaces = new int[numbNonFaces][24][24];
	}
	
	public void integralImages(String path, int max, int[][][] data) throws IOException{
		for(int i = 0; i < max; i++){
			
			File[] filesList = new File(path).listFiles();
			Arrays.sort(filesList);
			BufferedImage buffIm = ImageIO.read(filesList[i]);
			//System.out.println(filesList[i].getName());
			String str = "";
			String integ = "";
			for(int n = 0; n < 24; n++){
				str = "";
				integ = "";
				for(int m = 0; m < 24; m++){
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
				//System.out.println(integ);
			}
		}
	}
	
	public void generateFeat(int[][][] data){
		for(int pic = 0; pic < data.length; pic++){
			//HOOR2
			for(int scale = 0; scale < data[pic].length/2; scale++){
				for(int n = 0; n < 24; n++){
					for(int m = 0; m < 24-scale; m++){
						int white = 0;
						int grey = 0;
						if(n==0){
							if(m == 0){
								white = data[pic][0][0 + scale];
								grey = getRectangel(data,pic, 0, 0, 
										new Pair(n,m+scale),  new Pair(n,m+1+2*scale));								
							} else {
								white = getRectangel(data,pic, 0, 0, 
										new Pair(n,m-1),  new Pair(n,m+scale));
								grey = getRectangel(data,pic, 0, 0, 
										new Pair(n,m-1+scale),  new Pair(n,m+1+2*scale));	
							}
						}else {
							white = getRectangel(data, pic, 0, 
									0, 
									new Pair(n,m-1), 
									new Pair(n,m+scale));
							grey = getRectangel(data,pic, 0, 
									0, 
									new Pair(n,m-1+scale),  new Pair(n,m+1+2*scale));
							
						}
						System.out.println(grey - white);
					}
				}
			}
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
		Features feat = new Features();
		File f = new File("./res");
		feat.integralImages(feat.FACES, feat.numbFaces, feat.integralFaces);
		feat.integralImages(feat.NONFACES, feat.numbNonFaces, feat.integralNonFaces);
		feat.generateFeat(feat.integralFaces);
		System.out.println(feat.numbFaces);
	}
}
