package HibernateOrm1;

//codigo (int), nombre cantante (string), año (int) , album (string)

import jakarta.persistence.*; 
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "cantante")
public class Cantante {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "ano", nullable = false)
	private LocalDate ano;

	@Column(name = "album", nullable = false)
	private String album;
	
	public Cantante(int codigo,String nombre, LocalDate ano,String album) {
		this.codigo=codigo;
		this.nombre = nombre;
		this.ano = ano;
		this.album=album;
	}
	
	public Cantante(String nombre, LocalDate ano,String album) {
		this.nombre = nombre;
		this.ano = ano;
		this.album=album;
	}
	
	public Cantante() {
		
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getAno() {
		return ano;
	}

	public void setAno(LocalDate ano) {
		this.ano = ano;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return nombre + ", ano=" + ano + ", album=" + album + ".";
	}
	
}

