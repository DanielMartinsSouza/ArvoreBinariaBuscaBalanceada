package arvore;


import No.Node;
import balance.Balance;

public class AVLTree<T extends Comparable<T>> {
    public Node<T> root;
    public int globalRotationCounterRR;
    public int globalRotationCounterRL;
    public int globalRotationCounterLL;
    public int globalRotationCounterLR;
    public int comparisonsCounter;
    public int totalNodes;
    public int totalInsertedNodes;

    public AVLTree() {
        root = null;
    }

    //Exclui a arvore
    public void clearTree() {
        root = null;
    }

    //Metodo que verifica se a arvore está vazia
    public boolean checkAVLIsEmpty() {
        return root == null;
    }

    //Obtem o numero total de Rotações
    public int getTotalRotatesGlobal() {
        return (globalRotationCounterRL + globalRotationCounterRR + globalRotationCounterLR + globalRotationCounterLL);
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

    //Obtem o menor valor e o deleta
    private Node<T> delMin(Node<T> node, Node<T> rightNode, Balance h) {
        if (rightNode.leftNode != null) {
            rightNode.leftNode = delMin(node, rightNode.leftNode, h);
            if (h.balanceFactor) {
                balanceL(rightNode, h);
            }
            return rightNode;
        } else {
            node.key = rightNode.key;
            node.frequency = rightNode.frequency;
            h.balanceFactor = true;
            return rightNode.rightNode;
        }
    }

    //busca o valor a ser removido
    public void remove(T key) {
        Balance h = new Balance(false);
        root = removeRecursive(root, key, h);
    }

    private Node<T> removeRecursive(Node<T> node, T key, Balance h) {

        if (node == null) return node;

        if (key.compareTo(node.key) < 0) {
            comparisonsCounter++;
            node.leftNode = removeRecursive(node.leftNode, key, h);
            if (h.balanceFactor) {
                //Realiza o balanceamento após a remoção da arvore direita
                node = balanceL(node, h);
            }
        } else if (key.compareTo(node.key) > 0) {
            comparisonsCounter++;
            node.rightNode = removeRecursive(node.rightNode, key, h);
            if (h.balanceFactor) {
                //Realiza o balanceamento após a remoção da arvore esquerda
                node = balanceR(node, h);
            }
        } else {
            Node<T> nodeTemp = node;

            if (nodeTemp.rightNode == null) {
                //Remoção do No e atribui o No filho da esquerda ao No ao No Pai
                node = nodeTemp.leftNode;
                h.balanceFactor = true;
            } else if (nodeTemp.leftNode == null) {
                //Remoção do No e atribui o No filho da direita ao No ao No Pai
                node = nodeTemp.rightNode;
                h.balanceFactor = true;
            } else {
                //Caso quando o No tem dois filhos
                //Ocore a remoção do No quando
                nodeTemp = delMin(node.rightNode, nodeTemp, h);
                node.key = nodeTemp.key;
                node.frequency = nodeTemp.frequency;
                if (h.balanceFactor) {
                    //Realiza o balanceamentow
                    node = balanceR(node, h);
                }
            }
        }
        return node;
    }

    //Metodo onde realiza a inserção na arvore
    public void insertElement(T key) {
        Balance h = new Balance(false);
        root = insertElementRecursive(key, root, h);
    }
    private Node<T> insertElementRecursive(T key, Node<T> nodeA, Balance h) {
        //check whether the node is null or not
        if (nodeA == null) {
            nodeA = new Node<T>(key);
            h.balanceFactor = true;
        }

        if (key.compareTo(nodeA.key) == 0) {
            comparisonsCounter++;
            nodeA.frequency = nodeA.frequency + 1;
        }

        //insert a node in case when the given element is lesser than the element of the root node
        if (key.compareTo(nodeA.key) < 0) {
            comparisonsCounter++;
            nodeA.leftNode = insertElementRecursive(key, nodeA.leftNode, h);
            if (h.balanceFactor) {
                switch (nodeA.balance) {
                    case -1:
                        nodeA.balance = 0;
                        h.balanceFactor = false;
                        break;
                    case 0:
                        nodeA.balance = 1;
                        break;
                    case 1:
                        Node<T> nodeB = nodeA.leftNode;
                        if (nodeB.balance == 1) {
                            nodeA = rotateLL(nodeA);
                            globalRotationCounterLL++;
                        } else {
                            nodeA = rotateLR(nodeA);
                            globalRotationCounterLR++;
                        }
                        nodeA.balance = 0;
                        h.balanceFactor = false;
                }
            }
        } else {
            if (key.compareTo(nodeA.key) > 0) {
                comparisonsCounter++;
                nodeA.rightNode = insertElementRecursive(key, nodeA.rightNode, h);
                if (h.balanceFactor) {
                    switch (nodeA.balance) {
                        case 1:
                            nodeA.balance = 0;
                            h.balanceFactor = false;
                            break;
                        case 0:
                            nodeA.balance = -1;
                            break;
                        case -1:
                            Node<T> nodeB = nodeA.rightNode;
                            if (nodeB.balance == -1) {
                                nodeA = rotateRR(nodeA);
                                globalRotationCounterRR++;
                            } else {
                                nodeA = rotateRL(nodeA);
                                globalRotationCounterRL++;
                            }
                            nodeA.balance = 0;
                            h.balanceFactor = false;
                    }
                }
            }
        }
        return nodeA;
    }

    //Busca por um No e caso não encontre o insere
    public void searchInsert(T key) {
        Balance h = new Balance(false);
        int rotatesRR = globalRotationCounterRR;
        int rotatesRL = globalRotationCounterRL;
        int rotatesLL = globalRotationCounterLL;
        int rotatesLR = globalRotationCounterLR;
        System.out.println("Inserindo "+key);
        root = searchInsertRecursive(key, root, h);
        System.out.println("Rotação LL: "+(globalRotationCounterLL - rotatesLL));
        System.out.println("Rotação LR: "+(globalRotationCounterLR - rotatesLR));
        System.out.println("Rotação RR: "+(globalRotationCounterRR - rotatesRR));
        System.out.println("Rotação RL: "+(globalRotationCounterRL - rotatesRL));
    }
    private Node<T> searchInsertRecursive(T key, Node<T> nodeA, Balance h) {
        if (nodeA == null) {
            nodeA = new Node<T>(key);
            h.balanceFactor = true;
        }

        if (key.compareTo(nodeA.key) < 0) {
            comparisonsCounter++;
            nodeA.leftNode = searchInsertRecursive(key, nodeA.leftNode, h);
            //Altera fator de balanceamento apos a inserção da arvore esquerda
            if (h.balanceFactor) {
                switch (nodeA.balance) {
                    case -1:
                        nodeA.balance = 0;
                        h.balanceFactor = false;
                        break;
                    case 0:
                        nodeA.balance = 1;
                        break;
                    case 1:
                        Node<T> nodeB = nodeA.leftNode;
                        if (nodeB.balance == 1) {
                            //Rotação LL
                            globalRotationCounterLL++;
                            nodeA = rotateLL(nodeA);
                        } else {
                            //RotaçãoLR
                            globalRotationCounterLR++;
                            nodeA = rotateLR(nodeA);
                        }
                        nodeA.balance = 0;
                        h.balanceFactor = false;
                }
            }
        } else {
            if (key.compareTo(nodeA.key) > 0) {
                comparisonsCounter++;
                nodeA.rightNode = searchInsertRecursive(key, nodeA.rightNode, h);
                //Altera fator de balanceamento apos a inserção da arvore direita
                if (h.balanceFactor) {
                    switch (nodeA.balance) {
                        case 1:
                            nodeA.balance = 0;
                            h.balanceFactor = false;
                            break;
                        case 0:
                            nodeA.balance = -1;
                            break;
                        case -1:
                            Node<T> nodeB = nodeA.rightNode;
                            if (nodeB.balance == -1) {
                                //Rotação RR
                                globalRotationCounterRR++;
                                nodeA = rotateRR(nodeA);
                            } else {
                                //Rotação RL
                                globalRotationCounterRL++;
                                nodeA = rotateRL(nodeA);
                            }
                            nodeA.balance = 0;
                            h.balanceFactor = false;
                    }
                }
            } else {
                nodeA.frequency = nodeA.frequency + 1;
            }
        }
        return nodeA;

    }

    //Busca pelo elemento
    public boolean search(T key) {
        return searchRecursive(root, key);
    }
    private boolean searchRecursive(Node<T> node, T keyNode) {
        boolean check = false;
        //Busca pelo No enquanto No é diferente de Nulo (Caso for nulo não existe)
        while ((node != null) && !check) {
            T key = node.key;
            if (keyNode.compareTo(key) < 0) {
                //Percore a subarvore esquerda
                comparisonsCounter++;
                node = node.leftNode;
            }
            else if (keyNode.compareTo(key) > 0) {
                //Percore a subarvore direita
                comparisonsCounter++;
                node = node.rightNode;
            }
            else {
                //No encontrado
                check = true;
                break;
            }
            check = searchRecursive(node, keyNode);
        }
        return check;
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

    //Altera o fator de balanceamento do No e Realiza as rotações quando a subarvore da esquerda diminui
    private Node<T> balanceL(Node<T> node, Balance h) {
        switch (node.balance) {
            case 1:
                node.balance = 0;
                break;
            case 0:
                node.balance = -1;
                h.balanceFactor = false;
                break;
            case -1:
                Node<T> nodeB = node.rightNode;
                Node<T> nodeC = nodeB.leftNode;
                if (nodeB.balance <= 0) {
                    globalRotationCounterRR++;
                    node.rightNode = nodeB.leftNode;
                    nodeB.leftNode = node;
                    if (nodeB.balance == 0) {
                        nodeB.balance = 1;
                        h.balanceFactor = false;
                    } else {
                        node.balance = 0;
                        nodeB.balance = 0;
                    }
                    return nodeB;
                } else {
                    globalRotationCounterRL++;
                    nodeB.leftNode = nodeC.rightNode;
                    nodeC.rightNode = nodeB;
                    node.rightNode = nodeC.leftNode;
                    nodeC.leftNode = node;
                    if (nodeC.balance == -1) {
                        node.balance = 1;
                    } else {
                        node.balance = 0;
                    }
                    if (nodeC.balance == 1) {
                        nodeB.balance = -1;
                    } else {
                        nodeB.balance = 0;
                    }
                    nodeC.balance = 0;
                    return nodeC;
                }
        }
        return node;
    }

    //Altera o fator de balanceamento do No e Realiza as rotações quando a subarvore da direita diminui
    private Node<T> balanceR(Node<T> node, Balance h) {
        switch (node.balance) {
            case -1:
                node.balance = 0;
                break;
            case 0:
                node.balance = 1;
                h.balanceFactor = false;
                break;
            case 1:
                Node<T> nodeB = node.leftNode;
                Node<T> nodeC = nodeB.rightNode;
                if (nodeB.balance >= 0) {
                    globalRotationCounterLL++;
                    node.leftNode = nodeB.rightNode;
                    nodeB.rightNode = node;
                    if (nodeB.balance == 0) {
                        nodeB.balance = -1;
                        h.balanceFactor = false;
                    } else {
                        node.balance = 0;
                        nodeB.balance = 0;
                    }
                    return nodeB;
                } else {
                    globalRotationCounterLR++;
                    nodeB.rightNode = nodeC.leftNode;
                    nodeC.leftNode = nodeB;
                    node.leftNode = nodeC.rightNode;
                    nodeC.rightNode = node;
                    if (nodeC.balance == 1) {
                        node.balance = -1;
                    } else {
                        node.balance = 0;
                    }
                    if (nodeC.balance == 1) {
                        nodeB.balance = 1;
                    } else {
                        nodeB.balance = 0;
                    }
                    nodeC.balance = 0;
                    return nodeC;
                }
        }
        return node;
    }

    //Realiza a rotação LL
    private Node<T> rotateLL(Node<T> nodeA) {
        Node<T> nodeB = nodeA.leftNode;
        nodeA.leftNode = nodeB.rightNode;
        nodeB.rightNode = nodeA;
        nodeA.balance = 0;
        return nodeB;
    }

    //Realiza a rotação RR
    private Node<T> rotateRR(Node<T> nodeA) {
        Node<T> nodeB = nodeA.rightNode;
        nodeA.rightNode = nodeB.leftNode;
        nodeB.leftNode = nodeA;
        nodeA.balance = 0;
        return nodeB;
    }

    //Realiza a rotação LR
    private Node<T> rotateLR(Node<T> nodeA) {
        Node<T> nodeB = nodeA.leftNode;
        Node<T> nodeC = nodeB.rightNode;
        nodeB.rightNode = nodeC.leftNode;
        nodeC.leftNode = nodeB;
        nodeA.leftNode = nodeC.rightNode;
        nodeC.rightNode = nodeA;
        if (nodeC.balance == 1)
            nodeA.balance = -1;
        else
            nodeA.balance = 0;

        if (nodeC.balance == -1)
            nodeB.balance = 1;
        else
            nodeB.balance = 0;

        return nodeC;
    }

    //Realiza a rotação RL
    private Node<T> rotateRL(Node<T> nodeA) {
        Node<T> nodeB = nodeA.rightNode;
        Node<T> nodeC = nodeB.leftNode;
        nodeB.leftNode = nodeC.rightNode;
        nodeC.rightNode = nodeB;
        nodeA.rightNode = nodeC.leftNode;
        nodeC.leftNode = nodeA;
        if (nodeC.balance == -1)
            nodeA.balance = 1;
        else
            nodeA.balance = 0;

        if (nodeC.balance == 1)
            nodeB.balance = -1;
        else
            nodeB.balance = 0;
        return nodeC;
    }

}
