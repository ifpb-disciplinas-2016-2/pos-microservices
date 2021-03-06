package ifpb.ads.produto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 14:17:40
 */
@XmlRootElement
@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String description;

    public Produto() {
    }

    public Produto(String name, String description) {
        this.nome = name;
        this.description = description;
    }

    public Produto(int id, String name, String description) {
        this.id = id;
        this.nome = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", name=" + nome + ", description=" + description + '}';
    }
}
