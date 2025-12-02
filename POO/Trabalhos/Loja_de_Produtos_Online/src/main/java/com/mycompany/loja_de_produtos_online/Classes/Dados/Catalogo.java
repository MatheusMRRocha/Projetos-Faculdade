package com.mycompany.loja_de_produtos_online.Classes.Dados;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.TiposProduto;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.ProdutoFisico;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.ProdutoDigital;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Factorys.ProdutoFactory;
import com.mycompany.loja_de_produtos_online.Interfaces.IDados;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;


public class Catalogo<T extends Produto> {
    private final List<T> produtos;
    private final ProdutoFactory factory;
    private final IDados<String> dados; 

    public Catalogo(ProdutoFactory factory, IDados<String> dados) {
        this.produtos = new ArrayList<>();
        this.factory = factory;
        this.dados = dados;
    }

    public void adicionarProduto(T produto) {
        this.produtos.add(produto);
        System.out.printf("Produto Adicionado ao Catálogo: %s\n", produto.getNome());
    }

    // --- Lógica de Serialização/Desserialização (Corrigida) ---
    
    // Serializa Produtos em linhas de texto, garantindo formato decimal US (ponto)
    private List<String> serializar() {
        List<String> linhas = new ArrayList<>();
        for (Produto p : produtos) {
            String tipo = "";
            String param = "";
            
            if (p instanceof ProdutoFisico) {
                tipo = TiposProduto.FISICO.name();
                ProdutoFisico pf = (ProdutoFisico) p;
                
                // Formata o peso usando Locale.US para garantir o separador '.'
                String pesoFormatado = String.format(Locale.US, "%.2f", pf.getPeso());
                param = pesoFormatado + ";" + pf.getDimensao();
                
            } else if (p instanceof ProdutoDigital) {
                tipo = TiposProduto.DIGITAL.name();
                ProdutoDigital pd = (ProdutoDigital) p;
                param = pd.getDownload();
            } else {
                continue;
            }
            
            // Formata o preço usando Locale.US para garantir o separador '.'
            linhas.add(String.format(Locale.US, "%s|%s|%.2f|%s|%s", 
                tipo, p.getNome(), p.getPreco(), p.getDescricao(), param));
        }
        return linhas;
    }

    // Desserializa linhas de texto em objetos Produto
    private void desserializar(List<String> linhas) {
        // Limpa a lista atual, preparando para o carregamento
        this.produtos.clear(); 
        
        for (String linha : linhas) {
            // Usa o separador padrão |
            String[] partes = linha.split("\\|");
            if (partes.length < 5) {
                 System.err.printf("[Desserialização] Linha ignorada devido a formato inválido (menos de 5 partes): %s\n", linha);
                 continue;
            }

            try {
                TiposProduto tipo = TiposProduto.valueOf(partes[0]);
                String nome = partes[1];
                
                // Trata o preço: substitui vírgulas por pontos antes de tentar o parse, para robustez.
                String precoStr = partes[2].replace(',', '.'); 
                double preco = Double.parseDouble(precoStr);
                
                String descricao = partes[3];
                String paramBruto = partes[4];
                
                Map<String, Object> params;
                
                if (tipo == TiposProduto.FISICO) {
                    String[] subParams = paramBruto.split(";");
                    
                    // Trata o peso: substitui vírgulas por pontos antes de tentar o parse.
                    String pesoStr = subParams[0].replace(',', '.'); 
                    double peso = Double.parseDouble(pesoStr);
                    
                    String dimensao = subParams[1];
                    params = Map.of("peso", peso, "dimensao", dimensao);
                    
                } else { // DIGITAL
                    String downloadUrl = paramBruto;
                    params = Map.of("downloadUrl", downloadUrl);
                }

                @SuppressWarnings("unchecked")
                T produto = (T) factory.criarProduto(tipo, nome, preco, descricao, params);
                this.produtos.add(produto);

            } catch (Exception e) {
                // Mensagem de erro mais detalhada para facilitar o debug
                System.err.printf("[Desserialização] FALHA ao processar linha: %s. Erro: %s\n", linha, e.toString());
            }
        }
        System.out.printf("[Catálogo] %d produtos carregados.\n", this.produtos.size());
    }
    
    
    public void salvarCatalogo(String caminhoArquivo) throws IOException {
        List<String> dadosSerializados = serializar();
        dados.salvar(dadosSerializados, caminhoArquivo);
    }

    public void carregarCatalogo(String caminhoArquivo) throws IOException {
        List<String> dadosCarregados = dados.carregar(caminhoArquivo);
        desserializar(dadosCarregados);
    }

    public List<T> listarTodos() {
        return new ArrayList<>(produtos);
    }
    
    public Optional<T> buscarPorId(int id) {
        return produtos.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst();
    }

    public void exibirCatalogo() {
        System.out.println("\n=== Catálogo de Produtos ===");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto no catálogo.");
            return;
        }
        for (T produto : produtos) {
            System.out.println(produto.toString());
            System.out.printf("Detalhes: %s\n", produto.exibirDetalhes());
        }
        System.out.println("============================");
    }   
}