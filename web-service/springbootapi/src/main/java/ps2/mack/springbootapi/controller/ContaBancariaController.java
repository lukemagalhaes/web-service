package ps2.mack.springbootapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<ContaBancariaResponseDTO> conta = repository.findAll().stream()
                .map(ContaBancariaResponseDTO::new).toList();
        return conta;
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

}
