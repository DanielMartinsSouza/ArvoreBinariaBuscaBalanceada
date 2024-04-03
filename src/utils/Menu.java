package utils;

public class Menu {
    public static void invalidOptionMessage() {
        System.out.println("Opção invalida. Aperte Enter para continuar.");
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void welcomeMessage() {
        System.out.println("Aplicação iniciada!");
    }

    public static void exitMessage() {
        System.out.println("Finalizando a aplicação!");
    }

    public static void options() {
        System.out.println("""
                Selecione alguma opção
                Opções:
                1 - Inserir No de acordo com os valores do arquivo bible-kjv-na-np-lo nas Arvores AVL e ABB
                2 - Inserir um novo No nas Arvores AVL e ABB
                3 - Remover valor nas Arvores AVL e ABB
                4 - Buscar valor nas Arvores AVL e ABB
                5 - Quantidade de palavras distintas nas Arvores AVL e ABB
                6 - Quantidades total de palavras inserida na arvore nas Arvores AVL e ABB
                7 - Altura da arvore nas Arvores AVL e ABB
                8 - Numero total de comparações nas Arvores AVL e ABB
                9 - Numero total de rotações na Arvores AVL
                10 - Numero de rotações LL na Arvores AVL
                11 - Numero de rotações RR na Arvores AVL
                12 - Numero de rotações LR na Arvores AVL
                13 - Numero de rotações RL na Arvores AVL
                14 - Médias de comparações por palavras nas Arvores AVL e ABB
                15 - Média de rotações por palavras distintas na Arvores AVL
                16 - Imprimir arvores Preorder
                17 - Imprimir arvores Inorder
                18 - Imprimir arvores Postorder
                19 - Excluir arvores
                20 - Menor valor da arvore
                21 - Maior valor da arvore
                22 - Sair
                """);
    }
}
