package br.senac.dto;

import java.util.ArrayList;

public class PessoaDTO {
    private int id;
    private String nome;
    private String email;
    private int cpf;
    private int telefone;
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

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public ArrayList<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(ArrayList<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
