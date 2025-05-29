package torneo;

import java.util.Random;
import java.util.concurrent.Callable;

public class Partido implements Callable<String> {
    private final int jugador1;
    private final int jugador2;
    private final Random random = new Random();

    public Partido(int jugador1, int jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public String call() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador ").append(jugador1).append(" vs Jugador ").append(jugador2).append("\n");

        int setsJ1 = 0, setsJ2 = 0;

        for (int set = 1; set <= 3; set++) {
            Thread.sleep(1500 + random.nextInt(501));
            int ganadorSet = random.nextBoolean() ? jugador1 : jugador2;
            sb.append("Set ").append(set).append(": Jugador ").append(ganadorSet).append("\n");

            if (ganadorSet == jugador1) setsJ1++;
            else setsJ2++;

            if (setsJ1 == 2 || setsJ2 == 2) break;
        }

        int ganador = (setsJ1 > setsJ2) ? jugador1 : jugador2;
        sb.append("Ganador del partido: Jugador ").append(ganador).append("\n\n");

        return sb.toString();
    }
}
