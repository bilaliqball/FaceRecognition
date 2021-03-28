package objectDetection;
import java.io.File;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
public class ObjectDetection {
public String path=null;
public String filename=null;
public void setPath(String p) {this.path=p;}
public void setFilename(String f) {this.filename=f;}

ObjectDetection(){
LoadLibraries();
setPath("C:\\Users\\bilal.iqbal\\eclipse-workspace\\FaceRecognition\\TestingDatasets\\");}

public void LoadLibraries() {
File lib = null;
String os = System.getProperty("os.name");
String bitness = System.getProperty("sun.arch.data.model");
if (os.toUpperCase().contains("WINDOWS")) {
if (bitness.endsWith("64")) {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x64\\" + System.mapLibraryName("opencv_java341"));} 
else {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x84\\" + System.mapLibraryName("opencv_java341"));}}
System.load(lib.getAbsolutePath());}


public void lineDetect(String[] args) {
//String file=path+filename;
String file="C:\\Users\\bilal.iqbal\\Pictures\\Images\\images\\img1.jpg";
Mat dst = new Mat(), sht = new Mat(), pht=new Mat();
Mat src = Imgcodecs.imread(file, Imgcodecs.IMREAD_GRAYSCALE);
if( src.empty() ) {System.out.println("Error opening image!");System.exit(-1);}
Imgproc.Canny(src, dst, 50, 200, 3, false);// Edge detection
Imgproc.cvtColor(dst, sht, Imgproc.COLOR_GRAY2BGR);
pht = sht.clone();// Copy edges to the images that will display the results in BGR
Mat lines = new Mat(); 
Imgproc.HoughLines(dst, lines, 1, Math.PI/180, 150); // Standard Hough Line Transform
for (int x = 0; x < lines.rows(); x++) {
double rho = lines.get(x, 0)[0];
double theta = lines.get(x, 0)[1];
double a = Math.cos(theta), b = Math.sin(theta);
double x0 = a*rho, y0 = b*rho;
Point pt1 = new Point(Math.round(x0 + 1000*(-b)), Math.round(y0 + 1000*(a)));
Point pt2 = new Point(Math.round(x0 - 1000*(-b)), Math.round(y0 - 1000*(a)));
Imgproc.line(sht, pt1, pt2, new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);}

Mat linesP = new Mat(); 
Imgproc.HoughLinesP(dst, linesP, 1, Math.PI/180, 50, 50, 10); // Probabilistic Line Transform
for (int x = 0; x < linesP.rows(); x++) {
double[] l = linesP.get(x, 0);
Imgproc.line(pht, new Point(l[0], l[1]), new Point(l[2], l[3]), new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);}

//HighGui.imshow("Detected Lines- Standard Hough Line Transform", sht);
HighGui.imshow("Detected Lines- Probabilistic Line Transform", pht);
HighGui.waitKey();
System.exit(0);}

public void circleDetect(String[] args) {
//String file=path+filename;
String file="C:\\Users\\bilal.iqbal\\Pictures\\Images\\images\\img1.jpg";
Mat src = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR);
if( src.empty() ) {System.out.println("Error opening image!");System.exit(-1);}
Mat gray = new Mat();
Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
Imgproc.medianBlur(gray, gray, 5);
Mat circles = new Mat();
Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0,(double)gray.rows()/16, 100.0, 30.0, 1, 100); 
for (int x = 0; x < circles.cols(); x++) {
double[] c = circles.get(0, x);
Point center = new Point(Math.round(c[0]), Math.round(c[1]));// circle center
Imgproc.circle(src, center, 1, new Scalar(0,100,100), 3, 8, 0 );
int radius = (int) Math.round(c[2]);// circle radius
Imgproc.circle(src, center, radius, new Scalar(255,0,255), 3, 8, 0 );}
HighGui.imshow("detected circles", src);
HighGui.waitKey();
System.exit(0);}

public void edgeDetect(String[] args) {
String file = path+filename;
//String file="C:\\Users\\bilal.iqbal\\Pictures\\Images\\images\\img1.jpg";
Mat rgbImage = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR);
Mat imageGray = new Mat();
Mat imageCanny = new Mat();
Imgproc.cvtColor(rgbImage, imageGray, Imgproc.COLOR_BGR2GRAY);
Imgproc.Canny(imageGray, imageCanny, 10, 100, 3, true);
Imgcodecs.imwrite(path+"EdgeDetection\\"+filename, imageCanny);
HighGui.imshow("Actual Image",rgbImage );
HighGui.imshow("GrayScale Image",imageGray );
HighGui.imshow("Edge Image", imageCanny);
HighGui.waitKey();
System.exit(0);}


public void edgeDetection(String[] args) {
String file = path+filename;
//String file="C:\\Users\\bilal.iqbal\\Pictures\\Images\\images\\img1.jpg";
Mat rgbImage = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR);
Mat imageGray = new Mat();
Mat imageCanny = new Mat();
Mat imageRotate=new Mat();
Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(rgbImage.cols()/2, rgbImage.rows()/2),-35, 1);
Imgproc.cvtColor(rgbImage, imageGray, Imgproc.COLOR_BGR2GRAY);
Imgproc.Canny(imageGray, imageCanny, 10, 100, 3, true);

HighGui.imshow("Actual Image",rgbImage );
HighGui.imshow("GrayScale Image",imageGray );
HighGui.imshow("Edge Image", imageCanny);

Imgproc.warpAffine(imageCanny, imageRotate,rotationMatrix, new Size(rgbImage.cols()+50, rgbImage.cols()+120));
HighGui.imshow("Rotation", imageRotate);
HighGui.waitKey();
System.exit(0);}


public void faceDetect(String [] args) {
CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\bilal.iqbal\\Pictures\\classifiers\\lbpCascad_face.xml");
Mat image = Imgcodecs.imread("C:\\Users\\bilal.iqbal\\Pictures\\Images\\faces\\face2.jpg");
MatOfRect faceDetections = new MatOfRect();
faceDetector.detectMultiScale(image, faceDetections);
System.out.println(String.format("Detected %s faces",faceDetections.toArray().length));
for (Rect rect : faceDetections.toArray()) {
Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));}
//Imgcodecs.imwrite(path+"detect"+filename, image);
HighGui.imshow("Face Detected", image);
HighGui.waitKey();
System.exit(0);}


public  void faceDetection() {
CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\bilal.iqbal\\Pictures\\Classifiers\\lbpCascad_face.xml");
Mat image = Imgcodecs.imread("C:\\Users\\bilal.iqbal\\Pictures\\Images\\faces\\face2.jpg");
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
//


Mat resizeimage = new Mat();
Size sz = new Size(256,256);
Imgproc.resize(gray, resizeimage, sz );
Imgcodecs.imwrite("C:\\Users\\bilal.iqbal\\Pictures\\Images\\persons\\detected\\", resizeimage);

//HighGui.imshow("Face Detected",resizeimage);
//HighGui.waitKey();
//System.exit(0);
}

public static void main(String args[]) {
	//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	ObjectDetection o=new ObjectDetection();
o.faceDetect(args);
	}




}


