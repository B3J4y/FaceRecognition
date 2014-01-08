import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Features {
	int numbFaces = 0;
	int numbNonFaces = 0;
	final String FACES = "./res/png/faces";
	final String NONFACES = "./res/png/nonfaces";
	int[][][] integralFaces;
	
	public Features(){
		numbFaces = new File(FACES).listFiles().length;
		numbNonFaces = new File(NONFACES).listFiles().length;
		integralFaces = new int[numbFaces][24][24];
	}
	
	public void integralImages() throws IOException{
		System.out.println(new File(FACES).listFiles()[0].getName());
		BufferedImage buffIm = ImageIO.read(new File(FACES).listFiles()[0]);
		String str = "";
		String integ = "";
		for(int n = 0; n < 24; n++){
			str = "";
			integ = "";
			for(int m = 0; m < 24; m++){
				if(m == 0){
					if(n == 0){
						integralFaces[0][n][m] = (buffIm.getRGB(n, m) & 0xFF);
					} else {
						integralFaces[0][n][m] = (buffIm.getRGB(n, m) & 0xFF) 
								+integralFaces[0][n - 1][m];
					}
				} else {
					if(n == 0){
						integralFaces[0][n][m] = integralFaces[0][n][m - 1] + (buffIm.getRGB(n, m) & 0xFF);
					} else {
						integralFaces[0][n][m] = integralFaces[0][n][m - 1] 
								+ (buffIm.getRGB(n, m) & 0xFF) 
								+ integralFaces[0][n][m - 1] 
								- integralFaces[0][n -1][m - 1];
					}
				}
				integ += integralFaces[0][n][m] + " ";
				str += (buffIm.getRGB(n, m) & 0xFF) + " ";
			}
			System.out.println(str);
			System.out.println(integ);
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		Features feat = new Features();
		File f = new File("./res");
		feat.integralImages();
		System.out.println(feat.numbFaces);
	}
}
