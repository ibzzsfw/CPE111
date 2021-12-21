public class CSVnode {

    int field1;
    long field2;
    String field3;
    String field4;

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