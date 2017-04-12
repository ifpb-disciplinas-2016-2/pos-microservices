package ifpb.ads.cliente;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 14:28:38
 */
@Path("cliente")
@Stateless
public class ClienteResources {

    @EJB
    private Clientes service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProdutos() {
        List<Cliente> listar = service.listar();
        GenericEntity<List<Cliente>> retorno
                = new GenericEntity<List<Cliente>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    @GET
    @Path("/valida/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validar(@PathParam("id") int id) {
        Cliente cliente = service.buscar(id);
        
        // para o tratamento de erro vamos utilizar o código do Response
        if(cliente==null){
            return Response.status(Status.NO_CONTENT).build();
        }
        
        return Response.ok().build();
    }
}
