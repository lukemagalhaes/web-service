package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ps2.mack.springbootapi.time.Time;
import ps2.mack.springbootapi.time.TimeRepository;
import ps2.mack.springbootapi.time.TimeRequestDTO;
import ps2.mack.springbootapi.time.TimeResponseDTO;

@RestController
@RequestMapping("/api/times")
public class TimeController {
    @Autowired
    private TimeRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Void> saveTime(@RequestBody @Valid TimeRequestDTO data) {
        try {
            Time timeData = new Time(data);
            repository.save(timeData);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> getAll() {
        // Nao contem erro notfound, retorna lista vazia
        try {
            List<TimeResponseDTO> timeList = repository.findAll().stream()
                    .map(TimeResponseDTO::new).toList();
            return ResponseEntity.ok(timeList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @PutMapping ("/{id}")
    public ResponseEntity<Time> updateTime(@RequestBody Time data) {
        try {
            if (data.getId() > 0) {
                Time updatedTime = repository.save(data);
                return ResponseEntity.ok(updatedTime);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TimeResponseDTO> getTimeById(@PathVariable Long id) {
        try {
            Time time = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            return ResponseEntity.ok(new TimeResponseDTO(time));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<TimeResponseDTO>> buscarPorNome(@RequestParam String time) {
        try {
            List<TimeResponseDTO> timeList = repository.findByNomeContainingIgnoreCase(time)
                    .stream()
                    .map(TimeResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(timeList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
