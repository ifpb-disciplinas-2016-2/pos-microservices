package ifpb.ads.venda;

import java.util.List;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 03/04/2017, 08:19:14
 */
@DataSourceDefinition(
        name = "java:app/jdbc/venda",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://banco-venda:5432/venda",
        user = "postgres",
        password = "12345")
@Stateless
public class Vendas {

    @PersistenceContext
    private EntityManager em;

    public Venda salvar(Venda venda) {
        em.persist(venda);
        return venda;

    }

    public Venda remover(int key) {
        Venda venda = this.buscar(key);
        em.remove(venda);
        return venda;
    }

    public Venda atualizar(Venda venda) {
        return em.merge(venda);
    }

    public Venda buscar(int key) {
        return em.find(Venda.class, key);
    }

    public List<Venda> listar() {
        return em.createQuery("FROM Venda p", Venda.class)
                .getResultList();
    }
}
