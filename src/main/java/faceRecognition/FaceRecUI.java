package faceRecognition;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceRecUI extends JFrame {
public static void main(String args[]) throws IOException{FaceRecUI i =new FaceRecUI();}
    


public String path;public void setPath(String p) {this.path=p;}
public String filename=null;public void setFilename(String f) {this.filename=f;}
public BufferedImage i=null;	

public JTextField textField;
public JLabel label;

public JButton upload;
public JButton grayscale;
public JButton detect;
public JButton recognize;



public JPanel motherPanel;
public JPanel buttonPanel;
public FaceRecUI () throws IOException {
//FileChooser();
LoadLibries();

upload = new JButton("Upload Image");
grayscale = new JButton("Grayscale Conversion");
detect=new JButton("Face Detect");
recognize=new JButton("Face Recognize");


JPanel textPanel=new JPanel();
textField=new JTextField("0");//textField.setBounds(100,10, 200,30);
textField.setToolTipText("Enter angle orientation");
textField.setLocation(0,0);
textField.setSize(100, 30);
textField.setHorizontalAlignment(0);
textPanel.add(textField);

label=new JLabel("...");
label.setLocation(0,0);
label.setSize(100, 30);
label.setHorizontalAlignment(0);


motherPanel = new JPanel();
motherPanel.setLayout(new BoxLayout(motherPanel, BoxLayout.Y_AXIS));



buttonPanel = new JPanel();buttonPanel.setPreferredSize(new Dimension(160, 35));
buttonPanel.add(upload);
buttonPanel.add(grayscale);
buttonPanel.add(detect);
buttonPanel.add(recognize);
//buttonPanel.add(label);

UploadButton up = new UploadButton();upload.addActionListener(up);
GrayscaleButton gr=new GrayscaleButton();grayscale.addActionListener(gr);
FaceDetectButton rt=new FaceDetectButton();detect.addActionListener(rt);
FaceRecButton rs=new FaceRecButton(); recognize.addActionListener(rs);


//motherPanel.add(textPanel);
motherPanel.add(label);
motherPanel.add(buttonPanel);

add(motherPanel);
setTitle("Face Recognition");
setSize(1024, 920);
setLocationByPlatform(true);
setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
setVisible(true);

}

public void LoadLibries() {
File lib = null;
String os = System.getProperty("os.name");
String bitness = System.getProperty("sun.arch.data.model");
if (os.toUpperCase().contains("WINDOWS")) {
if (bitness.endsWith("64")) {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x64\\" + System.mapLibraryName("opencv_java341"));} 
else {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x84\\" + System.mapLibraryName("opencv_java341"));}}
System.load(lib.getAbsolutePath());}

public void FileChooser(){
JFileChooser fileChooser = new JFileChooser();
//File dir=new File("C:\\Users\\bilal.iqbal\\Pictures\\Images\\faces");
File dir=new File("C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TestingDatasets");
fileChooser.setCurrentDirectory(dir);
int result = fileChooser.showOpenDialog(this);
if (result == JFileChooser.APPROVE_OPTION) {
File selectedFile = fileChooser.getSelectedFile();//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
String file=selectedFile.getName(); String p=selectedFile.getParent();//System.out.println("Path: "+ path+" "+ "File: "+fn);

String pa[];pa=p.split("\\\\");String absPath="";
String n=pa[pa.length-1];
for(int i=0;i<pa.length;i++){absPath=absPath+pa[i]+"\\\\";}//System.out.println("AbsPath:"+ absPath);
setPath(absPath);
setFilename(file);
}}
    


public void Disp(BufferedImage image){
JLabel label = new JLabel(new ImageIcon(image));
JFrame f = new JFrame();
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.getContentPane().add(label);
f.pack();
f.setLocation(200,100);
f.setVisible(true);}

public  BufferedImage matToBufferedImage(Mat matrix) {  
int cols = matrix.cols();  
int rows = matrix.rows();  
int elemSize = (int)matrix.elemSize();  
byte[] data = new byte[cols * rows * elemSize];  
int type;  
matrix.get(0, 0, data);  
switch (matrix.channels()) {  
case 1:  type = BufferedImage.TYPE_BYTE_GRAY;  break;  
case 3:  type = BufferedImage.TYPE_3BYTE_BGR;  byte b;  
for(int i=0; i<data.length; i=i+3) {  
b = data[i];  
data[i] = data[i+2];  
data[i+2] = b;  }  
break;  
default:  return null;  }  
BufferedImage image2 = new BufferedImage(cols, rows, type);  
image2.getRaster().setDataElements(0, 0, cols, rows, data);  
return image2;  } 

public class UploadButton implements ActionListener {
	@Override
public void actionPerformed(ActionEvent e) { 
label.setText("123");FileChooser();
String name=path+filename;System.out.println(name);
Mat src = Imgcodecs.imread(name, Imgcodecs.IMREAD_COLOR);
i=matToBufferedImage(src);
Disp(i);}}

public class GrayscaleButton implements ActionListener {
	@Override
public void actionPerformed(ActionEvent e) {
		String name=path+filename;
Mat src = Imgcodecs.imread(name, Imgcodecs.IMREAD_COLOR);
Mat gray = new Mat();
Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
BufferedImage i=null;
i=matToBufferedImage(gray);
Disp(i);}}



public class ResizeButton implements ActionListener {
	@Override
public void actionPerformed(ActionEvent e) {  
 Mat gray=Imgcodecs.imread(path+filename, Imgcodecs.IMREAD_GRAYSCALE);
//Rect rectCrop=null;int x=gray.width(); int y=gray.height();rectCrop = new Rect(40, 40, x-80, y-80);Mat resizeimage = new Mat(gray,rectCrop);
Mat resizeimage = new Mat();Size sz = new Size(125,150);Imgproc.resize(gray, resizeimage, sz );
i=matToBufferedImage(resizeimage);
Disp(i);}}





public class FaceDetectButton implements ActionListener {
	@Override
public void actionPerformed(ActionEvent e) { 
CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\bilal.iqbal\\Pictures\\classifiers\\lbpCascad_face.xml");
Mat image = Imgcodecs.imread(path+filename);
MatOfRect faceDetections = new MatOfRect();
faceDetector.detectMultiScale(image, faceDetections);
System.out.println(String.format("Detected %s faces",faceDetections.toArray().length));
for (Rect rect : faceDetections.toArray()) {
Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));}
i=matToBufferedImage(image);
Disp(i);
}}

public class FaceRecButton implements ActionListener {
	@Override
public void actionPerformed(ActionEvent e) { 
 CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\bilal.iqbal\\Pictures\\Classifiers\\lbpCascad_face.xml");
Mat image = Imgcodecs.imread(path+filename);
MatOfRect faceDetections = new MatOfRect();
faceDetector.detectMultiScale(image, faceDetections);
System.out.println(String.format("Detected %s faces",  faceDetections.toArray().length));
Rect rectCrop=null;
for (Rect rect : faceDetections.toArray()) {
Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 255, 0));
rectCrop = new Rect(rect.x+1, rect.y+1, rect.width-1, rect.height-1);}
Mat croppedFace = new Mat(image,rectCrop);
Mat gray = new Mat();
Imgproc.cvtColor(croppedFace, gray, Imgproc.COLOR_BGR2GRAY);
Mat resizeimage = new Mat();
Size sz = new Size(256,256);//*****************************************************************************************
Imgproc.resize(gray, resizeimage, sz );
Imgcodecs.imwrite("C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TestingDatasetw\\"+filename, resizeimage);
faceRec(filename);

}}


