package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ps2.mack.springbootapi.contaBancaria.ContaBancaria;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRepository;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRequestDTO;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaResponseDTO;

@RestController
@RequestMapping("/api/contas")
public class ContaBancariaController {
    @Autowired
    private ContaBancariaRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Void> saveContaBancaria(@RequestBody @Valid ContaBancariaRequestDTO data) {
        try {
            ContaBancaria contaData = new ContaBancaria(data);
            repository.save(contaData);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ContaBancariaResponseDTO>> getAll() {
        // Nao contem erro notfound, retorna lista vazia
        try {
            List<ContaBancariaResponseDTO> contaList = repository.findAll().stream()
                    .map(ContaBancariaResponseDTO::new).toList();
            return ResponseEntity.ok(contaList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> updateContaBancaria(@RequestBody ContaBancaria data) {
        try {
            if (data.getId() > 0) {
                ContaBancaria updatedContaBancaria = repository.save(data);
                return ResponseEntity.ok(updatedContaBancaria);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContaBancaria(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaResponseDTO> getContaBancariaById(@PathVariable Long id) {
        try {
            ContaBancaria conta = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            return ResponseEntity.ok(new ContaBancariaResponseDTO(conta));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ContaBancariaResponseDTO>> buscarPorNome(@RequestParam String conta) {
        try {
            List<ContaBancariaResponseDTO> contaList = repository.findByNomeTitularContainingIgnoreCase(conta)
                    .stream()
                    .map(ContaBancariaResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(contaList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
