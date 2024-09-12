package br.senac.resource;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PessoaDTO;
import br.senac.service.PagamentoService;
import br.senac.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.sql.SQLException;
import java.util.List;

@Path("/pagamentos")
@Tag(name = "Pagamento Resource", description = "Endpoints para gerenciar pagamentos")
public class PagamentoResource {
    @Inject
    PagamentoService pagamentoService;

    @Inject
    PessoaService pessoaService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar uma nova Pagamento")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Pagamento criada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),

    })
    public Response cadastrarPagamento(PagamentoDTO pagamento) {
        try {
            pagamentoService.createPagamento(pagamento);
            return Response.status(Response.Status.CREATED).entity(pagamento).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar um pagamento existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarPagamento(PagamentoDTO pagamento) {
        try {
            pagamentoService.updatePagamento(pagamento);
            return Response.ok(pagamento).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar uma Pagamento pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Pagamento deletado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarPagamento(@PathParam("id") int id) {
        try {
            pagamentoService.deletePagamento(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter uma Pagamento pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pagamento obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Pagamento não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterPagamentoPorId(@PathParam("id") int id) {
        try {
            PagamentoDTO pagamento = pagamentoService.getPagamentoById(id);
            if (pagamento != null) {
                return Response.ok(pagamento).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter todos os pagamentos")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pagamentos obtidos com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodasPagamentos() {
        try {
            List<PagamentoDTO> pagamentos = pagamentoService.getAllPagamentos();
            return Response.ok(pagamentos).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/pessoa")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter Pessoa do Pagamento pelo PagamentoId")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pessoa obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterPessoaDoPagamentoPorId(@PathParam("id") int id) {
        try {
            PagamentoDTO pagamento = pagamentoService.getPagamentoById(id);
            if (pagamento != null) {
                PessoaDTO pessoa = pessoaService.getPessoaById(pagamento.getPessoa().getId());
                return Response.ok(pessoa).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/endereco")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter Endereco do Pagamento pelo PagamentoId")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Endereco(s) obtido(s) com sucesso"),
            @APIResponse(responseCode = "404", description = "Endereco(s) não encontrado(s)"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterEnderecoDoPagamentoPorId(@PathParam("id") int id) {
        try {
            PagamentoDTO pagamento = pagamentoService.getPagamentoById(id);
            if (pagamento != null) {
                PessoaDTO pessoa = pessoaService.getPessoaById(pagamento.getPessoa().getId());
                List<EnderecoDTO> enderecos = pessoaService.buscarEnderecos(pessoa.getId());
                return Response.ok(enderecos).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
