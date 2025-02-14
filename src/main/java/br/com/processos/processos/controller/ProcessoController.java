package br.com.processos.processos.controller;

import br.com.processos.processos.model.Processo;
import br.com.processos.processos.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {
    @Autowired
    private ProcessoService service;

    @PostMapping
    public ResponseEntity<Processo> createProcesso(@RequestParam("npu") String npu,
                                                   @RequestParam("municipio") String municipio,
                                                   @RequestParam("uf") String uf,
                                                   @RequestParam(value = "documentoPath", required = false) MultipartFile documentoPath) {
        try {
            Processo processo = new Processo();
            processo.setNpu(npu);
            processo.setMunicipio(municipio);
            processo.setUf(uf);

            if (documentoPath != null && !documentoPath.isEmpty()) {
                service.saveProcesso(processo, documentoPath);
            } else {
                service.saveProcesso(processo, null);
            }

            return ResponseEntity.ok(processo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Processo>> getAllProcessos(Pageable pageable) {
        Page<Processo> processos = service.getAllProcessos(pageable);
        return ResponseEntity.ok(processos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getProcessoById(@PathVariable Long id) {
        Processo processo = service.getProcessoById(id);
        return ResponseEntity.ok(processo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Processo> updateProcesso(@PathVariable Long id,
                                                   @RequestParam("npu") String npu,
                                                   @RequestParam("municipio") String municipio,
                                                   @RequestParam("uf") String uf) {
        try {
            Processo processoDetails = new Processo();
            processoDetails.setNpu(npu);
            processoDetails.setMunicipio(municipio);
            processoDetails.setUf(uf);
            Processo updatedProcesso = service.updateProcesso(id, processoDetails);
            return ResponseEntity.ok(updatedProcesso);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Processo> deleteProcesso(@PathVariable Long id) {
        Processo processo = service.getProcessoById(id);
        service.deleteProcesso(id);
        return ResponseEntity.ok(processo);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        Processo processo = service.getProcessoById(id);
        Path filePath = Paths.get(processo.getDocumentoPath());
        Resource resource = new UrlResource(filePath.toUri());

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                .body(resource);
    }
}
