import java.util.GregorianCalendar;

public class LinkedListText {
    public static void main(String[] args) {
        MyDoubleWithOutTailLinkedList list = new MyDoubleWithOutTailLinkedList();
        list.add(new Rental() {
            @Override
            public double getCost(GregorianCalendar checkOut) {
                return 0;
            }
        });
        System.out.println(list.size());
    }
}
