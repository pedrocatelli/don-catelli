package br.senac.resource;

import br.senac.dto.MarmitaDTO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PedidoDTO;
import br.senac.service.MarmitaService;
import br.senac.service.PagamentoService;
import br.senac.service.PedidoService;
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

@Path("/pedidos")
@Tag(name = "Pedido Resource", description = "Endpoints para gerenciar pedidos")
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @Inject
    MarmitaService marmitaService;

    @Inject
    PagamentoService pagamentoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar um novo Pedido")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),
    })
    public Response cadastrarPedido(PedidoDTO pedido) {
        try {
            pedidoService.createPedido(pedido);
            return Response.status(Response.Status.CREATED).entity(pedido).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar um Pedido existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarPedido(PedidoDTO pedido) {
        try {
            pedidoService.updatePedido(pedido);
            return Response.ok(pedido).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar um Pedido pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarPessoa(@PathParam("id") int id) {
        try {
            pedidoService.deletePedido(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter um Pedido pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pedido obtido com sucesso"),
            @APIResponse(responseCode = "404", description = "Pedido não encontrado"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterPedidoPorId(@PathParam("id") int id) {
        try {
            PedidoDTO pedido = pedidoService.getPedidoById(id);
            if (pedido != null) {
                return Response.ok(pedido).build();
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
    @Operation(summary = "Obter todos os Pedidos")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pedidos obtidos com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodosPedidos() {
        try {
            List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
            return Response.ok(pedidos).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter marmita do Pedido")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Marmita obtida com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterMarmitaDoPedido(@PathParam("id") int id) {
        try {
            MarmitaDTO marmita = marmitaService.getMarmitaById(pedidoService.getPedidoById(id).getMarmita().getId());
            return Response.ok(marmita).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter pagamento do Pedido")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pagamento obtido com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterPagamentoDoPedido(@PathParam("id") int id) {
        try {
            PagamentoDTO pagamento = pagamentoService.getPagamentoById(pedidoService.getPedidoById(id).getPagamento().getId());
            return Response.ok(pagamento).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}