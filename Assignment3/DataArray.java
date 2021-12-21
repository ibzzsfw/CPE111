import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
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

    public void render(int first, int last) {

        for (int i = first; i <= last; i++)
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
 
    public double merge_sort_number(/* CSVnode[] sort, */CSVnode[] temporary, int first, int last) {

        int middle = 0;

        long start_sort = System.nanoTime();

        if (first < last) {
            middle = (first + last) / 2;
            merge_sort_number(/* data, */temporary, first, middle);
            merge_sort_number(/* data, */temporary, middle + 1, last);
            merge_data_number(/* data, */temporary, first, middle, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public void merge_data_number(/* CSVnode[] data, */CSVnode[] temporary, int first, int middle, int last) {

        int i, i1, i2;
        i1 = first;
        i2 = middle + 1;

        for (i = 0; i <= last - first; i++)
            if (i1 <= middle && i2 <= last)
                if (data[i1].field2 < data[i2].field2)
                    temporary[i] = data[i1++];
                else
                    temporary[i] = data[i2++];
            else if (i1 > middle)
                temporary[i] = data[i2++];
            else if (i2 > last)
                temporary[i] = data[i1++];
        for (i = 0; i <= last - first; i++)
            data[first + i] = temporary[i];
    }

    public double merge_sort_string(/* CSVnode[] sort, */CSVnode[] temporary, int first, int last) {

        int middle = 0;

        long start_sort = System.nanoTime();

        if (first < last) {
            middle = (first + last) / 2;
            merge_sort_string(/* data, */temporary, first, middle);
            merge_sort_string(/* data, */temporary, middle + 1, last);
            merge_data_string(/* data, */temporary, first, middle, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public void merge_data_string(/* CSVnode[] data, */CSVnode[] temporary, int first, int middle, int last) {

        int i, i1, i2;
        i1 = first;
        i2 = middle + 1;

        for (i = 0; i <= last - first; i++)
            if (i1 <= middle && i2 <= last)
                if (data[i1].field4.compareToIgnoreCase(data[i2].field4) < 0)
                    temporary[i] = data[i1++];
                else
                    temporary[i] = data[i2++];
            else if (i1 > middle)
                temporary[i] = data[i2++];
            else if (i2 > last)
                temporary[i] = data[i1++];
        for (i = 0; i <= last - first; i++)
            data[first + i] = temporary[i];
    }

    public double middlePivot_sort_number(int first, int last) {

        int i = first;
        int j = last;

        long start_sort = System.nanoTime();

        if (first < last) {
            long pivot = data[(first + last) / 2].field2;
            while (i < j) {
                while (data[i].field2 < pivot)
                    i++;
                while (data[j].field2 > pivot)
                    j--;
                if (i <= j)
                    swap(i++, j--);
            }
            if (first < j)
                middlePivot_sort_number(first, j);
            if (i < last)
                middlePivot_sort_number(i, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public double middlePivot_sort_string(int first, int last) {

        int i = first;
        int j = last;

        long start_sort = System.nanoTime();

        if (first < last) {
            String pivot = data[(first + last) / 2].field4;
            while (i < j) {
                while (data[i].field4.compareTo(pivot) < 0)
                    i++;
                while (data[j].field4.compareTo(pivot) > 0)
                    j--;
                if (i <= j)
                    swap(i++, j--);
            }
            if (first < j)
                middlePivot_sort_string(first, j);
            if (i < last)
                middlePivot_sort_string(i, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public double partition_sort_number(int first, int last) {

        int i = first;
        int j = last;

        long start_sort = System.nanoTime();

        if (first < last) {
            do {
                while ((data[i].field2 <= data[j].field2) && (i < j))
                    j--;
                if (data[i].field2 > data[j].field2) {
                    swap(i, j);
                    i++;
                }
                while ((data[i].field2 <= data[j].field2) && (i < j))
                    i++;
                if (data[i].field2 > data[j].field2) {
                    swap(i, j);
                    j--;
                }
            } while (i < j);
            if (first < j - 1)
                partition_sort_number(first, j - 1);
            if (i + 1 < last)
                partition_sort_number(i + 1, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public double partition_sort_string(int first, int last) {

        int i = first;
        int j = last;

        long start_sort = System.nanoTime();

        if (first < last) {
            do {
                while ((data[i].field4.compareTo(data[j].field4) <= 0) && (i < j))
                    j--;
                if (data[i].field4.compareTo(data[j].field4) > 0) {
                    swap(i, j);
                    i++;
                }
                while ((data[i].field4.compareTo(data[j].field4) <= 0) && (i < j))
                    i++;
                if (data[i].field4.compareTo(data[j].field4) > 0) {
                    swap(i, j);
                    j--;
                }
            } while (i < j);
            if (first < j - 1)
                partition_sort_string(first, j - 1);
            if (i + 1 < last)
                partition_sort_string(i + 1, last);
        }
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }

    public double Array_sort_number() {

        long start_sort = System.nanoTime();
        Arrays.sort(data, new compare_number());
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }


    public double Array_sort_string() {

        long start_sort = System.nanoTime();
        Arrays.sort(data, new compare_field4());
        long stop_sorting = System.nanoTime();
        return (stop_sorting - start_sort) / 1E6;
    }
}
    