package dz.a2s.a2spreparation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("")
    public ResponseEntity<String> home() throws Exception {
        return ResponseEntity.ok("Welcome home");
    }

}
