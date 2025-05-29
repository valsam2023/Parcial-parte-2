package torneo;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<int[]> emparejamientos = List.of(
                new int[]{1, 16},
                new int[]{2, 15},
                new int[]{3, 14},
                new int[]{4, 13},
                new int[]{5, 12},
                new int[]{6, 11},
                new int[]{7, 10},
                new int[]{8, 9}
        );

        ExecutorService executor = Executors.newFixedThreadPool(4);
        emparejamientos = jugarRonda(emparejamientos, "OCTAVOS DE FINAL", executor);

        int ronda = 1;
        while (emparejamientos.size() > 1) {
            String nombreRonda = switch (emparejamientos.size()) {
                case 4 -> "CUARTOS DE FINAL";
                case 2 -> "SEMIFINAL";
                case 1 -> "FINAL";
                default -> "RONDA " + (++ronda);
            };

            emparejamientos = jugarRonda(emparejamientos, nombreRonda, executor);
        }

        System.out.println("\n🏆 ¡Campeón del torneo: Jugador " + emparejamientos.get(0)[0] + "!");
        executor.shutdown();
    }

    public static List<int[]> jugarRonda(List<int[]> emparejamientos, String nombreRonda, ExecutorService executor) throws InterruptedException, ExecutionException {
        System.out.println("\n===== " + nombreRonda + " =====");
        List<Future<String>> resultados = new ArrayList<>();
        List<int[]> ganadores = new ArrayList<>();

        for (int[] par : emparejamientos) {
            Future<String> resultado = executor.submit(new Partido(par[0], par[1]));
            resultados.add(resultado);
        }

        for (int i = 0; i < resultados.size(); i++) {
            String salida = resultados.get(i).get();
            System.out.print(salida);
            int ganador = Integer.parseInt(salida.split("Ganador del partido: Jugador ")[1].trim());
            ganadores.add(new int[]{ganador});
        }

        List<int[]> siguienteRonda = new ArrayList<>();
        for (int i = 0; i < ganadores.size(); i += 2) {
            if (i + 1 < ganadores.size()) {
                siguienteRonda.add(new int[]{ganadores.get(i)[0], ganadores.get(i + 1)[0]});
            }
        }

        return siguienteRonda;
    }
}
