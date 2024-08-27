package br.senac.dto;

public class MarmitaDTO {
    private int id;
    private String nome;
    private String acompanhamentos;
    private ProteinaDTO protenia;
    private ComboDTO combo;

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

    public String getAcompanhamentos() {
        return acompanhamentos;
    }

    public void setAcompanhamentos(String acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
    }

    public ProteinaDTO getProtenia() {
        return protenia;
    }

    public void setProtenia(ProteinaDTO protenia) {
        this.protenia = protenia;
    }

    public ComboDTO getCombo() {
        return combo;
    }

    public void setCombo(ComboDTO combo) {
        this.combo = combo;
    }
}