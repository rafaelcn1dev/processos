package br.com.processos.processos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoService {
    @Autowired
    private ProcessoRepository repository;

    public Processo saveProcesso(Processo processo) {
        return repository.save(processo);
    }

    public List<Processo> getAllProcessos() {
        return repository.findAll();
    }

    public Processo getProcessoById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo not found with id " + id));
    }
}
