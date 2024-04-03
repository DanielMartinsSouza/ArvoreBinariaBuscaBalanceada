package No;

public class Node<T> {
    public T key;
    public int balance;
    public int frequency;
    public Node<T> leftNode;
    public Node<T> rightNode;

    //default constructor to create null node
    public Node() {
        leftNode = null;
        rightNode = null;
        key = null;
        balance = 0;
    }
    // parameterized constructor
    public Node(T key) {
        leftNode = null;
        rightNode = null;
        this.key = key;
        balance = 0;
        frequency = 1;
    }
}
