package faceRecognition;

import java.io.File;

public class FaceRecognitionDriver {

	
	public FaceRecognitionDriver() {
//		File lib = null;
//		String os = System.getProperty("os.name");
//		String bitness = System.getProperty("sun.arch.data.model");
//		if (os.toUpperCase().contains("WINDOWS")) {
//		if (bitness.endsWith("64")) {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x64\\" + System.mapLibraryName("opencv_java2411"));} 
//		else {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x84\\" + System.mapLibraryName("opencv_java341"));}}
//		System.load(lib.getAbsolutePath());
	}
	
	public void faceRec() {
		FaceRecognitionDriver i=new FaceRecognitionDriver();
		FaceRecognition fr=new FaceRecognition();
		String trainingDatasetPath="C:\\Users\\bilal.iqbal\\eclipse-workspace\\ImageProcessings\\TrainingDataset\\";
		String testingDatasetPath="C:\\Users\\bilal.iqbal\\eclipse-workspace\\ImageProcessings\\TestingDataset\\";	
		
		String directoryName="C:\\Users\\bilal.iqbal\\eclipse-workspace\\ImageProcessings\\TrainingDataset\\";		
		String imageToMatch=testingDatasetPath+"jack2.png";
		
		
//		String directoryName="C:\\Users\\bilal.iqbal\\Downloads\\fingerprint_bitmaps\\New folder\\";
//		String imageToMatch="C:\\Users\\bilal.iqbal\\Downloads\\fingerprint_bitmaps\\Grayscale\\thumb (5).jpg";
		
		
		
		int selectedNumberOfEigenfaces=2;
		String extension=fr.getFileExtensions(imageToMatch);		
		//fr.debugs("trying to match:"+imageToMatch+" using "+selectedNumberOfEigenfaces+" eigenfaces");
		long startTime=System.currentTimeMillis();
		fr.checkCache(directoryName,extension,selectedNumberOfEigenfaces);
		fr.reconstructFaces(selectedNumberOfEigenfaces);
		
		MatchResult result=fr.findMatchResult(imageToMatch,selectedNumberOfEigenfaces);

		String target=imageToMatch;
		String match=result.getMatchFileName();
		double confidence=(1-result.getMatchDistance())*100;
	
		
		String[] targetImage;
		String[] matchImage;
		
		targetImage=target.split("\\\\");matchImage=match.split("\\\\");
		String targetimage=targetImage[targetImage.length-1];
		String matchimage=matchImage[matchImage.length-1];
		System.out.println("Image: "+ targetimage+ " Matches with: "+matchimage+ " with " + confidence+"% similarity.");
	}
	public static void main(String[] args) {		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		long startTime=System.currentTimeMillis();
		FaceRecognitionDriver i=new FaceRecognitionDriver();
		i.faceRec();
		long endTime=System.currentTimeMillis();
		System.out.println("\ntotal time taken="+(endTime-startTime)+" millisecs");
	}
}
