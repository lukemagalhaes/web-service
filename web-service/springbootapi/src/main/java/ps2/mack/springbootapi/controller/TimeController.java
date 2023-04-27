package ps2.mack.springbootapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ps2.mack.springbootapi.time.Time;
import ps2.mack.springbootapi.time.TimeRepository;
import ps2.mack.springbootapi.time.TimeRequestDTO;
import ps2.mack.springbootapi.time.TimeResponseDTO;

@RestController
@RequestMapping("time")
public class TimeController {
    
    @Autowired
    private TimeRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveTime(@RequestBody TimeRequestDTO data){
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
}
