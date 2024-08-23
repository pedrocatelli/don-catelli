package br.senac.dto;

public class PedidoDTO {

    private int id;
    private  MarmitaDTO marmita;
    private  PagamentoDTO pagamento;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PagamentoDTO getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoDTO pagamento) {
        this.pagamento = pagamento;
    }

    public MarmitaDTO getMarmita() {
        return marmita;
    }

    public void setMarmita(MarmitaDTO marmita) {
        this.marmita = marmita;
    }

    public void setPagamento(Object pagamento) {
    }
}
