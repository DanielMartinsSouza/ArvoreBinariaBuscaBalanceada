package arvore;

import No.Node;

public class ABBTree<T extends Comparable<T>> {
    public Node<T> root;
    public int comparisonsCounterABB;
    public int totalNodes;
    public int totalInsertedNodes;

    public ABBTree() {
        root = null;
    }

    //Exclui a arvore
    public void clearTree() {
        root = null;
    }

    //Metodo que verifica se a arvore está vazia
    public boolean checkABBIsEmpty() {
        return root == null;
    }

    //Obtem o numero total de No que estão na arvore
    public int getTotalNumberOfNodes() {
        totalNodes = getTotalNumberOfNodesRecursive(root);
        return totalNodes;
    }

    private int getTotalNumberOfNodesRecursive(Node<T> node) {
        if (node == null) return 0;
        else {
            int length = 1;
            length = length + getTotalNumberOfNodesRecursive(node.leftNode);
            length = length + getTotalNumberOfNodesRecursive(node.rightNode);
            return length;
        }
    }

    //Obtem a altura da arvore
    public int getHeight() {
        return getHeightRecursive(root) - 1; //-1 por conta que inicia no 0
    }

    private int getHeightRecursive(Node<T> node) {

        if (node == null) {
            return 0;
        } else {
            int leftHeight = getHeightRecursive(node.leftNode);
            int rightHeight = getHeightRecursive(node.rightNode);

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    //Obtem numero total de No inseridos na arvore
    public int getTotalFrequency() {
        totalInsertedNodes = getTotalFrequencyRecursive(root);
        return totalInsertedNodes;
    }

    private int getTotalFrequencyRecursive(Node<T> node) {
        if (node == null) return 0;
        else {
            int frequency = node.frequency;
            frequency += getTotalFrequencyRecursive(node.leftNode);
            frequency += getTotalFrequencyRecursive(node.rightNode);
            return frequency;
        }
    }

    //Obtem o menor valor inserido na arvore
    public T minValueNode(){
        return minValueNodeRecursive(root);
    }

    private T minValueNodeRecursive(Node<T> node) {
        Node<T> minNode = node;
        while (minNode.leftNode != null) minNode = minNode.leftNode;
        return minNode.key;
    }

    //Obtem o maior valor inserido na arvore
    public T maxValueNode(){
        return maxValueNodeRecursive(root);
    }

    private T maxValueNodeRecursive(Node<T> node) {
        Node<T> maxNode = node;
        while (maxNode.rightNode != null) maxNode = maxNode.rightNode;
        return maxNode.key;
    }

    public int getMinimumHeight() {
        int totalNodes = getTotalNumberOfNodes();
        return (int) (Math.ceil(Math.log(totalNodes + 1) / Math.log(2))) - 1;
    }


    //Metodo onde realiza a inserção na arvore
    public void insertElement(T key) {
        System.out.println("Inserindo "+key);
        root = insertElementRecursive(root, key);
    }
    private Node<T> insertElementRecursive(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        }

        if (key.compareTo(node.key) < 0) {
            comparisonsCounterABB++;
            node.leftNode = insertElementRecursive(node.leftNode, key);
        } else {
            comparisonsCounterABB++;
            if (key.compareTo(node.key) > 0) {
                comparisonsCounterABB++;
                node.rightNode = insertElementRecursive(node.rightNode, key);
            } else {
                comparisonsCounterABB++;
                node.frequency += 1;
            }
        }

        return node;
    }

    public void searchInsertElement(T key) {
        System.out.println("Inserindo "+key);
        root = insertElementRecursive(root, key);
    }
    private Node<T> searchInsertElementRecursive(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        }

        if (key.compareTo(node.key) < 0) {
            comparisonsCounterABB++;
            node.leftNode = insertElementRecursive(node.leftNode, key);
        } else {
            comparisonsCounterABB++;
            if (key.compareTo(node.key) > 0) {
                comparisonsCounterABB++;
                node.rightNode = insertElementRecursive(node.rightNode, key);
            } else {
                comparisonsCounterABB++;
                node.frequency += 1;
            }
        }

        return node;
    }


    //busca o valor a ser removido
    public void remove(T key) {
        root = removeRecursive(root, key);
    }
    private Node<T> removeRecursive(Node<T> node, T key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            comparisonsCounterABB++;
            node.leftNode = removeRecursive(node.leftNode, key);
        } else {
            comparisonsCounterABB++;
            if (key.compareTo(node.key) > 0) {
                comparisonsCounterABB++;
                node.rightNode = removeRecursive(node.rightNode, key);
            } else {
                comparisonsCounterABB++;
                if (node.leftNode == null) {
                    return node.rightNode;
                } else if (node.rightNode == null) {
                    return node.leftNode;
                }

                node.key = minValueNode(node.rightNode);
                node.rightNode = removeRecursive(node.rightNode, node.key);
            }
        }
        return node;
    }

    //Obtem o menor valor e o deleta
    private T minValueNode(Node<T> node) {
        T minValue = node.key;
        while (node.leftNode != null) {
            minValue = node.leftNode.key;
            node = node.leftNode;
        }
        return minValue;
    }

    //Busca pelo elemento
    public boolean searchElement(T key) {
        return searchElementRecursive(root, key);
    }
    private boolean searchElementRecursive(Node<T> node, T key) {
        if (node == null) {
            return false;
        }

        if (key.compareTo(node.key) < 0) {
            comparisonsCounterABB++;
            return searchElementRecursive(node.leftNode, key);
        } else {
            comparisonsCounterABB++;
            if (key.compareTo(node.key) > 0) {
                comparisonsCounterABB++;
                return searchElementRecursive(node.rightNode, key);
            } else {
                comparisonsCounterABB++;
                return true;
            }
        }
    }

    //Imprime a arvore InOrder
    public void inorderTraversal() {
        inorderTraversal(root);
    }
    private void inorderTraversal(Node<T> head) {
        if (head != null) {
            inorderTraversal(head.leftNode);
            System.out.print(head.key + " ");
            inorderTraversal(head.rightNode);
        }
    }

    //Imprime a arvore PreOrder
    public void preorderTraversal() {
        preorderTraversal(root);
    }
    private void preorderTraversal(Node<T> head) {
        if (head != null) {
            System.out.print(head.key + " ");
            preorderTraversal(head.leftNode);
            preorderTraversal(head.rightNode);
        }
    }

    //Imprime a arvore PosOrder
    public void postorderTraversal() {
        postorderTraversal(root);
    }
    private void postorderTraversal(Node<T> head) {
        if (head != null) {
            postorderTraversal(head.leftNode);
            postorderTraversal(head.rightNode);
            System.out.print(head.key + " ");
        }
    }
}
