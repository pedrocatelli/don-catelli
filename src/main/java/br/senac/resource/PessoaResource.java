package br.senac.resource;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PessoaDTO;
import br.senac.dto.UfDTO;
import br.senac.exceptions.PessoaNaoEncontradaException;
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

@Path("/pessoas")
@Tag(name = "Pessoa Resource", description = "Endpoints para gerenciar pessoas")
public class PessoaResource {

    @Inject
    PessoaService pessoaService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar uma nova Pessoa")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),

    })
    public Response cadastrarPessoa(PessoaDTO pessoa) {
        try {
            pessoaService.createPessoa(pessoa);
            return Response.status(Response.Status.CREATED).entity(pessoa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar uma Pessoa existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response atualizarPessoa(PessoaDTO pessoa) {
        try {
            pessoaService.updatePessoa(pessoa);
            return Response.ok(pessoa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletar uma Pessoa pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response deletarPessoa(@PathParam("id") int id) {
        try {
            pessoaService.deletePessoa(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter uma Pessoa pelo ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pessoa obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Pessoa não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterPessoaPorId(@PathParam("id") int id) {
        try {
            PessoaDTO pessoa = pessoaService.getPessoaById(id);
            if (pessoa != null) {
                return Response.ok(pessoa).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obter todas as Pessoas")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Pessoas obtidas com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Response obterTodasPessoas() {
        try {
            List<PessoaDTO> pessoas = pessoaService.getAllPessoas();
            return Response.ok(pessoas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/enderecos")
    @Operation(summary = "Obter todos os endereços de uma Pessoa")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Endereços obtidos com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),
            @APIResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public Response obterTodasPessoas(@PathParam("id") int id) throws Exception{
        try {
            List<EnderecoDTO> enderecos = pessoaService.buscarEnderecos(id);
            return Response.ok(enderecos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (PessoaNaoEncontradaException pne){
            return Response.status(Response.Status.NOT_FOUND).entity("Pessoa não existe!").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Criar um novo endereço para a pessoa")
    @Path("/{id}/enderecos")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @APIResponse(responseCode = "500", description = "Erro interno do servidor"),

    })
    public Response cadastrarEnderecoParaPessoa(@PathParam("id") int id, EnderecoDTO endereco) throws Exception{
        try {
            pessoaService.createEndereco(id, endereco);
            return Response.status(Response.Status.CREATED).entity(endereco).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (PessoaNaoEncontradaException pne){
            return Response.status(Response.Status.NOT_FOUND).entity("Pessoa não existe!").build();
        }
    }

}
