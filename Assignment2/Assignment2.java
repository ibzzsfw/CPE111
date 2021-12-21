import java.util.Arrays;
import java.util.Scanner;

public class Assignment2 {

    public static void main(String[] args) {

        int maximum = 100010;
        double[/* algorithm */][/* technique */] time = new double[4][3];

        DataArray raw = new DataArray(maximum);
        DataArray sort = new DataArray(maximum);

        if (raw.load_Data_File("test.csv") > 0) {

            System.out.println("\nThis is sorting comparison program.");
            System.out.println("File read  = " + raw.counter + " records.");
            /* start input section */
            Scanner scanner = new Scanner(System.in);
            System.out.printf("Input number to sort : ");
            int n = scanner.nextInt();
            scanner.close();
            /* end of input section */
            /* start sort section */
            /* scan sort */
            sort.data = Arrays.copyOf(raw.data, n + 1);
            time[0][0] = sort.scan_sort(0, n - 1, true);
            time[0][1] = sort.scan_sort(0, n, true);
            time[0][2] = sort.scan_sort(0, n, false);
            /* selection sort */
            sort.data = Arrays.copyOf(raw.data, n + 1);
            time[1][0] = sort.selection_sort(0, n - 1, true);
            time[1][1] = sort.selection_sort(0, n, true);
            time[1][2] = sort.selection_sort(0, n, false);
            /* insertion sort */
            sort.data = Arrays.copyOf(raw.data, n + 1);
            time[2][0] = sort.insertion_sort(0, n - 1, true);
            time[2][1] = sort.insertion_sort(0, n, true);
            time[2][2] = sort.insertion_sort(0, n, false);
            /* bubble sort */
            sort.data = Arrays.copyOf(raw.data, n + 1);
            time[3][0] = sort.bubble_sort(0, n - 1, true);
            time[3][1] = sort.bubble_sort(0, n, true);
            time[3][2] = sort.bubble_sort(0, n, false);
            /* end of sort section */
            display(time);

        } else
            System.out.println("Error");
        System.out.printf("\n\nProgram written by 63070501061 Suppakorn Rakna\n\n");
    }

    public static void display(double[][] time) {

        String[/* Type of sort */] table = { "Scan Sort", "Selection Sort", "Insertion Sort", "Bubble Sort" };
        String bar = "\n+---------------+-----------------------+-----------------------+-----------------------+";
        System.out.printf("%s\n|     Sort\t|    Random data (n)\t|   Insert data (n+1)\t|     Descending (n+1)\t|%s",
                bar, bar);
        for (int i = 0; i < 4; i++)
            System.out.printf("\n|%s\t|\t%-9.4fms.\t|\t%-9.4fms.\t|\t%-9.4fms.\t|%s", table[i], time[i][0], time[i][1],
                    time[i][2], bar);
    }
}