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
		feat.generateFeat(feat.integralNonFaces, false);
		features = feat.result;
		
		for(Feat f : features){
			f.generateTreshold();
		}
	}

	
	public void training(int T){
		//initialize weights
		int allPics = features.get(0).valuesFaces.size();
		for(Feat f : features){
			for(ValueFace vf : f.valuesFaces){
				vf.weight = 1/allPics;
			}
			
		}
		//for from 1 .. T
		for(int i = 0; i < T; i++){
			//normalize weights
			
			//select best weak classifier
			//define h_t(x)
			//update weights
		}
	}
	public static void main(String[] args) throws IOException {
		AdaBoost a = new AdaBoost();
	}
}
