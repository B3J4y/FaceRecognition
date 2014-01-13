package de.violaJones.Training;

import java.io.IOException;
import java.util.List;

import de.violaJones.Features.*;

public class AdaBoost {
	public List<Feat> features;
	
	public AdaBoost() throws IOException{
		Features feat = new Features();
		feat.integralImages(feat.FACES, feat.numbFaces, feat.integralFaces);
		feat.integralImages(feat.NONFACES, feat.numbNonFaces, feat.integralNonFaces);
		feat.generateFeat(feat.integralFaces, true);
		features = feat.result;
	}
	
	
	public void training(int T){
		//initialize weights
		
		//for from 1 .. T
			//normalize weights
			//select best weak classifier
			//define h_t(x)
			//update weights
	}
}
