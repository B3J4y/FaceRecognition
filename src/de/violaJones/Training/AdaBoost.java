package de.violaJones.Training;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.violaJones.Features.*;
import de.violaJones.Features.Feat.Shape;

public class AdaBoost {
	public List<Feat> features;
	
	public AdaBoost() throws IOException{
		Features feat = new Features(100, 100);
		feat.integralImages(feat.FACES, feat.numbFaces, feat.integralFaces);
		System.out.println(new Date() + "--- Integralfaces. Done");
		feat.integralImages(feat.NONFACES, feat.numbNonFaces, feat.integralNonFaces);
		System.out.println(new Date() + "--- Integralnonfaces. Done");
		feat.generateFeat(feat.integralFaces, true);
		System.out.println(new Date() + "--- done. Generate Feat. (Faces)");
		feat.generateFeat(feat.integralNonFaces, false);
		System.out.println(new Date() + "--- done. Generate Feat. (Nonfaces)");
		System.out.println(new Date());
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
	
	public double classifierData(List<Feat> classifiers) throws IOException{
		Features f = new Features(0, 0);
		f.integralImages(f.FACES, f.numbFaces, f.integralFaces);
		System.out.println(new Date() + "--- Integralfaces. Done");
		f.integralImages(f.NONFACES, f.numbNonFaces, f.integralNonFaces);
		System.out.println(new Date() + "--- Integralnonfaces. Done");
		for(Feat cl : classifiers){
			//Point endPoint = new Point(cl.position.getFirst() , cl.size.getFirst());
			//get Rectangle data
			switch(cl.shape){
			case HOR2:
							
				break;
			case HOR3:
				
				break;
			case VERT2:
				
				break;
			case VERT3:
				
				break;
			case QUAD:
				
				break;
			}
		}
		return 0.0;
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
