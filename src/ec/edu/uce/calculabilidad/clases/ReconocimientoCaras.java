package ec.edu.uce.calculabilidad.clases;

/**
 * PROYECTO: Face x
 *
 * Clase de tipo Jframe que será la pantalla principal de neustro sistema
 * FECHA:15-02-2018
 *
 *
 * @author: 1312960444 Michael Ponce Cevallos, 1723465066 Jairo Mena 1726581141
 * Marlon Oña
 * @version 1.0.0
 */
//LIBRERIAS OPENCV
import static com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_core.CV_32SC1;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import com.googlecode.javacv.cpp.opencv_objdetect;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

//LIBRERIAS JAVA
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

//En esta clase es donde esta el algoritmo que permite reconocer los rostros
public class ReconocimientoCaras {

    //cargo los xml necesario y la base de datos local 
    private static String faceDataFolder = System.getProperty("user.dir") + "\\facerecognizer\\data\\";
    public static String imageDataFolder = faceDataFolder + "images\\";
    private static final String CASCADE_FILE = "haarcascade_frontalface_alt.xml";
    private static final String BinaryFile = faceDataFolder + "frBinary.dat";
    public static final String personNameMappingFileName = faceDataFolder + "personNumberMap.properties";

    final CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(CASCADE_FILE));
    private Properties dataMap = new Properties();
    private static ReconocimientoCaras instance = new ReconocimientoCaras();
//reconoce para 15 ftoos pero se peude ampliar
    public static final int NUM_IMAGES_PER_PERSON = 15;
    double binaryTreshold = 100;
    int highConfidenceLevel = 70;

    FaceRecognizer ptr_binary = null;
    private FaceRecognizer fr_binary = null;

    private ReconocimientoCaras() {
        createModels();
        loadTrainingData();
    }

    public static ReconocimientoCaras getInstance() {
        return instance;
    }

    private void createModels() {
        ptr_binary = createLBPHFaceRecognizer(1, 8, 8, 8, binaryTreshold);
        fr_binary = ptr_binary;
    }

    protected CvSeq detectFace(IplImage originalImage) {
        CvSeq faces = null;
        Loader.load(opencv_objdetect.class);
        try {
            IplImage grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
            cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
            CvMemStorage storage = CvMemStorage.create();
            faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 1, 0);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return faces;
    }

    public String identifyFace(IplImage image) {
        String personName = "";
        Set keys = dataMap.keySet();

        if (keys.size() > 0) {
            int[] ids = new int[1];
            double[] distance = new double[1];
            int result = -1;

            fr_binary.predict(image, ids, distance);
            result = ids[0];

            if (result > -1 && distance[0] < highConfidenceLevel) {
                personName = (String) dataMap.get("" + result);
            }
        }

        return personName;
    }
//metodo q le permite aprender la nueva cara que ingrese el usaurio

    public boolean learnNewFace(String personName, IplImage[] images) throws Exception {
        int memberCounter = dataMap.size();
        if (dataMap.containsValue(personName)) {
            Set keys = dataMap.keySet();
            Iterator ite = keys.iterator();
            while (ite.hasNext()) {
                String personKeyForTraining = (String) ite.next();
                String personNameForTraining = (String) dataMap.getProperty(personKeyForTraining);
                if (personNameForTraining.equals(personName)) {
                    memberCounter = Integer.parseInt(personKeyForTraining);
                }
            }
        }
        dataMap.put("" + memberCounter, personName);
        storeTrainingImages(personName, images);
        retrainAll();

        return true;
    }

    public IplImage preprocessImage(IplImage image, CvRect r) {
        IplImage gray = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        IplImage roi = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        CvRect r1 = new CvRect(r.x() - 10, r.y() - 10, r.width() + 10, r.height() + 10);
        cvCvtColor(image, gray, CV_BGR2GRAY);
        cvSetImageROI(gray, r1);
        cvResize(gray, roi, CV_INTER_LINEAR);
        cvEqualizeHist(roi, roi);
        return roi;
    }

    private void retrainAll() throws Exception {
        Set keys = dataMap.keySet();
        System.out.println("+++" + keys.size());
        if (keys.size() > 0) {

            MatVector trainImages = new MatVector(keys.size() * NUM_IMAGES_PER_PERSON);
            CvMat trainLabels = CvMat.create(keys.size() * NUM_IMAGES_PER_PERSON, 1, CV_32SC1);
            Iterator ite = keys.iterator();
            int count = 0;
            System.err.println("Cargando imagenes para entrenamiento ...");
            System.out.println("");
            while (ite.hasNext()) {

                String personKeyForTraining = (String) ite.next();
                String personNameForTraining = (String) dataMap.getProperty(personKeyForTraining);
                IplImage[] imagesForTraining = readImages(personNameForTraining);

                for (int i = 0; i < imagesForTraining.length; i++) {

                    trainLabels.put(count, 0, Double.parseDouble(personKeyForTraining));
                    IplImage grayImage = IplImage.create(imagesForTraining[i].width(), imagesForTraining[i].height(), IPL_DEPTH_8U, 1);
                    cvCvtColor(imagesForTraining[i], grayImage, CV_BGR2GRAY);
                    trainImages.put(count, grayImage);
                    count++;

                }
            }
            System.err.println("Hecho.");
            System.err.print("Realizando entrenamiento ...");
            fr_binary.train(trainImages, trainLabels);
            System.err.println("Hecho.");
            storeTrainingData();
        }

    }

    private void loadTrainingData() {
        try {
            File personNameMapFile = new File(personNameMappingFileName);
            if (personNameMapFile.exists()) {
                FileInputStream fis = new FileInputStream(personNameMappingFileName);
                dataMap.load(fis);
                fis.close();
            }

            File binaryDataFile = new File(BinaryFile);
            binaryDataFile.createNewFile();
            fr_binary.load(BinaryFile);
            System.err.println("hecho");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void storeTrainingData() throws Exception {
        System.err.print("Almacenando modelos ...");

        File binaryDataFile = new File(BinaryFile);
        if (binaryDataFile.exists()) {
            binaryDataFile.delete();
        }
        fr_binary.save(BinaryFile);

        File personNameMapFile = new File(personNameMappingFileName);
        if (personNameMapFile.exists()) {
            personNameMapFile.delete();
        }
        FileOutputStream fos = new FileOutputStream(personNameMapFile, false);
        dataMap.store(fos, "");
        fos.close();

        System.err.println("hecho.");
    }

    public void storeTrainingImages(String personName, IplImage[] images) {
        for (int i = 0; i < images.length; i++) {
            String imageFileName = imageDataFolder + "training\\" + personName + "_" + i + ".bmp";
            File imgFile = new File(imageFileName);
            if (imgFile.exists()) {
                imgFile.delete();
            }
            cvSaveImage(imageFileName, images[i]);
        }
    }

    private IplImage[] readImages(String personName) {
        File imgFolder = new File(imageDataFolder);
        IplImage[] images = null;
        if (imgFolder.isDirectory() && imgFolder.exists()) {
            images = new IplImage[NUM_IMAGES_PER_PERSON];
            for (int i = 0; i < NUM_IMAGES_PER_PERSON; i++) {
                String imageFileName = imageDataFolder + "training\\" + personName + "_" + i + ".bmp";
                IplImage img = cvLoadImage(imageFileName);
                images[i] = img;
            }

        }
        return images;
    }
}
