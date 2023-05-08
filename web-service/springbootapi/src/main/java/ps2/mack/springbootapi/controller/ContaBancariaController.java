package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import ps2.mack.springbootapi.contaBancaria.ContaBancaria;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRepository;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRequestDTO;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaResponseDTO;

@RestController
@RequestMapping("/contas")
public class ContaBancariaController {
    @Autowired
    private ContaBancariaRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveContaBancaria(@RequestBody @Valid ContaBancariaRequestDTO data) {
        ContaBancaria contaData = new ContaBancaria(data);
        repository.save(contaData);
        return;
    }

    @GetMapping
    public List<ContaBancariaResponseDTO> getAll() {
        List<ContaBancariaResponseDTO> contaList = repository.findAll().stream()
                .map(ContaBancariaResponseDTO::new).toList();
        return contaList;
    }

    @PutMapping
    public ContaBancaria updateContaBancaria(@RequestBody ContaBancaria data){
        if(data.getId()>0)
            return repository.save(data);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteContaBancaria(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @GetMapping("/{id}")
    public ContaBancariaResponseDTO getContaBancariaById(@PathVariable Long id) {
        ContaBancaria conta = repository.findById(id).orElseThrow();
        return new ContaBancariaResponseDTO(conta);
    }

    @GetMapping("/buscar")
    public List<ContaBancariaResponseDTO> buscarPorNome(@RequestParam String conta) {
        return repository.findByNomeTitularContainingIgnoreCase(conta)
            .stream()
            .map(ContaBancariaResponseDTO::new)
            .collect(Collectors.toList());
    }

}
