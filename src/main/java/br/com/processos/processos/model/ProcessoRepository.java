package br.com.processos.processos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    Page<Processo> findAll(Pageable pageable);
}
