import com.sun.org.glassfish.external.statistics.CountStatistic;

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
        // adding a cousole and game on the same date does not work

        // no list
        if (top == null) {
            top = new DNode(s, null, null);
            return;
        }



        // list only has console and rental is a game (CHECK)
//        if(top.getData() instanceof Console && s instanceof Game){
//            top = new DNode(s, top, null);
//            top.getNext().setPrev(top);
//            return;
//        }

        // list has game and we want to add console
//        if(top.getData() instanceof Game && s instanceof Console){
//
//            top = new DNode(s, null, top);
//            top.getPrev().setNext(top);
//            return;
//        }

        if (s instanceof Console && top.getData().getDueBack().after(s.dueBack)){
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        // s is a Game, and s goes on top
        if (s instanceof Game && top.getData().getDueBack().after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        /**** Case when date is before other game ****/
//         s is a Game, and s goes at the bottom
//        if (s instanceof Game && top.getData().getDueBack().before(s.dueBack)){
//            top = new DNode(s, null, top);
//            top.getPrev().setNext(top);
//            return;
//        }

        /**** Case when date is before other Console ****/
        // s is a Game, and s goes at the bottom
//        if (s instanceof Console && top.getData().getDueBack().before(s.dueBack)){
//            top = new DNode(s, null, top);
//            top.getPrev().setNext(top);
//            return;
//        }

        /****************** Need to double check ********************/

         //list already has game and adding another game (after) (NOTE: not sorting by due date)
        if(top.getData() instanceof Game && s instanceof Game){
            // sort by DueBack
            System.out.println("Before if statement " + top.toString());
            if(top.getData().getDueBack().after(s.dueBack)){
                top = new DNode(s, null, top);
                System.out.println("After if statement " + top.toString());
                top.getPrev().setNext(top);
                System.out.println("After setting arrows " + top.toString());
                return;
            }

            if (top.getData().getDueBack().before(s.dueBack)){
                top = new DNode(s, top, null);
                top.getNext().setPrev(top);
                return;
            }
        }

        // list only has console and adding another console (NOTE: not sorting by due date)
        /********* Need to check because currently last statement is void *******/
        if(top.getData() instanceof Console && s instanceof Console){
            System.out.println("Before if statement " + top.toString());
            if(top.getData().getDueBack().after(s.dueBack)){
                top = new DNode(s, null, top);
                System.out.println("After if statement " + top.toString());
                top.getPrev().setNext(top);
                System.out.println("After setting arrows " + top.toString());
                return;
            }

            if (top.getData().getDueBack().before(s.dueBack)){
                top = new DNode(s, top, null);
                top.getNext().setPrev(top);
                return;
            }
        }

        /** edits made on 14/10/2022 **/

        // If we have a game, and we want to add a console -> game, console
        if(top.getData() instanceof Game && s instanceof Console){
        while (temp.getData() instanceof Console)
            temp = temp.getPrev();

            top = new DNode(s, temp, null);
            top.getNext().setPrev(top);

        }

        // If we have a console, and we want to add a game -> game, console
        if(top.getData() instanceof Console && s instanceof Game){
            while (temp.getData() instanceof Game)
                temp = temp.getPrev();

            top = new DNode(s, temp, null);
            top.getNext().setPrev(top);

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