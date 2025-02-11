package br.com.processos.processos.model;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {
    @Autowired
    private ProcessoService service;

    @PostMapping
    public ResponseEntity<Processo> createProcesso(@Valid @RequestBody Processo processo) {
        Processo savedProcesso = service.saveProcesso(processo);
        return ResponseEntity.ok(savedProcesso);
    }

    @GetMapping
    public ResponseEntity<List<Processo>> getAllProcessos() {
        List<Processo> processos = service.getAllProcessos();
        return ResponseEntity.ok(processos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getProcessoById(@PathVariable Long id) {
        Processo processo = service.getProcessoById(id);
        return ResponseEntity.ok(processo);
    }
}
