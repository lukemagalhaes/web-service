package ps2.mack.springbootapi.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public void saveTime(@RequestBody @Valid TimeRequestDTO data){
        Time timeData = new Time(data); 
        repository.save(timeData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<TimeResponseDTO> getAll(){
        List<TimeResponseDTO> timeList = repository.findAll().stream().map(TimeResponseDTO::new).toList();
        return timeList;
    }

    @PutMapping
    public Time updateTime(@RequestBody Time data){
        if(data.getId()>0)
            return repository.save(data);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @GetMapping("/{id}")
    public TimeResponseDTO getTimeById(@PathVariable Long id) {
        Time time = repository.findById(id).orElseThrow();
        return new TimeResponseDTO(time);
    }
    
    @GetMapping("/buscar")
    public List<TimeResponseDTO> buscarPorNome(@RequestParam String time) {
        return repository.findByNomeContainingIgnoreCase(time)
            .stream()
            .map(TimeResponseDTO::new)
            .collect(Collectors.toList());
    }
    // http://localhost:8080/times/buscar?time=pedro
}
