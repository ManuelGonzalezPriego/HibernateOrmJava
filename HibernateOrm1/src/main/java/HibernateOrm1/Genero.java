package HibernateOrm1;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*EN la BD musica, creamos otra tabla de genero musical y creamos varios registros como "pop", "rock","flamenco",...) con su ID.

En la tb de cantantes  asignamos la FK del genero musical que le corresponda.*/

@Entity
@Table(name = "genero")
public class Genero {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "genero", unique = true,nullable = false)
	private String genero;
	
	@OneToMany(mappedBy = "genero", cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<Cantante> canstantes;

	public Genero(String genero) {
		this.genero = genero;
	}
	
	public Genero() {
		
	}

	@Override
	public String toString() {
		return "Genero Id=" + id + ", genero=" + genero + ".";
	}
	
	

}
