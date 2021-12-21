import java.util.Arrays;

public class Assignment3 {

    public static void main(String[] args) {

        int maximum = 100010;
        double[/* algorithm */][/* instant */] time = new double[4][2];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                time[i][j] = 0;
        Object[/* (?) sort */][/* field */][][] table = new Object[4][3][3][4];
        {
            for (int x = 0; x < 2; x++)
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        for (int k = 0; k < 4; k++)
                            table[x][i][j][k] = null;
        }

        DataArray raw = new DataArray(maximum);
        DataArray sort = new DataArray(maximum);
        DataArray temporary = new DataArray(maximum);

        if (raw.load_Data_File("test.csv") > 0) {

            System.out.println("\nThis is sorting comparison program.");
            System.out.println("File read  = " + raw.counter + " records.");
            /* start input section */
            // Scanner scanner = new Scanner(System.in);
            // System.out.printf("Input number to sort : ");
            int n = 100000;// scanner.nextInt();
            // scanner.close();
            /* end of input section */
            /* start sort section */
            sort.data = raw.data.clone();
            // table[0][0][0] = table[1][0][0] = table[2][0][0] = table[3][0][0] =
            // create_table_from(sort.data[0]);
            // table[0][0][1] = table[1][0][1] = table[2][0][1] = table[3][0][1] =
            // create_table_from(sort.data[49999]);
            // table[0][0][2] = table[1][0][2] = table[2][0][2] = table[3][0][2] =
            // create_table_from(sort.data[99999]);
            sort.data = Arrays.copyOf(raw.data, n);
            time[0][0] = sort.Array_sort_number();
            // table[0][1][0] = create_table_from(sort.data[0]);
            // table[0][1][1] = create_table_from(sort.data[49999]);
            // table[0][1][2] = create_table_from(sort.data[99999]);
            sort.data = Arrays.copyOf(raw.data, n);
            time[0][1] = sort.Array_sort_string();
            // table[0][2][0] = create_table_from(sort.data[0]);
            // table[0][2][1] = create_table_from(sort.data[49999]);
            // table[0][2][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            time[1][0] = sort.partition_sort_number(0, n - 1);
            // table[1][1][0] = create_table_from(sort.data[0]);
            // table[1][1][1] = create_table_from(sort.data[49999]);
            // table[1][1][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            time[1][1] = sort.partition_sort_string(0, n - 1);
            // table[1][2][0] = create_table_from(sort.data[0]);
            // table[1][2][1] = create_table_from(sort.data[49999]);
            // table[1][2][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            time[2][0] = sort.middlePivot_sort_number(0, n - 1);
            // table[2][1][0] = create_table_from(sort.data[0]);
            // table[2][1][1] = create_table_from(sort.data[49999]);
            // table[2][1][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            time[2][1] = sort.middlePivot_sort_string(0, n - 1);
            // table[2][2][0] = create_table_from(sort.data[0]);
            // table[2][2][1] = create_table_from(sort.data[49999]);
            // table[2][2][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            temporary.data = raw.data.clone();
            time[3][0] = sort.merge_sort_number(/* sort.data, */temporary.data, 0, n - 1);
            // table[3][1][0] = create_table_from(sort.data[0]);
            // table[3][1][1] = create_table_from(sort.data[49999]);
            // table[3][1][2] = create_table_from(sort.data[99999]);
            sort.data = raw.data.clone();
            temporary.data = raw.data.clone();
            time[3][1] = sort.merge_sort_string(temporary.data, 0, n - 1);
            // table[3][2][0] = create_table_from(sort.data[0]);
            // table[3][2][1] = create_table_from(sort.data[49999]);
            // table[3][2][2] = create_table_from(sort.data[99999]);
            /* end of sort section */
            display_time(time);
            // display_testcase(table);
        } else
            System.out.println("Error");
        System.out.printf("\n\nProgram written by 63070501061 Suppakorn Rakna\n\n");
    }

    public static void display_time(double[][] time) {

        String[/* Type of sort */] sort = { "Array.sort", "Partitioning", "Middle-pivot", "Merge Sort" };
        String bar_time = "\n+---------------+-----------------------+-----------------------+";
        System.out.println(bar_time + "\n|\t\t|\tnumber (ms)\t|\tString (ms)\t|" + bar_time);
        for (int i = 0; i < 4; i++)
            System.out.printf("|%s\t|\t%-9.4fms.\t|\t%-9.4fms.\t|%s\n", sort[i], time[i][0], time[i][1], bar_time);
    }

    public static String bar_table = "\n+-----------+-----+------------+----------------------------------------------------+----------------------------------------------------+";

    public static void header(String head) {

        System.out.println(bar_table + "\n|" + "\t\t\t\t\t\t\t" + head + "\t\t\t\t\t\t\t" + "\t |" + bar_table);
    }

    public static Object[] create_table_from(CSVnode data) {

        Object[] table = new Object[4];

        table[0] = data.field1;
        table[1] = data.field2;
        table[2] = data.field3;
        table[3] = data.field4;

        return table;

    }

    public static void display_testcase(Object[][][][] table) {

        System.out.printf("%s\n| test.csv  |order|   number   |\t\t\tstring1\t\t\t    |\t\t\t\tstring2\t\t\t |",
                bar_table);
        String[/* (?) sort */] column = { "data[0]", "data[49999]", "data[99999]" };
        int run = 0;
        for (int algo = 0; algo < 4; algo++)
            for (int _sort = 0; _sort < 3; _sort++)
                for (int index = 0; index < 3; index++) {
                    switch (run) {
                        case 0:
                            header("unsort\t\t");
                            break;
                        case 3:
                            header("Array.sort number");
                            break;
                        case 6:
                            header("Array.sort string");
                            break;
                        case 9:
                            header("unsort\t\t");
                            break;
                        case 12:
                            header("Partitioning number");
                            break;
                        case 15:
                            header("Partitioning string");
                            break;
                        case 18:
                            header("unsort\t\t");
                            break;
                        case 21:
                            header("Middle-pivot number");
                            break;
                        case 24:
                            header("Middle-pivot string");
                            break;
                        case 27:
                            header("unsort\t\t");
                            break;
                        case 30:
                            header("Merge sort number");
                            break;
                        case 33:
                            header("Merge sort string");
                            break;
                    }
                    System.out.printf("|%-11s|%5d|%d|%s|%s|%s\n", column[index], table[algo][_sort][index][0],
                            table[algo][_sort][index][1], table[algo][_sort][index][2], table[algo][_sort][index][3],
                            bar_table);

                    run++;
                }
    }
}