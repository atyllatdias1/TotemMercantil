import java.util.*;

public class TotemMercantil {

    static Scanner sc = new Scanner(System.in);
    static Map<String, Map<String, List<Produto>>> bancoDeDados = new LinkedHashMap<>();
    static List<Produto> carrinho = new ArrayList<>();
    static Map<Integer, String> categoriasPorNumero = new LinkedHashMap<>();

    public static void main(String[] args) {
        inicializarBanco();
        System.out.println("Bem-vindo ao Totem de Autoatendimento!\n");

        boolean comprando = true;
        while (comprando) {
            mostrarCategorias();
            String categoria = escolherCategoria();
            mostrarSubcategorias(categoria);
            String subcategoria = escolherSubcategoria(categoria);
            escolherProduto(categoria, subcategoria);

            System.out.println("\nDeseja: \n1. Continuar comprando\n2. Finalizar compra");
            int opc = sc.nextInt();
            sc.nextLine();
            if (opc == 2) comprando = false;
        }

        finalizarCompra();
    }

    static void inicializarBanco() {
        bancoDeDados.put("Hortifruti", new HashMap<>() {{
            put("Frutas", Arrays.asList(
                    new Produto("Banana Prata (kg)", 4.50),
                    new Produto("Maçã Fuji (kg)", 6.00),
                    new Produto("Mamão Formosa (kg)", 5.80)
            ));
            put("Verduras", Arrays.asList(
                    new Produto("Alface Crespa (unid)", 2.50),
                    new Produto("Couve (maço)", 3.00)
            ));
            put("Legumes", Arrays.asList(
                    new Produto("Batata (kg)", 4.20),
                    new Produto("Cenoura (kg)", 3.80)
            ));
        }});

        bancoDeDados.put("Laticínios", new HashMap<>() {{
            put("Leites", Arrays.asList(
                    new Produto("Leite Integral Italac 1L", 4.80),
                    new Produto("Leite Desnatado Piracanjuba 1L", 5.00)
            ));
            put("Iogurtes", Arrays.asList(
                    new Produto("Iogurte Nestlé Morango 170g", 2.20),
                    new Produto("Danone Polpa Sortida 540g", 7.50)
            ));
            put("Queijos", Arrays.asList(
                    new Produto("Mussarela Fatiada (kg)", 29.90),
                    new Produto("Queijo Minas Frescal (kg)", 21.50)
            ));
        }});

        bancoDeDados.put("Doces", new HashMap<>() {{
            put("Chocolates", Arrays.asList(
                    new Produto("Barra Lacta 90g", 4.50),
                    new Produto("KitKat 45g", 3.00),
                    new Produto("Ouro Branco pacote 300g", 9.90)
            ));
            put("Biscoitos Recheados", Arrays.asList(
                    new Produto("Passatempo Chocolate", 2.50),
                    new Produto("Trakinas Morango", 2.70)
            ));
            put("Guloseimas", Arrays.asList(
                    new Produto("Bala Fini Tubes", 4.00),
                    new Produto("Chiclete Trident", 1.50)
            ));
        }});

        bancoDeDados.put("Bebidas", new HashMap<>() {{
            put("Refrigerantes", Arrays.asList(
                    new Produto("Coca-Cola Lata 350ml", 6.50),
                    new Produto("Guaraná Antárctica 2L", 8.00),
                    new Produto("Fanta Laranja 600ml", 5.00)
            ));
            put("Sucos", Arrays.asList(
                    new Produto("Suco de Laranja Natural One 900ml", 9.50),
                    new Produto("Suco de Uva Integral 1L", 11.00)
            ));
            put("Águas", Arrays.asList(
                    new Produto("Água Mineral com Gás 500ml", 2.00),
                    new Produto("Água Mineral sem Gás 1,5L", 3.50)
            ));
        }});

        bancoDeDados.put("Carnes", new HashMap<>() {{
            put("Bovina", Arrays.asList(
                    new Produto("Contra-filé (500g)", 22.00),
                    new Produto("Carne Moída (500g)", 16.00),
                    new Produto("Picanha (500g)", 36.00)
            ));
            put("Suína", Arrays.asList(
                    new Produto("Costelinha Suína (500g)", 18.00),
                    new Produto("Linguiça Toscana (500g)", 12.00)
            ));
            put("Frango", Arrays.asList(
                    new Produto("Coxa e Sobrecoxa (kg)", 9.90),
                    new Produto("Filé de Peito (kg)", 14.90)
            ));
        }});

        bancoDeDados.put("Padaria", new HashMap<>() {{
            put("Pães", Arrays.asList(
                    new Produto("Pão Francês (kg)", 12.00),
                    new Produto("Pão de Forma Pullman", 6.50)
            ));
            put("Bolos", Arrays.asList(
                    new Produto("Bolo de Cenoura (fatia)", 4.00),
                    new Produto("Bolo de Chocolate (fatia)", 4.50)
            ));
            put("Salgados", Arrays.asList(
                    new Produto("Coxinha de Frango", 5.00),
                    new Produto("Pão de Queijo Grande", 3.50)
            ));
        }});

        int idx = 1;
        for (String cat : bancoDeDados.keySet()) {
            categoriasPorNumero.put(idx, cat);
            idx++;
        }
    }

