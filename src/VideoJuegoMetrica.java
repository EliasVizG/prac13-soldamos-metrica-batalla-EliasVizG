
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class VideoJuegoMetrica {
	static Random rd = new Random();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		HashMap<Integer, Soldado> ejercito1 = new HashMap<Integer, Soldado>();
		HashMap<Integer, Soldado> ejercito2 = new HashMap<Integer, Soldado>();
		int maxSoldados = 20;
		int n = rd.nextInt(maxSoldados) + 1;
		for1: for (int i = 0; i < n; i++) {
			String nombre = "Soldado1x" + (i + 1);
			int fila = rd.nextInt(10), columna = rd.nextInt(10), nivelVida = rd.nextInt(10) + 1,
					nivelAtaque = rd.nextInt(10) + 1, nivelDefensa = rd.nextInt(10) + 1;
			for (Integer key : ejercito1.keySet()) {
				if (ejercito1.get(key).getColumna() == columna && ejercito1.get(key).getFila() == fila) {
					i--;
					continue for1;
				}
			}
			ejercito1.put(i, new Soldado(nombre, fila, columna, nivelAtaque, nivelDefensa, nivelVida));
		}

		n = rd.nextInt(maxSoldados) + 1;
		for2: for (int i = 0; i < n; i++) {
			String nombre = "Soldado2x" + (i + 1);
			int fila = rd.nextInt(10), columna = rd.nextInt(10), nivelVida = rd.nextInt(10) + 1,
					nivelAtaque = rd.nextInt(10) + 1, nivelDefensa = rd.nextInt(10) + 1;
			for (Integer key : ejercito1.keySet()) {
				if (ejercito1.get(key).getColumna() == columna && ejercito1.get(key).getFila() == fila) {
					i--;
					continue for2;
				}
			}
			for (Integer key : ejercito2.keySet()) {
				if (ejercito2.get(key).getColumna() == columna && ejercito2.get(key).getFila() == fila)
					i--;
				continue;
			}

			ejercito2.put(i, new Soldado(nombre, fila, columna, nivelAtaque, nivelDefensa, nivelVida));
		}

		System.out.println("EJERCITO 1");
		mostrarSoldadoMayorVida(ejercito1);
		mostrarPromedioPuntosVida(ejercito1);
		System.out.println(">>Puntos totales: " + puntosTotales(ejercito1) + "\n");
		mostrarEjercito(OrdenamientoNombreBurbuja(ejercito1));
		System.out.println("MOSTRANDO ORDENAMIENTO BURBUJAS");
		mostrarEjercito(OrdenamientoPuntosBurbuja(ejercito1));
		System.out.println("EJERCITO 2");
		mostrarSoldadoMayorVida(ejercito2);
		mostrarPromedioPuntosVida(ejercito2);
		System.out.println(">>Puntos totales: " + puntosTotales(ejercito2) + "\n");
		mostrarEjercito(OrdenamientoNombreBurbuja(ejercito2));
		System.out.println("MOSTRANDO ORDENAMIENTO SELECCION");
		mostrarEjercito(OrdenamientoPuntosSeleccion(ejercito2));
		mostrarTablero(mezclarEjercitos(ejercito1, ejercito2));

		while (ejercitoVive(ejercito1) && ejercitoVive(ejercito2)) {
			System.out.println(">>Turno jugador 1");
			turno(ejercito1, ejercito2);
			mostrarTablero(mezclarEjercitos(ejercito1, ejercito2));
			if (ejercitoVive(ejercito1) && ejercitoVive(ejercito2)) {
				System.out.println(">>Turno jugador 2");
				turno(ejercito2, ejercito1);
				mostrarTablero(mezclarEjercitos(ejercito1, ejercito2));
			} else
				break;
		}
		if (ejercitoVive(ejercito1)) {
			System.err.println("EL GANADOR ES EL EJERCITO 1!!!");
		} else if (!ejercitoVive(ejercito1) && !ejercitoVive(ejercito2)) {
			System.err.println("ES UN EMPATE!!!");
		} else
			System.err.println("EL GANADOR ES EL EJERCITO 2!!!");
	}

	public static boolean ejercitoVive(HashMap<Integer, Soldado> ejercito) {
		for (Integer key : ejercito.keySet()) {
			if (ejercito.get(key).getVive())
				return true;
		}
		return false;
	}

	public static int turno(HashMap<Integer, Soldado> ejercitoAtacante, HashMap<Integer, Soldado> ejercitoAtacado) {
		System.out.print("Ingrese la fila del soldado: ");
		int fila = sc.nextInt() - 1;
		System.out.print("Ingrese la columna del soldado: ");
		int columna = sc.nextInt() - 1;
		boolean soldadoExiste = false;
		int clave = -1;
		for (Integer key : ejercitoAtacante.keySet()) {
			if (ejercitoAtacante.get(key).getColumna() == columna && ejercitoAtacante.get(key).getFila() == fila) {
				soldadoExiste = true;
				clave = key;
				break;
			}
		}
		if (soldadoExiste) {
			System.out.println("Escoja la direccion de movimiento:\n1. arriba\t2. abajo\t3. izquierda\t4. derecha");
			int movimiento = sc.nextInt();
			if (movimientoPermitido(ejercitoAtacante.get(clave), movimiento, ejercitoAtacante))
				mover(ejercitoAtacante.get(clave), movimiento, ejercitoAtacante);
			else {
				System.out.println(">>Movimiento no permitido, intentelo de nuevo");
				turno(ejercitoAtacante, ejercitoAtacado);
				return -1;
			}
			for (Integer key : ejercitoAtacado.keySet())
				if (ejercitoAtacado.get(key).getColumna() == ejercitoAtacante.get(clave).getColumna()
						&& ejercitoAtacado.get(key).getFila() == ejercitoAtacante.get(clave).getFila())
					ejercitoAtacante.get(clave).atacar(ejercitoAtacado.get(key));
		} else {
			System.out.println(">>Soldado no encontrado, intentelo de nuevo");
			turno(ejercitoAtacante, ejercitoAtacado);
			return -1;
		}
		return -1;
	}

	public static void mover(Soldado soldado, int movimiento, HashMap<Integer, Soldado> ejercito) {
		switch (movimiento) {
		case 1:
			soldado.setFila(soldado.getFila() - 1);
			break;
		case 2:
			soldado.setFila(soldado.getFila() + 1);
			break;
		case 3:
			soldado.setColumna(soldado.getColumna() - 1);
			break;
		case 4:
			soldado.setColumna(soldado.getColumna() + 1);
			break;
		}
	}

	public static boolean movimientoPermitido(Soldado soldado, int movimiento, HashMap<Integer, Soldado> ejercito) {
		switch (movimiento) {
		case 1:
			if (soldado.getFila() - 1 < 0)
				return false;
			for (Integer key : ejercito.keySet())
				if (ejercito.get(key).getColumna() == soldado.getColumna()
						&& ejercito.get(key).getFila() == soldado.getFila() - 1)
					return false;
			break;
		case 2:
			if (soldado.getFila() + 1 > 9)
				return false;
			for (Integer key : ejercito.keySet())
				if (ejercito.get(key).getColumna() == soldado.getColumna()
						&& ejercito.get(key).getFila() == soldado.getFila() + 1)
					return false;
			break;
		case 3:
			if (soldado.getColumna() - 1 < 0)
				return false;
			for (Integer key : ejercito.keySet())
				if (ejercito.get(key).getColumna() == soldado.getColumna() - 1
						&& ejercito.get(key).getFila() == soldado.getFila())
					return false;
			break;
		case 4:
			if (soldado.getColumna() + 1 > 9)
				return false;
			for (Integer key : ejercito.keySet())
				if (ejercito.get(key).getColumna() == soldado.getColumna() + 1
						&& ejercito.get(key).getFila() == soldado.getFila())
					return false;
			break;
		}
		return true;
	}

	public static Soldado[][] mezclarEjercitos(HashMap<Integer, Soldado> ejercito1,
			HashMap<Integer, Soldado> ejercito2) {
		Soldado[][] ejercitos = new Soldado[10][10];

		for (Integer key : ejercito1.keySet())
			if (ejercito1.get(key).getVive())
				ejercitos[ejercito1.get(key).getFila()][ejercito1.get(key).getColumna()] = ejercito1.get(key);
		for (Integer key : ejercito2.keySet())
			if (ejercito2.get(key).getVive())
				ejercitos[ejercito2.get(key).getFila()][ejercito2.get(key).getColumna()] = ejercito2.get(key);

		return ejercitos;
	}

	public static void mostrarTablero(Soldado[][] ejercito) {
		for (int i = 0; i <= 10; i++) {
			System.out.print("\t" + i + "\t");
		}
		System.out.println();
		for (int i = 0; i < ejercito.length; i++) {
			System.out.print("\t" + (i + 1) + "\t");
			for (int j = 0; j < ejercito[i].length; j++) {
				if (ejercito[i][j] != null && ejercito[i][j].getVive()) {
					System.out.print("[  " + ejercito[i][j].getNombre() + "\t]");
				} else
					System.out.print("[\t\t]");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void mostrarSoldadoMayorVida(HashMap<Integer, Soldado> ejercito) {
		System.out.println(">>Soldado con mayor vida:<<");
		int max = 0;
		for (Integer i : ejercito.keySet()) {
			if (ejercito.get(i).getNivelVida() > max)
				max = ejercito.get(i).getNivelVida();
		}
		for (Integer i : ejercito.keySet()) {
			if (ejercito.get(i).getNivelVida() == max)
				ejercito.get(i).mostrarSoldado();
		}
		System.out.println();
	}

	public static void mostrarPromedioPuntosVida(HashMap<Integer, Soldado> ejercito) {
		int suma = 0;
		int conteo = 0;
		for (Integer key : ejercito.keySet()) {
			suma += ejercito.get(key).getNivelVida();
			conteo++;
		}
		double promedioTotal = 1.0 * suma / conteo;
		System.out.println(">>Promedio de puntos de vida: " + promedioTotal);
		System.out.println();
	}

	public static int puntosTotales(HashMap<Integer, Soldado> ejercito) {
		int suma = 0;
		for (Integer key : ejercito.keySet()) {
			suma += ejercito.get(key).getNivelVida() + ejercito.get(key).getNivelAtaque()
					+ ejercito.get(key).getNivelDefensa();
		}
		return suma;
	}

	public static void mostrarEjercito(ArrayList<Soldado> ejercito) {
		System.out.println(">>Mostrando Soldados... ");
		for (int i = 0; i < ejercito.size(); i++) {
			ejercito.get(i).mostrarSoldado();
		}
		System.out.println();
	}

	public static ArrayList<Soldado> OrdenamientoNombreBurbuja(HashMap<Integer, Soldado> ejercito) {
		ArrayList<Soldado> copia = new ArrayList<Soldado>();
		for (Integer key : ejercito.keySet())
			copia.add(ejercito.get(key));

		int tamaño = copia.size();
		int fin = tamaño - 1;
		boolean sorted = false;
		for (int i = 0; i < tamaño - 1 && !sorted; i++) {
			sorted = true;
			for (int j = 0; j < fin; j++) {
				Soldado s1 = copia.get(j);
				Soldado s2 = copia.get(j + 1);
				if (s1.getNombre().compareTo(s2.getNombre()) > 0) {
					sorted = false;
					copia.remove(j);
					copia.add(j + 1, s1);
				}
			}
			fin--;
		}
		return copia;
	}

	public static ArrayList<Soldado> OrdenamientoPuntosBurbuja(HashMap<Integer, Soldado> ejercito) {
		ArrayList<Soldado> copia = new ArrayList<Soldado>();
		for (Integer key : ejercito.keySet())
			copia.add(ejercito.get(key));

		int tamaño = copia.size();
		int fin = tamaño - 1;
		boolean sorted = false;
		for (int i = 0; i < tamaño - 1 && !sorted; i++) {
			sorted = true;
			for (int j = 0; j < fin; j++) {
				Soldado s1 = copia.get(j);
				Soldado s2 = copia.get(j + 1);
				if (s1.getNivelVida() < s2.getNivelVida()) {
					sorted = false;
					copia.remove(j);
					copia.add(j + 1, s1);
				}
			}
			fin--;
		}
		return copia;
	}

	public static ArrayList<Soldado> OrdenamientoPuntosSeleccion(HashMap<Integer, Soldado> ejercito) {
		ArrayList<Soldado> copia = new ArrayList<Soldado>();
		int n = 0;
		for (Integer key : ejercito.keySet()) {
			copia.add(ejercito.get(key));
			n++;
		}
		for (int i = 0; i < n - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < n; j++)
				if (copia.get(j).getNivelVida() > copia.get(minIndex).getNivelVida())
					minIndex = j;
			Soldado temp = copia.get(minIndex);
			copia.set(minIndex, copia.get(i));
			copia.set(i, temp);
		}
		return copia;
	}
}