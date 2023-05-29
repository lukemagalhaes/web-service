package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ps2.mack.springbootapi.produto.Produto;
import ps2.mack.springbootapi.produto.ProdutoRepository;
import ps2.mack.springbootapi.produto.ProdutoRequestDTO;
import ps2.mack.springbootapi.produto.ProdutoResponseDTO;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Controller produtos", description = "Métodos HTTP dos produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Operation(summary = "Salvar produto", description = "Salva um novo produto.", tags = { "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<Void> saveProduto(@RequestBody @Valid ProdutoRequestDTO data) {
        try {
            Produto produtoData = new Produto(data);
            repository.save(produtoData);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    @Operation(summary = "Consultar produto", description = "Consulta a lista de todos os produtos", tags = {
            "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto buscado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {
        // Nao contem erro notfound, retorna lista vazia
        try {
            List<ProdutoResponseDTO> produtoList = repository.findAll().stream()
                    .map(ProdutoResponseDTO::new).toList();
            return ResponseEntity.ok(produtoList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto da lista passando o ID como parâmetro.", tags = {
            "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto data) {
        try {
            if (data.getId() > 0) {
                Produto updatedProduto = repository.save(data);
                return ResponseEntity.ok(updatedProduto);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Manda uma requisição que apaga um produto passado como parâmetro.", tags = {
            "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar produto específico", description = "Consulta na lista de produtos o ID passado como parâmetro de pesquisa.", tags = {
            "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto específico buscado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable Long id) {
        try {
            Produto produto = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            return ResponseEntity.ok(new ProdutoResponseDTO(produto));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar produto", description = "Busca pela descrição e retorna suas informações", tags = {
            "Produtos" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Descrição buscada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(@RequestParam String produto) {
        try {
            List<ProdutoResponseDTO> produtoList = repository.findByDescricaoContainingIgnoreCase(produto)
                    .stream()
                    .map(ProdutoResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(produtoList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
