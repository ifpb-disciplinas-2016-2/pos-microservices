package ifpb.ads.produto;

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

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 14:28:38
 */
@Path("produto")
@Stateless
public class ProdutoResources {

    @EJB
    private Produtos service;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProdutos() {
        List<Produto> listar = service.listar();
        GenericEntity<List<Produto>> retorno = new GenericEntity<List<Produto>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    @GET
    @Path("valida/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validar(@PathParam("id") int id) {
        Produto produto = service.buscar(id);
        
        // para o tratamento de erro vamos utilizar o código do Response
        if(produto==null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        
        return Response.ok().build();
    }
}
