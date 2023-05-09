package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void saveProduto(@RequestBody @Valid ProdutoRequestDTO data) {
        Produto produtoData = new Produto(data);
        repository.save(produtoData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ProdutoResponseDTO> getAll(){
        List<ProdutoResponseDTO> produtoList = repository.findAll().stream().map(ProdutoResponseDTO::new).toList();
        return produtoList;
    }

    @PutMapping
    public Produto updateProduto(@RequestBody Produto data){
        if(data.getId()>0)
            return repository.save(data);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @GetMapping("/{id}")
    public ProdutoResponseDTO getProdutoById(@PathVariable Long id) {
        Produto produto = repository.findById(id).orElseThrow();
        return new ProdutoResponseDTO(produto);  
    }
    
    @GetMapping("/buscar")
    public List<ProdutoResponseDTO> buscarPorNome(@RequestParam String produto) {
        return repository.findByDescricaoContainingIgnoreCase(produto)
            .stream()
            .map(ProdutoResponseDTO::new)
            .collect(Collectors.toList());
    }
    // http://localhost:8080/produtos/buscar?produto=...
}