public void faceRec(String n) {
	FaceRecognition fr=new FaceRecognition();
	String trainingDatasetPath="C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TrainingDatasets\\";
	String testingDatasetPath="C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TestingDatasetw\\";	
	
	String directoryName="C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TrainingDatasets\\";		
	
	String imageToMatch=testingDatasetPath+n;
	int selectedNumberOfEigenfaces=2;
	String extension=fr.getFileExtensions(imageToMatch);		
	//fr.debugs("trying to match:"+imageToMatch+" using "+selectedNumberOfEigenfaces+" eigenfaces");

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
	String s="Image: "+ targetimage+ " Matches with: "+matchimage+ " with " + confidence+"% similarity.";
label.setText(s);

Mat tar=Imgcodecs.imread(testingDatasetPath+targetimage);MatOfKeyPoint targetKeyPoints=new MatOfKeyPoint();
Mat mat=Imgcodecs.imread(trainingDatasetPath+matchimage);MatOfKeyPoint matchKeyPoints=new MatOfKeyPoint();
Mat res = new Mat(tar.rows() * 3, tar.cols() * 3, Imgcodecs.CV_LOAD_IMAGE_COLOR);
MatOfDMatch goodMatches = new MatOfDMatch();
Features2d.drawMatches(tar,targetKeyPoints, mat,matchKeyPoints,goodMatches, res);
i=matToBufferedImage(res);
Disp(i);
}




}