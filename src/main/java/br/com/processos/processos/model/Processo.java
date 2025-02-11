package br.com.processos.processos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String npu;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataVisualizacao;
    private String municipio;
    private String uf;
    private String documentoPath;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNpu() {
        return npu;
    }

    public void setNpu(String npu) {
        this.npu = npu;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataVisualizacao() {
        return dataVisualizacao;
    }

    public void setDataVisualizacao(LocalDateTime dataVisualizacao) {
        this.dataVisualizacao = dataVisualizacao;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDocumentoPath() {
        return documentoPath;
    }

    public void setDocumentoPath(String documentoPath) {
        this.documentoPath = documentoPath;
    }
}