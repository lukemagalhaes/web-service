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
import ps2.mack.springbootapi.contaBancaria.ContaBancaria;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRepository;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaRequestDTO;
import ps2.mack.springbootapi.contaBancaria.ContaBancariaResponseDTO;

@RestController
@RequestMapping("/api/contas")
@Tag(name = "Controller conta bancária", description = "Métodos HTTP da conta bancária")
public class ContaBancariaController {
    @Autowired
    private ContaBancariaRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Operation(summary = "Salvar conta bancária", description = "Salva uma nova conta bancária.", tags = { "Contas" })
    // erro ao importar ApiResponse e ApiResponses!!
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta bancária salva com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
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
    @Operation(summary = "Consultar conta bancária", description = "Consulta a lista de todas as contas bancárias.", tags = {
            "Contas" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta bancária buscada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
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
    @Operation(summary = "Atualizar conta bancária", description = "Atualiza uma conta bancária da lista passando o ID como parâmetro.", tags = {
            "Contas" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta bancária atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
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
    @Operation(summary = "Deletar conta bancária", description = "Manda uma requisição que apaga a conta bancária passada como parâmetro.", tags = {
            "Contas" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta bancária apagada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
    public ResponseEntity<Void> deleteContaBancaria(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar conta bancária específica", description = "Consulta na lista de contas o ID passado como parâmetro de pesquisa.", tags = {
            "Contas" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta bancária específica buscada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
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
    @Operation(summary = "Buscar conta bancária", description = "Busca pelo nome na conta e retorna suas informações.", tags = {
            "Contas" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nome buscado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
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
