package com.example.CatalogoProdutos.controller;

import com.example.CatalogoProdutos.model.Produto;
import com.example.CatalogoProdutos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ProdutoController {

    // Define o diretório onde as imagens dos produtos serão salvas
    private static final String UPLOAD_DIR = "src/main/resources/static/imagens/produtos/";

    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para exibir a página de cadastro
    @GetMapping("/cadastro-produto")
    public String exibirFormularioCadastro() {
        return "cadastroProduto"; // Retorna o nome do arquivo HTML (sem a extensão)
    }

    // Método para processar o envio do formulário
    @PostMapping("/cadastrar-produto")
    public String cadastrarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") Double preco,
            @RequestParam(value = "desconto", required = false) Integer desconto,
            @RequestParam("imagem") MultipartFile imagem,
            RedirectAttributes redirectAttributes) {

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setDesconto(desconto != null ? desconto : 0);

        // Lógica para salvar a imagem
        if (!imagem.isEmpty()) {
            try {
                // Garante que o diretório de upload exista
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Gera um nome de arquivo único para evitar conflitos
                String nomeArquivoUnico = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();
                Path caminhoArquivo = uploadPath.resolve(nomeArquivoUnico);

                // Copia o arquivo para o diretório de destino
                Files.copy(imagem.getInputStream(), caminhoArquivo);

                // Salva o caminho relativo da imagem no objeto produto
                produto.setCaminhoImagem("/imagens/produtos/" + nomeArquivoUnico);

            } catch (IOException e) {
                e.printStackTrace();
                // Adiciona uma mensagem de erro para o usuário
                redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem do produto.");
                return "redirect:/cadastro-produto";
            }
        }

        // Salva o objeto produto no banco de dados
        produtoRepository.save(produto);

        // Adiciona uma mensagem de sucesso para ser exibida na página
        redirectAttributes.addFlashAttribute("successMessage", "Produto cadastrado com sucesso!");

        // Redireciona de volta para a página de cadastro
        return "redirect:/cadastro-produto";
    }
}