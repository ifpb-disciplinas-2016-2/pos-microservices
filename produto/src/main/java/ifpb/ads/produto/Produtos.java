package ifpb.ads.produto;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 14:19:03
 */
//@DataSourceDefinition(
//        name = "java:app/jdbc/produto",
//        className = "org.postgresql.Driver",
//        url = "jdbc:postgresql://postgres:5433/produto",
//        user = "postgres",
//        password = "12345")

@Stateless
//@Startup
public class Produtos {

    @PersistenceContext
    private EntityManager em;

    public Produto salvar(Produto produto) {
        em.persist(produto);
        return produto;

    }

    public Produto remover(int key) {
        Produto produto = this.buscar(key);
        em.remove(produto);
        return produto;
    }

    public Produto atualizar(Produto produto) {
        return em.merge(produto);
    }

    public Produto buscar(int key) {
        return em.find(Produto.class, key);
    }

    public List<Produto> listar() {
        return em.createQuery("FROM Produto p", Produto.class)
                .getResultList();
    }
}
