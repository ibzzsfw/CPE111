import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataArray {

    CSVnode[] data;
    int counter;

    public DataArray(int maximum) {

        data = new CSVnode[maximum];
        counter = 0;

    }

    public void add_Data(int field1, long field2, String field3, String field4) {

        CSVnode number = new CSVnode(field1, field2, field3, field4);
        data[counter++] = number;
    }

    public void swap(int i, int j) {

        CSVnode number = new CSVnode();
        number = data[i];
        data[i] = data[j];
        data[j] = number;
    }

    public void render(int start, int stop) {

        for (int i = start; i <= stop; i++)
            data[i].node_render();
        System.out.println();
    }

    public int load_Data_File(String filename) {

        int counter = 0;
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int field1;
            long field2;
            String field3;
            String field4;
            scanner.useDelimiter(",|\n");
            while (scanner.hasNextInt()) {
                field1 = scanner.nextInt();
                field2 = scanner.nextLong();
                field3 = scanner.next();
                field4 = scanner.next();
                add_Data(field1, field2, field3, field4);
                counter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {

            System.out.println("Error, can not open " + filename);
            return 0;

        }
        return counter;
    }

    public double scan_sort(int start, int stop, boolean ascending) {

        int i = 0;
        int j = 0;

        long start_time = System.nanoTime();

        for (i = start; i <= stop - 1; i++)
            for (j = i + 1; j <= stop; j++)
                if ((data[j].field2 < data[i].field2) == ascending)
                    swap(i, j);

        long stop_time = System.nanoTime();
        return ((stop_time - start_time) / 1E6);
    }

    public double selection_sort(int start, int stop, boolean ascending) {

        int i = 0;
        int j = 0;
        int minimum = 0;

        long start_time = System.nanoTime();

        for (i = start; i < stop; i++) {
            minimum = i;
            for (j = i + 1; j <= stop; j++) {
                if ((data[j].field2 < data[minimum].field2) == ascending)
                    minimum = j;
            }
            swap(minimum, i);
        }

        long stop_time = System.nanoTime();
        return ((stop_time - start_time) / 1E6);
    }

    public double insertion_sort(int start, int stop, boolean ascending) {

        int i = 0;
        int j = 0;
        CSVnode number = new CSVnode();

        long start_time = System.nanoTime();

        for (i = start + 1; i <= stop; i++) {
            number = data[i];
            for (j = i; ((j > start) && ((number.field2 < data[j - 1].field2) == ascending)); j--)
                data[j] = data[j - 1];
            data[j] = number;
        }
        long stop_time = System.nanoTime();
        return ((stop_time - start_time) / 1E6);
    }

    public double bubble_sort(int start, int stop, boolean ascending) {

        int i = 0;
        int j = 0;

        long start_time = System.nanoTime();

        for (i = start; i < stop; i++)
            for (j = stop; j > i; j--)
                if ((data[j].field2 < data[j - 1].field2) == ascending)
                    swap(j - 1, j);

        long stop_time = System.nanoTime();
        return ((stop_time - start_time) / 1E6);
    }
}