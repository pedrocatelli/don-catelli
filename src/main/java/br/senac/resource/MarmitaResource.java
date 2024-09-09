
package br.senac.resource;


import br.senac.dto.ComboDTO;
import br.senac.dto.MarmitaDTO;
import br.senac.dto.ProteinaDTO;
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

@Path("/marmita")
@Tag(name = "Marmita Resource", description = "Endpoints para gerenciar marmita")
public class MarmitaResource {

    @Inject
    MarmitaService marmitaService;

    @Inject
    ComboService comboService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar uma nova Marmita")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Marmita criada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),

    })
    public Response Marmita(MarmitaDTO marmita) {
        try {
            marmitaService.createMarmita(marmita);
            return Response.status(Response.Status.CREATED).entity(marmita).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar uma Marmita existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Marmita atualizada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarMarmita(MarmitaDTO marmita) {
        try {
            marmitaService.updateMarmita(marmita);
            return Response.ok(marmita).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar uma Marmita pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Marmita deletada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarMarmita(@PathParam("id") int id) {
        try {
            marmitaService.deleteMarmita(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter uma Marmita pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Marmita obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Marmita não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterMarmitaPorId(@PathParam("id") int id) {
        try {
            MarmitaDTO marmita = marmitaService.getMarmitaById(id);
            if (marmita != null) {
                return Response.ok(marmita).build();
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
    @Operation(summary = "Obter todas as Marmita")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Marmita obtidas com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodasMarmita() {
        try {
            List<MarmitaDTO> marmita = marmitaService.getAllMarmitas();
            return Response.ok(marmita).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter o combo da Marmita")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Combo obtido com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterComboDaMarmita(@PathParam("id") int id) {
        try {
            ComboDTO combo = marmitaService.getComboByMarmitaId(id);
            return Response.ok(combo).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter o combo da Marmita")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Combo obtido com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterProteinaDaMarmita(@PathParam("id") int id) {
        try {
            ProteinaDTO proteina = marmitaService.getProteinaByMarmitaId(id);
            return Response.ok(proteina).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}