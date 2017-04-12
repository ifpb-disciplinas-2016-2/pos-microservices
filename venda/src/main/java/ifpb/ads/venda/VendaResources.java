package ifpb.ads.venda;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 14:28:38
 */
@Path("venda")
@Stateless
public class VendaResources {

    @EJB
    private Vendas service;
    private Client client = ClientBuilder.newClient();
    private WebTarget cliente = client.target("http://app-cliente:8080/cliente/api/cliente");
    private WebTarget produto = client.target("http://app-produto:8080/produto/api/produto");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarVendas() {
        List<Venda> listar = service.listar();
        GenericEntity<List<Venda>> retorno
                = new GenericEntity<List<Venda>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vendaComId(@PathParam("id") int id) {
        
        Venda venda = service.buscar(id);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (venda==null) {
            builder.add("status", "erro");
            builder.add("msg", "venda não encontrado");
            return Response.ok(builder.build()).build();
        }
        
        Link link = Link.fromUri("{id}")
                .baseUri(cliente.getUri())
                .rel("cliente")
                .build(venda.getCliente());
        
        return Response.ok(link.toString()).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response newOrder(@Context UriInfo uriInfo) throws URISyntaxException {
        Venda venda = new Venda();
        service.salvar(venda);
        String id = String.valueOf(venda.getId());
        URI uriOrder = uriInfo.getBaseUriBuilder() // .../ws/
                .path(VendaResources.class) // .../ws/order
                .path(id) // .../ws/order/1
                .build();
        return Response
                .created(uriOrder)
                .entity(venda)
                .build();
    }

    @PUT
    @Path("{idOrder}/cliente/{idClient}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addClientInOrder(
            @PathParam("idOrder") int idOrder,
            @PathParam("idClient") int idClient) {

        boolean clienteValido = validarCliente(idClient);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (!clienteValido) {
            builder.add("status", "erro");
            builder.add("msg", "cliente não encontrado");
            return Response.ok(builder.build()).build();
        }

        Venda order = service.buscar(idOrder);
        //solicitar ao serviço de cliente
        order.setCliente(idClient);
        service.atualizar(order);

        builder.add("status", "Ok");
        builder.add("msg", "venda atualizada");

        return Response
                .ok()
                .entity(builder.build())
                .build();
    }
    @PUT
    @Path("{idOrder}/produto/{idProduto}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addProductInOrder(
            @PathParam("idOrder") int idOrder,
            @PathParam("idProduto") int idProduto) {

        boolean produtoValido = validarProduto(idProduto);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (!produtoValido) {
            builder.add("status", "erro");
            builder.add("msg", "produto não encontrado");
            return Response.ok(builder.build()).build();
        }

        Venda order = service.buscar(idOrder);
        //solicitar ao serviço de cliente
        order.addProduto(idProduto);
        
        service.atualizar(order);

        builder.add("status", "Ok");
        builder.add("msg", "venda atualizada");

        return Response
                .ok()
                .entity(builder.build())
                .build();
    }

    private boolean validarCliente(int idClient) {
        WebTarget targ = cliente
                .path("valida")
                .path("{id}");

        Response response = targ
                .resolveTemplate("id", idClient)
                .request()
                .get();

        return response.getStatus() == Status.OK.getStatusCode();

    }

    private boolean validarProduto(int idProduto) {
       WebTarget targ = produto
                .path("valida")
                .path("{id}");

        Response response = targ
                .resolveTemplate("id", idProduto)
                .request()
                .get();

        return response.getStatus() == Status.OK.getStatusCode();
    }
}
