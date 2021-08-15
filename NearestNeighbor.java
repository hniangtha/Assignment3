import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

public class NearestNeighbor {
    //creates values for arrays
    static double [][] testingVal= new double[75][4];
    static double [][] trainingVal= new double[75][4];
    static String[] trainingClassLabel = new String [75];
    static String[] testingClassLabel = new String [75];
    static String[] predictedClassLabel =new String [75];

    public static void main(String[] args) {


        // ask user to put training file path and create array
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the training file: ");
        String trainPath = input.nextLine();
        File trainingFilePath = new File(trainPath);
        Scanner trainingFile = null;
        try {
            trainingFile = new Scanner(trainingFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; Objects.requireNonNull(trainingFile).hasNextLine(); i++) { // creates array for our training values
            String str = trainingFile.nextLine();
            String[] numbers = str.split(",", 5);
            trainingClassLabel[i]=numbers[4];
            for (int j = 0; j < 4; j++) {
                trainingVal[i][j] = Double.parseDouble(numbers[j]);
            }
        }
        // ask user to put testing file path and create array

        System.out.print("Enter the name of the testing file: ");
        String testPath = input.nextLine();
        File testingFile = new File(testPath);
        Scanner fileTest = null;
        try {
            fileTest = new Scanner(testingFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; Objects.requireNonNull(fileTest).hasNextLine(); i++) { // creates array for our testing values
            String str = fileTest.nextLine();
            String[] numbers = str.split(",", 5);
            testingClassLabel[i]=numbers[4];
            for (int j = 0; j < 4; j++) {
                testingVal[i][j] = Double.parseDouble(numbers[j]);
            }
        }

        //print accuracy
        accuracy(testingClassLabel, prediction(testingVal,trainingVal,trainingClassLabel,predictedClassLabel));

        trainingFile.close();
        fileTest.close();
        input.close();


    }

    //Uses our distance formula to find the shortest distance between our value and our test values.
    public static int closestDist(double sly, double swy, double ply, double pwy, double[][] trainingVal) {

        int temp = 0;

        double Slx = trainingVal[0][0];
        double Swx = trainingVal[0][1];
        double Plx = trainingVal[0][2];
        double Pwx = trainingVal[0][3];
        double SL =Math.pow((Slx -sly),2);
        double SW =Math.pow((Swx -swy),2);
        double PL =Math.pow((Plx -ply),2);
        double PW =Math.pow((Pwx -pwy),2);

        double closestDist =Math.sqrt(SL + SW + PL +PW);

        for(int i = 0; i < 75; i++) {

            double slx = trainingVal[i][0];
            double swx = trainingVal[i][1];
            double plx = trainingVal[i][2];
            double pwx = trainingVal[i][3];
            double sl=Math.pow((slx -sly),2);
            double sw =Math.pow((swx -swy),2);
            double pl =Math.pow((plx -ply),2);
            double pw =Math.pow((pwx -pwy),2);

            double testClosestDis =Math.sqrt(sl+ sw + pl + pw);

            if (testClosestDis < closestDist) {
                temp = i;
                closestDist = testClosestDis;

            }
        }

        return temp;
    }

    //This method calculates our closes training label from the given testing values
    private static String[] prediction(double[][] testingVal, double[][] trainingVal, String [] trainingClassLabel, String [] predictedClassLabel) {

        for (int i = 0; i < 75; i++) {

            double TVal_0 = testingVal[i][0];
            double TVal_1 = testingVal[i][1];
            double TVal_2 = testingVal[i][2];
            double TVal_3 = testingVal[i][3];

            int closestPrediction = closestDist(TVal_0, TVal_1,TVal_2, TVal_3,trainingVal);

            predictedClassLabel[i] = trainingClassLabel[closestPrediction];
        }
        return predictedClassLabel;
    }

    //This method finds the accuracy of our correct values from our total values
    public static void accuracy(String[]trueLabel, String[]predictedLabel) {
        int count=0;
        double correctVal =0;
        double accuracy = 0;

        System.out.println("\nEX#: TRUE LABEL, PREDICTED LABEL");
        for (int i = 0; i < 75; i++) {
            count++;
            System.out.println(count+": "+trueLabel[i] + " " + predictedLabel[i]);
            if (trueLabel[i].equals(predictedLabel[i])) {
                correctVal++;
                accuracy = correctVal/75;

            }
        }
        DecimalFormat dFormatter = new DecimalFormat("0.################");
        String formatted = dFormatter.format(accuracy);

        System.out.print("ACCURACY: "+ formatted);

    }

}
