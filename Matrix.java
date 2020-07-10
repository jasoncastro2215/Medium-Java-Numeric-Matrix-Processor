import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit");
            System.out.print("Your choice: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    double[][] arr1 = inputMatrix("first ");
                    double[][] arr2 = inputMatrix("second ");
                    addMatrix(arr1, arr2);
                    break;
                case "2":
                    double[][] arr = inputMatrix("");
                    double[][] newArr = multiplyConstant(arr);
                    showTable(newArr, "The product of matrix and constant is:");
                    break;
                case "3":
                    arr1 = inputMatrix("first ");
                    arr2 = inputMatrix("second ");
                    multiplyMatrix(arr1, arr2);
                    break;
                case "4":
                    chooseTraspose();
                    break;
                case "5":
                    arr = inputMatrix("");
                    if (arr.length == 2 && arr[0].length == 2) {
                        System.out.println("The result is:\n" + (arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0]));
                        break;
                    } else {
                        double result = determinant(arr);
                        if (result == Double.POSITIVE_INFINITY)
                            System.out.println("ERROR");
                        else
                            System.out.println("The result is:\n" + result);
                    }
                    break;
                case "6":
                    arr = inputMatrix("");
//                    arr = new double[1][1];
                    inverse(arr);
                    break;
                case "0":
                    exit = true;
            }
        }
    }

    static void inverse(double[][] arr) {
//        arr = new double[][]{{2, -1, 0},{0, 1, 2},{1, 1, 0}};
        double result = 0;
        if (arr.length == 2 && arr[0].length == 2)
            result = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
        else
            result = determinant(arr);
        if (result == 0 || result == Double.POSITIVE_INFINITY)
            System.out.println("ERROR");
        else {
            if (arr.length == 2)
                adjoin2(arr, 1 / result);
            else {
                double[][] newArr = new double[arr.length][arr.length];
                double determinant = determinant(arr);
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        newArr[i][j] = cofactor(arr, i, j);
                    }
                }
                for (int i = 0; i < newArr.length; i++) {
                    for (int j = 0; j < newArr[0].length; j++) {
                        arr[j][i] = newArr[i][j];
                    }
                }
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr[0].length; j++) {
                        newArr[i][j] = 1/determinant * arr[i][j];
                    }
                }
                showTable(newArr, "The result is:");
            }
        }
    }

    static double cofactor(double[][] arr, double I, double J) {
        int n = arr.length;
        double[][] newArr = new double[n - 1][n - 1];
        int row = 0;
        for (int i = 0; i < n; i++) {
            int column = 0;
            for (int j = 0; j < n; j++) {
                if (!(i == I || j == J)) {
                    newArr[row][column] = arr[i][j];
                    column++;
                }
            }
            if (I != i)
                row++;
        }
        if (n == 3)
            return Math.pow(-1, I + J) * (newArr[0][0] * newArr[1][1] - newArr[0][1] * newArr[1][0]);
        else
            return Math.pow(-1, I + J) * determinant(newArr);
    }

    static void adjoin2(double[][] arr, double determinant) {
        double temp;
        temp = arr[0][0];
        arr[0][0] = arr[1][1];
        arr[1][1] = temp;

        arr[1][0] *= -1;
        arr[0][1] *= -1;

        showTable(determinantTimesAdjoin(arr, determinant), "The result is:");
    }

    static double[][] determinantTimesAdjoin(double[][] arr, double determinant) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] *= determinant;
            }
        }
        return arr;
    }

    static double determinant(double[][] arr) {
//        arr = new double[][]{{1,2},{5,6}};
//        double[][] arr = new double[][]{{1,2},{3,4}};
//        double[][] arr = new double[][]{{1,2,3},{4,5,6},{7,8,9}};
//        arr = new double[][]{{1,2,3,4,5},{4,5,6,4,3},{0,0,0,1,5},{1,3,9,8,7},{5,8,4,7,11}};
        int row = arr.length;
        int column = arr[0].length;
        if (row == column) {
            double det = 0;
            for (int j = 0; j < row; j++) {
                double[][] newArr = minor(arr, j);
                det += arr[0][j] * Math.pow(-1, 0+j) * recursiveDeterminant(newArr);
            }
            return det;
        } else
            return Double.POSITIVE_INFINITY;
    }

    static double recursiveDeterminant(double[][] arr) {
        int n = arr.length;
        if (n == 2) {
            return (arr[0][0]*arr[1][1] - arr[0][1]*arr[1][0]);
        }
        double det = 0;
        for (int k = 0; k < n; k++) {
            double[][] newArr = minor(arr, k);
            det += arr[0][k] * Math.pow(-1, 0+k) * recursiveDeterminant(newArr);
        }
        return det;
    }
    
    static double[][] minor(double[][] arr, int index) {
        int n = arr.length;
        double[][] newArr = new double[n - 1][n - 1];
        int row = -1;
        for (int i = 0; i < n; i++) {
            int column = 0;
            for (int j = 0; j < n; j++) {
                if (!(i == 0 || j == index)) {
                    newArr[row][column] = arr[i][j];
                    column++;
                }
            }
            row++;
        }
        return newArr;
    }

    static void chooseTraspose() {
        System.out.println("\n1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line");
        System.out.print("Your choice: ");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                double[][] arr = inputMatrix("");
                traspose(arr, "main");
                break;
            case "2":
                arr = inputMatrix("");
                traspose(arr, "side");
                break;
            case "3":
                arr = inputMatrix("");
                traspose(arr, "vertical");
                break;
            case "4":
                arr = inputMatrix("");
                traspose(arr, "horizontal");
        }
    }



    static void traspose(double[][] arr, String type) {
        double[][] newArr = new double[arr[0].length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                switch (type) {
                    case "main":
                        newArr[j][i] = arr[i][j];
                        break;
                    case "side":
                        newArr[arr.length - 1 - j][i] = arr[arr.length - 1 - i][j];
                        break;
                    case "vertical":
                        newArr[i][arr[0].length - 1 - j] = arr[i][j];
                        break;
                    case "horizontal":
                        newArr[arr.length - 1 - i][j] = arr[i][j];
                }
            }
        }
        showTable(newArr, "The result is:");
    }

    static void multiplyMatrix(double[][] arr1, double[][] arr2) {
        if (arr1[0].length == arr2.length) {
            int row1 = arr1.length;
            int column1 = arr1[0].length;
            int column2 = arr2[0].length;
            double[][] newArr = new double[row1][column2];
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < column2; j++) {
                    double sum = 0;
                    for (int k = 0; k < column1; k++) {
                        sum += (arr1[i][k] * arr2[k][j]);
                    }
                    newArr[i][j] = sum;
                }
            }
            showTable(newArr, "The multiplication result is:");
        } else
            System.out.println("ERROR\n");
    }

    static void addMatrix(double[][] arr1, double[][] arr2) {
        double[][] newArr = new double[arr1.length][arr1[0].length];
        if ((arr1.length == arr2.length) && (arr1[0].length == arr2[0].length)) {
            for (int i = 0; i < arr1.length; i++) {
                for (int j = 0; j < arr1[0].length; j++) {
                    newArr[i][j] = arr1[i][j] + arr2[i][j];
                }
            }
            showTable(newArr, "The addition result is:");
        } else
            System.out.println("ERROR\n");
    }

    static double[][] multiplyConstant(double[][] arr) {
        System.out.println("Enter constant(single integer):");
        int multiplyBy = Integer.parseInt(scanner.nextLine());
        double[][] newArr = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                newArr[i][j] = multiplyBy * arr[i][j];
            }
        }
        return newArr;
    }

    static double[][] inputMatrix(String str) {
        System.out.printf("Enter size of %smatrix: ", str);
        String[] input = scanner.nextLine().split(" ");
        int row = Integer.parseInt(input[0]);
        int column = Integer.parseInt(input[1]);
        double[][] arr = new double[row][column];
        System.out.printf("Enter %smatrix:\n", str);
        for (int i = 0; i < row; i++) {
            arr[i] = toIntArr(scanner.nextLine().split(" "));
        }

        return arr;
    }

    static void showTable(double[][] Arr, String strMessage) {
        System.out.println(strMessage);
        for (double[] doubles : Arr) {
            for (int j = 0; j < Arr[0].length; j++) {
                if (j == Arr[0].length - 1) {
                    System.out.println(doubles[j]);
                    break;
                }
                System.out.print(doubles[j] + " ");
            }
        }
        System.out.println();
    }

    static double[] toIntArr(String[] arr) {
        double[] intArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Double.parseDouble(arr[i]);
        }
        return intArr;
    }
}