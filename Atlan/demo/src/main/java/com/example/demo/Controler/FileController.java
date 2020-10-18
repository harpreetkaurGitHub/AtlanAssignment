package com.example.demo.Controler;


import com.example.demo.Service.ReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
public class FileController {

    @Autowired
    ReadFileService readFileService;

    @GetMapping("/read")
    public String readFile() {
            readFileService.runMethod();

        return "reading File";
    }

    @GetMapping("/pause")
    public String pauseFile() {
            readFileService.pauseFile();

        return "Successfully Paused";
    }

    @GetMapping("/resume")
    public String reseumFile() {
            readFileService.resumeFile();
        return "File Resume";
    }

    @GetMapping("/stop")
    public String  stopFile() {
            readFileService.stopFile();
        return "Successfully Stopped";
    }
}
