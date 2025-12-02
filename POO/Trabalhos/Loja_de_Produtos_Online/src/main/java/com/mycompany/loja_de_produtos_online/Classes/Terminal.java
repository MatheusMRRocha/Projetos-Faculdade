package com.mycompany.loja_de_produtos_online.Classes;

import com.mycompany.loja_de_produtos_online.Classes.User.ProcessadorPedido;
import com.mycompany.loja_de_produtos_online.Classes.User.CarrinhoDeCompras;
import com.mycompany.loja_de_produtos_online.Classes.Dados.Catalogo;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.TiposProduto;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Pagamento.PagamentoCartao;
import com.mycompany.loja_de_produtos_online.Classes.Pagamento.PagamentoBoleto;
import com.mycompany.loja_de_produtos_online.Classes.Factorys.ProdutoFactory;
import com.mycompany.loja_de_produtos_online.Interfaces.ICarrinho;
import com.mycompany.loja_de_produtos_online.Interfaces.PagamentoStrategy;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Terminal {
    private final Catalogo<Produto> catalogo;
    private final CarrinhoDeCompras carrinho;
    private final ProdutoFactory factory;
    private final Scanner scanner;
    
    private final String CAMINHO_ARQUIVO_CATALOGO = "C:\\Users\\mathe\\OneDrive\\Documentos\\GitHub\\Projetos-Faculdade\\POO\\Trabalhos\\Loja_de_Produtos_Online\\src\\main\\java\\com\\mycompany\\loja_de_produtos_online\\Classes\\Dados\\catalogo_loja.txt";

    public Terminal(Catalogo<Produto> catalogo, ProdutoFactory factory){
        this.catalogo = catalogo;
        this.carrinho = new CarrinhoDeCompras();
        this.factory = factory;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar(){
        try {
            catalogo.carregarCatalogo(CAMINHO_ARQUIVO_CATALOGO);
        } catch (IOException e) {
            System.err.println("\nERRO DE CARREGAMENTO");
            System.err.println("Detalhe: " + e.getMessage());
        }

        int opcao;
        do {
            exibirMenuPrincipal();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
                processarOpcao(opcao);
            } catch (InputMismatchException e) {
                System.out.println("\n[ERRO] Entrada invalida, Por favor, digite um numero");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("\n[ERRO INESPERADO] " + e.getMessage());
                opcao = -1;
            }

        } while (opcao != 0);

        System.out.println("\nObrigado por usar a Loja Online. Encerrando o sistema.");
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n==================================");
        System.out.println("     LOJA ONLINE - MENU PRINCIPAL");
        System.out.println("==================================");
        System.out.println("1) Exibir Catalogo");
        System.out.println("2) Adicionar Novo Produto");
        System.out.println("3) Adicionar Produto ao Carrinho");
        System.out.println("4) Visualizar Carrinho de Compras (" + carrinho.getPrecoTotal() + " R$)");
        System.out.println("5) Finalizar Pedido");
        System.out.println("6) Salvar Catalogo");
        System.out.println("0) Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                catalogo.exibirCatalogo();
                break;
            case 2:
                adicionarNovoProduto();
                break;
            case 3:
                adicionarAoCarrinho();
                break;
            case 4:
                carrinho.exibirItens();
                break;
            case 5:
                finalizarPedido();
                break;
            case 6:
                salvarCatalogo();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.");
        }
    }

    private void salvarCatalogo() {
        try {
            catalogo.salvarCatalogo(CAMINHO_ARQUIVO_CATALOGO);
        } catch (IOException e) {
            System.err.println("\n[ERRO] Falha salvar o catalogo: " + e.getMessage());
        }
    }

    private void adicionarNovoProduto() {
        System.out.println("\n--- Adicionar Novo Produto ---");
        System.out.print("Nome do Produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço (ex: 50.00): ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        System.out.print("Tipo (FISICO ou DIGITAL): ");
        String tipoStr = scanner.nextLine().toUpperCase();

        try {
            TiposProduto tipo = TiposProduto.valueOf(tipoStr);
            Map<String, Object> params;
            Produto novoProduto;

            if (tipo == TiposProduto.FISICO) {
                System.out.print("Peso em kg (ex: 0.5): ");
                double peso = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Dimensão (ex: 'P', 'M', 'G'): ");
                String dimensao = scanner.nextLine();
                params = Map.of("peso", peso, "dimensao", dimensao);
            } else if (tipo == TiposProduto.DIGITAL) {
                System.out.print("URL de Download: ");
                String url = scanner.nextLine();
                params = Map.of("downloadUrl", url);
            } else {
                throw new IllegalArgumentException("Tipo de produto nao suportado");
            }

            novoProduto = factory.criarProduto(tipo, nome, preco, descricao, params);
            catalogo.adicionarProduto(novoProduto);

        } catch (IllegalArgumentException | InputMismatchException e) {
            System.err.println("\n[ERRO DE CRIAÇÃO] Dados invalidos ou tipo nao reconhecido. " + e.getMessage());
            if (scanner.hasNextLine()) scanner.nextLine();
        }
    }
    
    private void adicionarAoCarrinho() {
        if (catalogo.listarTodos().isEmpty()) {
            System.out.println("\nO catálogo está vazio");
            return;
        }
        
        System.out.println("\n--- Adicionar ao Carrinho ---");
        catalogo.exibirCatalogo(); // Mostra IDs disponíveis
        
        System.out.print("Digite o ID do produto que deseja adicionar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Optional<Produto> produtoOpt = catalogo.buscarPorId(id);
            
            if (produtoOpt.isPresent()) {
                carrinho.adicionarItem((ICarrinho) produtoOpt.get());
                System.out.printf("\n[SUCESSO] '%s' adicionado ao carrinho.\n", produtoOpt.get().getNome());
            } else {
                System.out.println("\n[ERRO] ID do produto nao encontrado no catalogo");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n[ERRO] Por favor, digite um numero de ID valido");
            scanner.nextLine();
        }
    }

    private void finalizarPedido() {
        if (carrinho.getPrecoTotal() == 0.0) {
            System.out.println("\n[ERRO] O carrinho está vazio, Adicione itens antes de finalizar o pedido");
            return;
        }

        System.out.println("\n--- FINALIZAR PEDIDO ---");
        System.out.printf("Total a Pagar: R$ %.2f\n", carrinho.getPrecoTotal());
        
        System.out.println("Escolha a forma de pagamento:");
        System.out.println("1. Cartao de Credito");
        System.out.println("2. Boleto Bancario");
        System.out.print("Opcao: ");

        int opPagamento;
        try {
            opPagamento = scanner.nextInt();
            scanner.nextLine();
            
            ProcessadorPedido processador = new ProcessadorPedido();
            PagamentoStrategy estrategia;
            
            if (opPagamento == 1) {
                System.out.print("Nome no cartao: ");
                String nome = scanner.nextLine();
                System.out.print("Número do cartao: ");
                String numero = scanner.nextLine();
                estrategia = new PagamentoCartao(nome, numero);
                
            } else if (opPagamento == 2) {
                System.out.print("CPF para emissão do boleto: ");
                String cpf = scanner.nextLine();
                estrategia = new PagamentoBoleto(cpf);
                
            } else {
                System.out.println("\n[ERRO] Opçao de pagamento invalida.");
                return;
            }

            processador.setEstrategiaPagamento(estrategia);
            processador.finalizarPedido(carrinho);
            carrinho.esvaziarCarrinho();
            
        } catch (InputMismatchException e) {
            System.out.println("\n[ERRO] Por favor, digite um numero valido para a opção de pagamento");
            scanner.nextLine();
        }
    }
}