import br.senac.dto.ProteinaDTO;;
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

@Path("/proteina")
@Tag(name = "Proteina Resource", description = "Endpoints para gerenciar proteina")
public class ProteinaResource {

    @Inject
    ProteinaService proteinaService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar uma nova proteina")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Proteina criada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),

    })
    public Response cadastrarProteina(ProteinaDTO proteina) {
        try {
            proteinaService.createProteina(proteina);
            return Response.status(Response.Status.CREATED).entity(proteina).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar uma proteina existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Proteina atualizada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarProteina(ProteinaDTO proteina) {
        try {
            proteinaService.updateProteina(proteina);
            return Response.ok(proteina).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar uma Proteina pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Proteina deletada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarProteina(@PathParam("id") int id) {
        try {
            proteinaService.deleteProteina(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter uma Proteina pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Proteina obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Proteina não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterProteinaPorId(@PathParam("id") int id) {
        try {
            ProteinaDTO proteina = proteinaService.getProteinaById(id);
            if (proteina != null) {
                return Response.ok(proteina).build();
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
    @Operation(summary = "Obter todas as Proteina")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Proteina obtidas com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodasProteina() {
        try {
            List<ProteinaDTO> proteina = proteinaService.getAllProteina();
            return Response.ok(proteina).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }