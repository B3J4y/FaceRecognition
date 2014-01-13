package de.violaJones.Training;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

	
	public List<Feat> training(int T){
		//initialize weights
		int allPics = features.get(0).valuesFaces.size();
		List<Feat> weakClassifiers = new ArrayList<Feat>();
		for(Feat f : features){
			for(ValueFace vf : f.valuesFaces){
				vf.weight = (double)1/allPics;
			}
			
		}
		//for from 1 .. T
		for(int i = 0; i < T; i++){
			System.out.println(new Date() + " " + i);
			//normalize weights
			for(Feat f : features){
				f.error = 0;
				double allweights = f.getAllweight();
				for(ValueFace vf : f.valuesFaces){
					vf.weight = vf.weight/allweights;
					int y;
					if (vf.face){
						y = 1;
					} else {
						y = 0;
					}
					f.error += (double)vf.weight *Math.abs(f.h(vf.value) - y);
				}
			}
			//select best weak classifier
			Collections.sort(features);
			weakClassifiers.add(features.get(0));
			features.remove(0);
			//define h_t(x)
			//update weights
			for(Feat f : features){
				double beta = (double)(f.error/(1-f.error));
				for(ValueFace vf : f.valuesFaces){
					int y;
					if (vf.face){
						y = 1;
					} else {
						y = 0;
					}
					vf.weight = (double)(vf.weight*Math.pow(beta,(1-Math.abs(f.h(vf.value) - y))));
				}
				
			}
		}
		return weakClassifiers;
	}
	public static void main(String[] args) throws IOException {
		System.out.println(new Date());
		AdaBoost a = new AdaBoost();
		List<Feat> wC= a.training(100);
		System.out.println("---------------------------");
		System.out.println(new Date());
		System.out.println(wC.size());
	}
}
