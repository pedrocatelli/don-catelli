package br.senac.dto;

import br.senac.dto.UfDTO;

import java.util.ArrayList;
import java.util.Date;

public class PessoaDTO {
    private int id;
    private String nome;
    private String email;
    private Date nascimento;
    private UfDTO uf;
    private ArrayList<EnderecoDTO> enderecos;
    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public UfDTO getUf() {
        return uf;
    }

    public void setUf(UfDTO uf) {
        this.uf = uf;
    }

    public ArrayList<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(ArrayList<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
