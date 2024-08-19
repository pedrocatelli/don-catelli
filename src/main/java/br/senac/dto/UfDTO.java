package br.senac.dto;

public class UfDTO {
    private int id;
    private String sgUf;
    private String nmUf;
    //criar getters e setters

    public UfDTO() {
    }

    public String getNmUf() {
        return nmUf;
    }

    public void setNmUf(String nmUf) {
        this.nmUf = nmUf;
    }

    public String getSgUf() {
        return sgUf;
    }

    public void setSgUf(String sgUf) {
        this.sgUf = sgUf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
