package imageTransformation;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImageTransformation {
	
public String path=null;
public String filename=null;
public void setPath(String p) {this.path=p;}
public void setFilename(String f) {this.filename=f;}
	
public static void main(String args[]) {
	ImageTransformation it=new ImageTransformation();
	it.GrayScale(args);
}
ImageTransformation(){
setPath("C:\\Users\\bilal.iqbal\\Pictures\\Images\\thumbprint\\");
setFilename("thumbprints (1).png");
loadLibraries();
}

public void loadLibraries() {
File lib = null;
String os = System.getProperty("os.name");
String bitness = System.getProperty("sun.arch.data.model");
if (os.toUpperCase().contains("WINDOWS")) {
if (bitness.endsWith("64")) {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x64\\" + System.mapLibraryName("opencv_java341"));} 
else {lib = new File("C:\\Users\\bilal.iqbal\\Pictures\\Libraries\\x84\\" + System.mapLibraryName("opencv_java341"));}}
System.load(lib.getAbsolutePath());}

public void Binarized() {
Mat input = Imgcodecs.imread(path+filename, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
Mat binarized=new Mat();
Imgproc.threshold(input, binarized, 100, 255,Imgproc.THRESH_TOZERO);
HighGui.imshow("Binary Image",binarized);
HighGui.waitKey();
System.exit(0);}
	
public void GrayScale(String args[]) {
Mat src = Imgcodecs.imread(path+filename, Imgcodecs.IMREAD_COLOR);
Mat gray = new Mat();
Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
Imgcodecs.imwrite("C:\\Users\\bilal.iqbal\\Pictures\\Images\\faces\\face2a.jpg", gray);
HighGui.imshow("Grayscale Conversion", gray);
HighGui.waitKey();
System.exit(0);}



public void EdgeDetect(String args[]) {
Mat Canny=new Mat();
Mat src = Imgcodecs.imread("C:\\Users\\bilal.iqbal\\Pictures\\Images\\thumbprint\\thumbprints (1).png", Imgcodecs.IMREAD_GRAYSCALE);// Load an image
Imgproc.Canny(src, Canny, 50, 200, 3, false);// Edge detection
Imgproc.cvtColor(Canny, Canny, Imgproc.COLOR_GRAY2BGR);// Copy edges to the images that will display the results in BGR
//Imgcodecs.imwrite("C:\\Users\\bilal.iqbal\\Pictures\\Images\\faces\\face2bb.jpg", Canny);
HighGui.imshow("Edge Detection", Canny);
HighGui.waitKey();
System.exit(0);}

public  void Rotate(String args[]) {
Mat src = Imgcodecs.imread(path+filename);
Mat dst = new Mat();
Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(src.cols()/2, src.rows()/2),10, 1);
Imgproc.warpAffine(src, dst,rotationMatrix, new Size(src.cols(), src.cols()));
HighGui.imshow("Rotation", dst);
HighGui.waitKey();
System.exit(0);}
	

public void Crop(String [] args) {
Mat image = Imgcodecs.imread(path+filename);
Rect rectCrop=null;
rectCrop = new Rect(200, 200, 128, 128);
Mat image_roi = new Mat(image,rectCrop);
HighGui.imshow("Cropped Image", image_roi);
HighGui.waitKey();
System.exit(0);
}


public void Resize(String [] args) {
Mat image = Imgcodecs.imread(path+filename);
Mat resizeimage = new Mat();
Size sz = new Size(256,512);
Imgproc.resize( image, resizeimage, sz );
HighGui.imshow("Face Detect", resizeimage);
HighGui.waitKey();
System.exit(0);
}


public void ImageCopy(String [] args) {
Mat image = Imgcodecs.imread(path+filename);
Mat copy = image.clone();
Size sizeA = image.size();
int r=0,g=0,b=0,rgb=0;
final int rows=(int) sizeA.height;
final int cols=(int) sizeA.width;
int[][] res=new int[rows][cols];
Color c=null;
for (int i = 0; i < sizeA.height; i++)
for (int j = 0; j < sizeA.width; j++) {
double [] data = image.get(i, j);
r =(int) data[0];
g = (int)data[1];
b = (int)data[2];
c=new Color(r,g,b);
rgb=c.getRGB();
res[i][j]=rgb;
}
HighGui.imshow("Image",copy);
HighGui.waitKey();
System.exit(0);}


public static BufferedImage matToBufferedImage(Mat matrix) {  
int cols = matrix.cols();  
int rows = matrix.rows();  
int elemSize = (int)matrix.elemSize();  
byte[] data = new byte[cols * rows * elemSize];  
int type;  
matrix.get(0, 0, data);  
switch (matrix.channels()) {  
case 1:  
type = BufferedImage.TYPE_BYTE_GRAY;  
break;  
case 3:  
    type = BufferedImage.TYPE_3BYTE_BGR;  
    // bgr to rgb  
        byte b;  
        for(int i=0; i<data.length; i=i+3) {  
            b = data[i];  
            data[i] = data[i+2];  
            data[i+2] = b;  
        }  
        break;  
    default:  
        return null;  
    }  
    BufferedImage image2 = new BufferedImage(cols, rows, type);  
    image2.getRaster().setDataElements(0, 0, cols, rows, data);  
    return image2;  
} 





}
