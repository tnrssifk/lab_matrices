import java.util.Scanner;

public class MatrixConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("матрицы комплексных чисел");

        ComplexNumber[][] d1 = {
                {new ComplexNumber(1, 1), new ComplexNumber(2, 0)},
                {new ComplexNumber(0, 1), new ComplexNumber(1, -1)}
        };
        ComplexMatrix m1 = new ComplexMatrix(d1);


        ComplexNumber[][] d2 = {
                {new ComplexNumber(1, 0), new ComplexNumber(0, 1)},
                {new ComplexNumber(1, 1), new ComplexNumber(2, 2)}
        };
        ComplexMatrix m2 = new ComplexMatrix(d2);

        System.out.println("матрица A:");
        System.out.println(m1);

        System.out.println("матрица B:");
        System.out.println(m2);

        System.out.println("выберите операцию:");
        System.out.println("1. сложить A + B");
        System.out.println("2. умножить A * B");
        System.out.println("3. транспонировать A");
        System.out.println("4. определитель A");
        System.out.println("5. разделить A / B");

        System.out.print("ваш выбор: ");
        int choice = scanner.nextInt();


        try {
            switch (choice) {
                case 1:
                    System.out.println("результат сложения:");
                    System.out.println(m1.add(m2));
                    break;
                case 2:
                    System.out.println("результат умножения:");
                    System.out.println(m1.multiply(m2));
                    break;
                case 3:
                    System.out.println("транспонированная A:");
                    System.out.println(m1.transpose());
                    break;
                case 4:
                    System.out.println("определитель A: " + m1.determinant());
                    break;
                case 5:
                    System.out.println("результат деления A / B:");
                    System.out.println(m1.divide(m2));
                    break;
                default:
                    System.out.println("неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("произошла ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}