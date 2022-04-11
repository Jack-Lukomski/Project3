 import com.sun.org.glassfish.external.statistics.CountStatistic;

 import java.awt.event.ItemEvent;
 import java.io.Console.*;
 import java.io.Console.*;
 import java.io.PushbackInputStream;
 import java.io.Serializable;
        import java.util.Random;
        import java.util.concurrent.CountDownLatch;

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

        /** This adds a game if consoles are already added to the list **/
        if (s instanceof Game && getEndList(top).getData() instanceof Console){
            temp = new DNode(s, getBeginConsole(top), getEndGame(top));
            temp.getPrev().setNext(temp);
            temp.getNext().setPrev(temp);
            return;
        }

//         s is a Game, and s goes on top
        if (s instanceof Game && top.getData().getDueBack().after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }
        /** If the first rental to add to the list is a game **/
        if (s instanceof Game && size() == 1) {
            temp = new DNode(s, null, top);
            temp.getPrev().setNext(temp);
            return;
        }

        /** If s is a game and the size is greater than zero, this sorts the games by date **/
        if (s instanceof Game && size() > 1) {
            for (int i = 1; i < size(); i++) {
                if (temp.getData().getDueBack().after(s.dueBack)) {
                    temp = new DNode(s, temp.getNext(), temp.getPrev());
                    temp.getPrev().setNext(temp);
                    temp.getNext().setPrev(temp);
                }
                temp = temp.getNext();
            }
            temp = new DNode(s, null, temp);
            temp.getPrev().setNext(temp);
            return;
        }

        /** This adds a console to the list, only after games **/
        if (s instanceof Console){
            temp = new DNode(s, null, getEndList(top));
            temp.getPrev().setNext(temp);
        }

    }

    /*****************
     *
     * @param n
     * @return The last game in the list
     */
    public static DNode getEndGame(DNode n){
        try{
            while (n.getNext().getData() instanceof Game)
                n = n.getNext();
        } catch (NullPointerException e){

        }
        return n;
    }

    /***************
     *
     * @param n
     * @return The first console on the list
     */
     public static DNode getBeginConsole(DNode n){
        return getEndGame(n).getNext();
     }

    /************
     *
     * @param n
     * @return The last node in the list
     */
    public static DNode getEndList(DNode n){
            while (n.getNext() != null)
                n = n.getNext();

        return n;
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

    public static int getSize(DNode n){
        int count = 0;
        while (n.getNext() != null) {
            n = n.getNext();
            count++;
        }
        return count;
    }

}