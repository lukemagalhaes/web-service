package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ps2.mack.springbootapi.produto.Produto;
import ps2.mack.springbootapi.produto.ProdutoRepository;
import ps2.mack.springbootapi.produto.ProdutoRequestDTO;
import ps2.mack.springbootapi.produto.ProdutoResponseDTO;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
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
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
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
