import java.util.Comparator;

public class CSVnode {

    int field1;
    long field2;
    String field3;
    String field4;

    public int compareTo(CSVnode x) {

        if (field2 > x.field2)
            return 1;
        else if (field2 == x.field2)
            return 0;
        else
            return -1;

    }

    public CSVnode() {

    }

    public CSVnode(int __field1__, long __field2__, String __field3__, String __field4__) {

        field1 = __field1__;
        field2 = __field2__;
        field3 = __field3__;
        field4 = __field4__;

    }

    public void node_render() {

        System.out.printf("%6d %d %s %s\n", field1, field2, field3, field4);

    }
}

class compare_number implements Comparator<CSVnode> {

    public int compare(CSVnode x, CSVnode y) {

        if (x.field2 == y.field2)
            return 0;
        else if (x.field2 > y.field2)
            return 1;
        else
            return -1;
    }
}

class compare_field4 implements Comparator<CSVnode> {

    public int compare(CSVnode x, CSVnode y) {
        return x.field4.compareToIgnoreCase(y.field4);
    }
}