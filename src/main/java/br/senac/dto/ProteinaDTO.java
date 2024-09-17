package br.senac.dto;

public class ProteinaDTO {

        private int id;
        private String nome;
        private String descricao;
        private String img64;

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
