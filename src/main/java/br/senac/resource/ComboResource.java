package br.senac.resource;

import br.senac.dto.ComboDTO;
import br.senac.dto.MarmitaDTO;
import br.senac.dto.PessoaDTO;
import br.senac.dto.ProteinaDTO;
import br.senac.exceptions.PessoaNaoEncontradaException;
import br.senac.service.ComboService;
import br.senac.service.MarmitaService;
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

@Path("/combos")
@Tag(name = "Combo Resource", description = "Endpoints para gerenciar combos")
public class ComboResource {

    @Inject
    ComboService comboService;

    @Inject
    MarmitaService marmitaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar um novo Combo")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Combo criado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),
    })
    public Response cadastrarCombo(ComboDTO combo) {
        try {
            comboService.createCombo(combo);
            return Response.status(Response.Status.CREATED).entity(combo).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar um Combo existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Combo atualizado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarCombo(ComboDTO combo) {
        try {
            comboService.updateCombo(combo);
            return Response.ok(combo).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar um Combo pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Combo deletado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarPessoa(@PathParam("id") int id) {
        try {
            comboService.deleteCombo(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter um Combo pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Combo obtido com sucesso"),
            @APIResponse(responseCode = "404", description = "Combo não encontrado"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterComboPorId(@PathParam("id") int id) {
        try {
            ComboDTO combo = comboService.getComboById(id);
            if (combo != null) {
                return Response.ok(combo).build();
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
    @Operation(summary = "Obter todos os Combos")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Combos obtidos com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodosCombos() {
        try {
            List<ComboDTO> combos = comboService.getAllCombos();
            return Response.ok(combos).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}