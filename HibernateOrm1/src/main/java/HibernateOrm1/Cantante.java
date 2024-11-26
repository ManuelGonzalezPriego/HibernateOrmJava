package HibernateOrm1;

//codigo (int), nombre cantante (string), año (int) , album (string)

import jakarta.persistence.*; 
import java.time.LocalDate;

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
	
	@ManyToOne
	@JoinColumn(name = "genero_id")
	private Genero genero;
	
	public Cantante(String nombre, LocalDate ano,String album,Genero genero) {
		this.nombre = nombre;
		this.ano = ano;
		this.album=album;
		this.genero=genero;
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

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return nombre + ", ano=" + ano + ", album=" + album + ", genero="+ genero;
	}
	
}

