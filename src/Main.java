import arvore.ABBTree;
import arvore.AVLTree;
import utils.Menu;
import utils.io.ScannerSingleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AVLTree AVL = new AVLTree();
        ABBTree ABB = new ABBTree();
        String caminhoArquivo = "./bible-kjv-na-np-lo.txt";
        Scanner sc = ScannerSingleton.getScanner();
        int option;

        Menu.welcomeMessage();
        do {
            Menu.options();
            option = sc.nextInt();
            sc.nextLine();
            Menu.clearConsole();
            switch (option) {
                case 1:
                    try {
                        // Leitura do arquivo com o scanner
                        Scanner scannerAVL = new Scanner(new File(caminhoArquivo));
                        System.out.println("Inserindo palavras na Arvore AVL");
                        // Loop para percorrer
                        while (scannerAVL.hasNextLine()) {
                            String linha = scannerAVL.nextLine();
                            // Separa as palavras por espaço
                            String[] palavras = linha.split("\\s+");
                            //Logica para inserir a palavra na arvore
                            for (String palavra : palavras) {
                                if (!palavra.equals("")) {
                                    AVL.searchInsert(palavra);
                                }
                            }
                        }
                        System.out.println("Todas as palavras foram inseridas na Arvore AVL");
                        scannerAVL.close();
                        Thread.sleep(2000);
                    } catch (FileNotFoundException e) {
                        System.err.println("Arquivo não encontrado: " + e.getMessage());
                    }

                    try {
                        Scanner scannerABB = new Scanner(new File(caminhoArquivo));
                        System.out.println("Inserindo palavras na Arvore ABB");
                        while (scannerABB.hasNextLine()) {
                            String linha = scannerABB.nextLine();
                            String[] palavras = linha.split("\\s+");
                            for (String palavra : palavras) {
                                if (!palavra.equals("")) {
                                    ABB.searchInsertElement(palavra);
                                }
                            }
                        }
                        System.out.println("Todas as palavras foram inseridas na Arvore ABB");
                        scannerABB.close();
                        Thread.sleep(2000);
                    } catch (FileNotFoundException e) {
                        System.err.println("Arquivo não encontrado: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Valor a ser inserido: ");
                    //Leitura do valor a ser inserido
                    String inserirNo = sc.nextLine();
                    AVL.searchInsert(inserirNo);
                    ABB.searchInsertElement(inserirNo);
                    System.out.println("No inserido");
                    Thread.sleep(3000);
                    break;
                case 3:
                    System.out.println("Valor a ser removido: ");
                    //Leitura do valor a ser removido
                    String removerNo = sc.nextLine();
                    AVL.remove(removerNo);
                    ABB.remove(removerNo);
                    System.out.println("No removido");
                    Thread.sleep(3000);
                    break;
                case 4:
                    System.out.println("Valor a ser encontrado: ");
                    String encontrarNo = sc.nextLine();
                    if (AVL.search(encontrarNo) && ABB.searchElement(encontrarNo)) {
                        System.out.println("No com valor " + encontrarNo + " encontrado");
                    }else {
                        System.out.println("No com valor " + encontrarNo + " não encontrado");
                    }
                    Thread.sleep(3000);
                    break;
                case 5:
                    System.out.println("Palavras Distintas (AVL): " + AVL.getTotalNumberOfNodes());
                    System.out.println("Palavras Distintas (ABB): " + ABB.getTotalNumberOfNodes());
                    Thread.sleep(3000);
                    break;
                case 6:
                    System.out.println("Total de palavras inserida (AVL): " + AVL.getTotalFrequency());
                    System.out.println("Total de palavras inserida (ABB): " + ABB.getTotalFrequency());
                    Thread.sleep(3000);
                    break;
                case 7:
                    System.out.println("Altura da arvore (AVL): " + AVL.getHeight());
                    System.out.println("Altura da arvore (ABB): " + ABB.getHeight());
                    Thread.sleep(3000);
                    break;
                case 8:
                    System.out.println("Comparações (AVL): " + AVL.comparisonsCounter);
                    System.out.println("Comparações (ABB): " + ABB.comparisonsCounterABB);
                    Thread.sleep(3000);
                    break;
                case 9:
                    System.out.println("Total de rotações: " + AVL.getTotalRotatesGlobal());
                    System.out.println("Rotações LL: " + AVL.globalRotationCounterLL);
                    System.out.println("rotações RR: " + AVL.globalRotationCounterRR);
                    System.out.println("Rotações LR: " + AVL.globalRotationCounterLR);
                    System.out.println("Rotações RL: " + AVL.globalRotationCounterRL);
                    Thread.sleep(3000);
                    break;
                case 10:
                    System.out.println("Altura minima (AVL): "+AVL.getMinimumHeight());
                    System.out.println("Altura minima (ABB): "+ABB.getMinimumHeight());
                    Thread.sleep(3000);
                    break;
                case 11:
                    int comparacoesAVL = AVL.comparisonsCounter;
                    int totalPalavrasAVL = AVL.getTotalFrequency();
                    int mediaComparacaoPalavrasAVL = comparacoesAVL / totalPalavrasAVL;
                    System.out.println("Media de comparações por palavras (AVL): "+mediaComparacaoPalavrasAVL);
                    int comparacoesABB = ABB.comparisonsCounterABB;
                    int totalPalavrasABB = ABB.getTotalFrequency();
                    int mediaComparacaoPalavrasABB = comparacoesABB / totalPalavrasABB;
                    System.out.println("Media de comparações por palavras (ABB): "+mediaComparacaoPalavrasABB);
                    Thread.sleep(3000);
                    break;
                case 12:
                    double rotateAVL = AVL.getTotalRotatesGlobal();
                    double palavrasAVL = AVL.getTotalNumberOfNodes();
                    double mediaComparacaoPalavrasDistintasAVL = rotateAVL / palavrasAVL;
                    System.out.printf("Média de rotações por palavras distintas: %.2f%n", mediaComparacaoPalavrasDistintasAVL);
                    Thread.sleep(3000);
                    break;
                case 13:
                    System.out.println("AVL preorder:");
                    AVL.preorderTraversal();
                    Thread.sleep(2000);
                    System.out.println("ABB preorder:");
                    ABB.preorderTraversal();
                    Thread.sleep(2000);
                    break;
                case 14:
                    System.out.println("AVL inorder:");
                    AVL.inorderTraversal();
                    Thread.sleep(2000);
                    System.out.println("ABB inorder:");
                    ABB.inorderTraversal();
                    Thread.sleep(2000);
                    break;
                case 15:
                    System.out.println("AVL postorder:");
                    AVL.postorderTraversal();
                    Thread.sleep(2000);
                    System.out.println("ABB postorder:");
                    ABB.postorderTraversal();
                    Thread.sleep(2000);
                    break;
                case 16:
                    System.out.println("Excluindo...");
                    AVL.clearTree();
                    ABB.clearTree();
                    System.out.println("Arvore AVL e ABB excluida");
                    Thread.sleep(2000);
                    break;
                case 17:
                    System.out.println("Menor valor da AVL: " + AVL.minValueNode());
                    System.out.println("Menor valor da ABB: " + ABB.minValueNode());
                    Thread.sleep(2000);
                    break;
                case 18:
                    System.out.println("Maior valor da AVL: " + AVL.maxValueNode());
                    System.out.println("Maior valor da ABB: " + ABB.maxValueNode());
                    Thread.sleep(2000);
                    break;
                case 19:
                    break;
                default:
                    Menu.invalidOptionMessage();
                    sc.nextLine();
                    break;
            }

            Menu.clearConsole();
        } while (option != 19);

        Menu.exitMessage();
        ScannerSingleton.closeScanner();




    }


}
