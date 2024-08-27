package br.senac.resource;
import br.senac.dto.ComboDTO;
import br.senac.service.ComboService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.models.media.MediaType;
import java.sql.SQLException;


@Path("/combo")
@Tag(name = "Combo Resource", description = "Endpoints para gerenciar combos")
public class ComboResource {

    @Inject
    ComboService comboService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar um novo combo")
    @APIResponse({
            @APIResponse(responseCode = "201", description = "Combo criado  com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")

    })

    public Response obterComboPorId(@PathParam("id") int id) {
        try {
            ComboDTO combo = comboService.getComboById(id);
            return combo != null ? Response.ok(combo).build() : Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
    }

    @PUT

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar um Combo existente")
    @APIResponse({
            @APIResponse(responseCode = "200", description = "Combo atualizado com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")

    })

    public Response atualizarCombo(ComboDTO combo){
        try {
            comboService.updateComobo(combo);
            return combo != null ? Response.ok(combo).build() : Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

}
