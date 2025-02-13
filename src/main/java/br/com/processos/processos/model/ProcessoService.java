package br.com.processos.processos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProcessoService {

    private static final String UPLOAD_DIR = "src/main/resources/arquivos/";
    @Autowired
    private ProcessoRepository repository;

    /*public Processo saveProcesso(Processo processo) {
        return repository.save(processo);
    }*/

    public Processo saveProcesso(Processo processo, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return repository.save(processo);
    }

    public Page<Processo> getAllProcessos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Processo getProcessoById(Long id) {
        Processo processo = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo not found with id " + id));
        processo.setDataVisualizacao(LocalDateTime.now());
        return repository.save(processo);
    }

    public Processo updateProcesso(Long id, Processo processoDetails) throws IOException {
        Processo processo = getProcessoById(id);
        processo.setNpu(processoDetails.getNpu());
        processo.setMunicipio(processoDetails.getMunicipio());
        processo.setUf(processoDetails.getUf());
        processo.setDataVisualizacao(processoDetails.getDataVisualizacao());
        return repository.save(processo);
    }

    public void deleteProcesso(Long id) {
        Processo processo = getProcessoById(id);
        repository.delete(processo);
    }
}
