import com.sun.org.glassfish.external.statistics.CountStatistic;
import javafx.scene.chart.ScatterChart;
import org.omg.CORBA.NO_IMPLEMENT;

import java.awt.event.ItemEvent;
import java.io.Console.*;
import java.io.Console.*;
import java.io.Console.*;
import java.io.PushbackInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*********
 * @Description This method creates the basic functionally
 * of a linked list.
 * @Author Jack Lukomski, Hector Garcia, Julia Garcia
 * @Version 1.0
 */
public class MyDoubleWithOutTailLinkedList implements Serializable {

    /** The top of the linked list **/
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


    /*************
     * This method adds rentals to a list in order: games -> consoles
     * This method sorts the due dates from most recent to least recent
     * @param s The type of rental you would like to add
     */
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

            DNode newNode = new DNode(s, null, null);
            insertSort(temp, newNode);
    }

    /*****
     * This method sorts the list with the priority of games begin first
     * and consoles being second. When you add a new node to the list,
     * this method sorts by the date of the node added.
     * @param top the head of the linked list
     * @param newNode the node that is to be added
     * @return the new sorted list
     */
    private static DNode insertSort (DNode top, DNode newNode){
        DNode curr;

        if (top == null)
            top = newNode;
        else {
            curr = top;
            if (newNode.getData() instanceof Game) {
                while (curr.getNext() != null && curr.getNext().getData().getDueBack().before(newNode.getData().getDueBack()) && curr.getNext().getData() instanceof Game)
                    curr = curr.getNext();

                newNode.setNext(curr.getNext());

                if (curr.getNext() != null)
                    newNode.getNext().setPrev(newNode);

                curr.setNext(newNode);
                newNode.setPrev(curr);
            }
            else if (newNode.getData() instanceof Console){
                curr = getEndGame(curr);
                while (curr.getNext() != null && curr.getNext().getData().getDueBack().before(newNode.getData().getDueBack()) && curr.getNext().getData() instanceof Console)
                    curr = curr.getNext();

                newNode.setNext(curr.getNext());

                if (curr.getNext() != null)
                    newNode.getNext().setPrev(newNode);

                curr.setNext(newNode);
                newNode.setPrev(curr);
            }
        }
        return top;
    }

    /*****************
     * This method returns the last game in the list
     * @param n
     * @return The last game in the list
     */
    private static DNode getEndGame(DNode n){
        try{
            while (n.getNext().getData() instanceof Game)
                n = n.getNext();
        } catch (NullPointerException ignored){}
        return n;
    }

    /***************
     * This method returns the first item in the console list
     * @param n
     * @return The first console on the list
     */
    private static DNode getBeginConsole(DNode n){
        return getEndGame(n).getNext();
    }

    /************
     * This method returns last node in the list
     * @param n
     * @return The last node in the list
     */
    private static DNode getEndList(DNode n){
        while (n.getNext() != null)
            n = n.getNext();

        return n;
    }

    /******
     * This method removes rentals from the linked list.
     * @param index The index where the data is wanted to be removed at
     * @return null if it was a success
     */
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