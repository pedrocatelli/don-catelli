package br.senac.dto;

import java.util.List;

public class PedidoRequest {
    private PessoaDTO pessoa;
    private List<MarmitaDTO> marmitas;
    private PagamentoDTO pagamento;
    private PedidoDTO pedido;


    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public List<MarmitaDTO> getMarmitas() {
        return marmitas;
    }

    public void setMarmitas(List<MarmitaDTO> marmitas) {
        this.marmitas = marmitas;
    }

    public PagamentoDTO getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoDTO pagamento) {
        this.pagamento = pagamento;
    }


    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }
}
