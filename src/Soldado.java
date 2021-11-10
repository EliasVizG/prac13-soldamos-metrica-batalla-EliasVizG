import java.util.Random;

public class Soldado {
	private static Random rd = new Random();
	private String nombre;
	private int fila;
	private int columna;
	private int nivelAtaque;
	private int nivelDefensa;
	private int nivelVida;
	private int vidaActual;
	private int velocidad;
	private String actitud;
	private boolean vive;

	public Soldado(String nombre, int fila, int columna, int nivelAtaque, int nivelDefensa, int nivelVida,
			int velocidad, String actitud, boolean vive) {
		setNombre(nombre);
		setFila(fila);
		setColumna(columna);
		setNivelAtaque(nivelAtaque);
		setNivelDefensa(nivelDefensa);
		setNivelVida(nivelVida);
		setVidaActual(nivelVida);
		setVelocidad(velocidad);
		setActitud(actitud);
		setVive(vive);
	}

	public Soldado(String nombre, int fila, int columna, int nivelAtaque, int nivelDefensa, int nivelVida,
			int velocidad, String actitud) {
		this(nombre, fila, columna, nivelAtaque, nivelDefensa, nivelVida, velocidad, actitud, true);
	}

	public Soldado(String nombre, int fila, int columna, int nivelAtaque, int nivelDefensa, int nivelVida) {
		this(nombre, fila, columna, nivelAtaque, nivelDefensa, nivelVida, 0, "Indefinido");
	}

	public Soldado atacar(Soldado atacado) {
		actitud = "ofensiva";
		avanzar();
		int sumaVida = this.vidaActual + atacado.vidaActual;
		System.out.println("Probabilidad de " + this.vidaActual + " contra " + atacado.vidaActual + " de ganar");
		int probabilidad = rd.nextInt(sumaVida);
		if (probabilidad > this.vidaActual) {
			this.morir();
			atacado.setVidaActual(atacado.getVidaActual() + 1);
			System.out.println(">>Gana el soldado atacado");
		} else {
			atacado.morir();
			this.setVidaActual(this.vidaActual + 1);
			System.out.println(">>Gana el soldado atacante");
		}
		return this;
	}

	public Soldado defender() {
		actitud = "defensiva";
		velocidad = 0;
		return this;
	}

	public Soldado avanzar() {
		velocidad += 1;
		return this;
	}

	public Soldado retroceder() {
		if (velocidad > 0)
			defender();
		else
			velocidad -= 1;
		return this;
	}

	public Soldado serAtacado() {
		vidaActual -= rd.nextInt(3) + 1;
		if (vidaActual < 1)
			morir();
		return this;
	}

	public Soldado huir() {
		actitud = "fuga";
		velocidad += 2;
		return this;
	}

	public Soldado morir() {
		vidaActual = 0;
		vive = false;
		return this;
	}

	// setters
	public Soldado setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Soldado setFila(int fila) {
		this.fila = fila;
		return this;
	}

	public Soldado setColumna(int columna) {
		this.columna = columna;
		return this;
	}

	public Soldado setNivelAtaque(int nivelAtaque) {
		this.nivelAtaque = nivelAtaque;
		return this;
	}

	public Soldado setNivelDefensa(int nivelDefensa) {
		this.nivelDefensa = nivelDefensa;
		return this;
	}

	public Soldado setNivelVida(int nivelVida) {
		this.nivelVida = nivelVida;
		return this;
	}

	public Soldado setVidaActual(int vidaActual) {
		this.vidaActual = vidaActual;
		return this;
	}

	public Soldado setVelocidad(int velocidad) {
		this.velocidad = velocidad;
		return this;
	}

	public Soldado setActitud(String actitud) {
		this.actitud = actitud;
		return this;
	}

	public Soldado setVive(boolean vive) {
		this.vive = vive;
		return this;
	}

	// Metodos accesores
	public String getNombre() {
		return nombre;
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public int getNivelAtaque() {
		return nivelAtaque;
	}

	public int getNivelDefensa() {
		return nivelDefensa;
	}

	public int getNivelVida() {
		return nivelVida;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public String getActitud() {
		return actitud;
	}

	public boolean getVive() {
		return vive;
	}

	public void mostrarSoldado() {
		System.out.print("Nombre: " + nombre);
		System.out.print("\tFila: " + (fila + 1));
		System.out.print(" \tColumna: " + (columna + 1));
		System.out.print("\tNivel de ataque: " + nivelAtaque);
		System.out.print("\tNivel de defensa: " + nivelDefensa);
		System.out.print("\tNivel de vida: " + nivelVida);
		System.out.print("\tVida actual: " + vidaActual);
		System.out.print("\tVelocidad: " + velocidad);
		System.out.print("\tActitud: " + actitud);
		System.out.print("\tVive: " + vive);
		System.out.print("\n");
	}
}