import java.io.Serializable;
import java.util.Random;

public class MyDoubleWithOutTailLinkedList implements Serializable {

    private DNode top;

    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    // This method has been provided and you are not permitted to modify
    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        DNode temp = top;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }

        int totalBack = 0;
        temp = top;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }

        while (temp != null) {
            totalBack++;
            temp = temp.getPrev();
        }

        if (total != totalBack) {
            System.out.println("next links " + total + " do not match prev links " + totalBack);
            throw new RuntimeException();
        }

        return total;

    }

    // This method has been provided and you are not permitted to modify
    public void clear() {
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    public void add(Rental s) {
        DNode temp = top;

        // no list
        if (top == null) {
            top = new DNode(s, null, null);
            return;
        }

        // s is a Game, and s goes on top
        if (s instanceof Game && top.getData().getDueBack().after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        // s is a Console, and s goes on top
        if (s instanceof Console && top.getData().getDueBack().after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

    }

    public Rental remove(int index) {

        if (top == null)
            return null;

        if (index > size() - 1)
            return null;

        if (size() == 1){
            top = top.getNext();
            return null;
        }

        if (index == size() - 1){
            DNode b = getNode(index - 1);
            b.setNext(null);
            return null;
        }

          if (index == 0){
            top = top.getNext();
            top.setPrev(top.getPrev().getPrev());
        }else {
            DNode before = getNode(index - 1);
            before.setNext(before.getNext().getNext());
            before.getNext().setPrev(before);
        }
        return null;
    }

    public DNode getNode(int index){
        if (top == null)
            return null;

        if (index > size())
            return null;

        DNode temp = top;

        for (int i = 0; i < index; i++)
            temp = temp.getNext();

        return temp;

    }

    public Rental get(int index) {

        if (index > size())
            return null;

        if (top == null)
            return null;

        DNode temp = top;

        for (int i = 0; i < index; i++){
            temp = temp.getNext();
        }

        return temp.getData();
    }

    public void display() {
        DNode temp = top;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }

}