    static void mostrarCategorias() {
        System.out.println("O que procura?\nCategorias disponíveis:");
        for (Map.Entry<Integer, String> entry : categoriasPorNumero.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    static String escolherCategoria() {
        System.out.print("Digite o número ou nome da categoria: ");
        String entrada = sc.nextLine().trim();
        try {
            int opc = Integer.parseInt(entrada);
            if (categoriasPorNumero.containsKey(opc)) {
                String categoriaEscolhida = categoriasPorNumero.get(opc);
                System.out.println("Você escolheu: " + categoriaEscolhida);
                return categoriaEscolhida;
            } else {
                System.out.println("Número inválido. Tente novamente.");
                return escolherCategoria();
            }
        } catch (NumberFormatException e) {
            for (String cat : bancoDeDados.keySet()) {
                if (cat.equalsIgnoreCase(entrada)) {
                    System.out.println("Você escolheu: " + cat);
                    return cat;
                }
            }
            System.out.println("Categoria inválida. Tente novamente.");
            return escolherCategoria();
        }
    }

    static void mostrarSubcategorias(String categoria) {
        System.out.println("Subcategorias de " + categoria + ":");
        int i = 1;
        for (String sub : bancoDeDados.get(categoria).keySet()) {
            System.out.println(i + ". " + sub);
            i++;
        }
    }

    static String escolherSubcategoria(String categoria) {
        System.out.print("Digite o número ou nome da subcategoria: ");
        String entrada = sc.nextLine().trim();

        Map<String, List<Produto>> subs = bancoDeDados.get(categoria);
        List<String> listaSubs = new ArrayList<>(subs.keySet());

        try {
            int opc = Integer.parseInt(entrada);
            if (opc >= 1 && opc <= listaSubs.size()) {
                String subEscolhida = listaSubs.get(opc - 1);
                System.out.println("Você escolheu: " + subEscolhida);
                return subEscolhida;
            } else {
                System.out.println("Número inválido. Tente novamente.");
                return escolherSubcategoria(categoria);
            }
        } catch (NumberFormatException e) {
            for (String sub : listaSubs) {
                if (sub.equalsIgnoreCase(entrada)) {
                    System.out.println("Você escolheu: " + sub);
                    return sub;
                }
            }
            System.out.println("Subcategoria inválida. Tente novamente.");
            return escolherSubcategoria(categoria);
        }
    }

    static void escolherProduto(String categoria, String subcategoria) {
        List<Produto> produtos = bancoDeDados.get(categoria).get(subcategoria);
        System.out.println("Produtos disponíveis em " + subcategoria + ":");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i).nome + " - R$ " + produtos.get(i).preco);
        }
        System.out.print("Escolha o produto (número): ");
        int index = sc.nextInt() - 1;
        sc.nextLine();
        if (index < 0 || index >= produtos.size()) {
            System.out.println("Produto inválido.");
            return;
        }
        System.out.print("Quantidade: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        Produto selecionado = produtos.get(index);
        Produto item = new Produto(selecionado.nome, selecionado.preco, qtd);
        carrinho.add(item);
        System.out.println("Produto adicionado ao carrinho!");
    }

    static void finalizarCompra() {
        double total = 0;
        System.out.println("\nResumo da compra:");
        for (Produto p : carrinho) {
            double subtotal = p.preco * p.quantidade;
            total += subtotal;
            System.out.println(p.quantidade + "x " + p.nome + " - R$ " + String.format("%.2f", subtotal));
        }
        System.out.println("\nTotal: R$ " + String.format("%.2f", total));
        System.out.println("\nPagamento confirmado!");
        System.out.println("Sua compra está sendo montada. Aguarde no caixa. Prazo estimado: 10 minutos.");
    }

    static class Produto {
        String nome;
        double preco;
        int quantidade;

        Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
            this.quantidade = 1;
        }

        Produto(String nome, double preco, int quantidade) {
            this.nome = nome;
            this.preco = preco;
            this.quantidade = quantidade;
        }
    }
}
