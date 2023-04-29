package ps2.mack.springbootapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps2.mack.springbootapi.produto.Produto;
import ps2.mack.springbootapi.produto.ProdutoRepository;
import ps2.mack.springbootapi.produto.ProdutoRequestDTO;
import ps2.mack.springbootapi.produto.ProdutoResponseDTO;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveProduto(@RequestBody ProdutoRequestDTO data) {
        Produto produtoData = new Produto(data);
        repository.save(produtoData);
        return;
    }

    @GetMapping
    public List<ProdutoResponseDTO> getAll() {
        List<ProdutoResponseDTO> produto = repository.findAll().stream()
                .map(ProdutoResponseDTO::new).toList();
        return produto;
    }
}
