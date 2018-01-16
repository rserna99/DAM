import java.util.*;

/**
 * Simulador llei d'Hondt: Programa que gestiona l'assignació d'escanys en una votació
 * @author Roger Serna
 * @version 0.1
 */

public class eleccions {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        System.out.println("Numero d'escons:");
        int numEscons = sc.nextInt();

        System.out.println("Percentatge minim per obtenir representació:");
        int percentatgeMinim = sc.nextInt();

        System.out.println("Numero de partits politics:");
        int numPartits = sc.nextInt();

        String nomsPartits[] = new String[numPartits];
        int votsPartits[] = new int[numPartits];
        int votstsTotals = 0;

        int vots;

        for (int i = 0; i < numPartits; i++) {
            System.out.println("Introdueix el nom del partit polític:");
            nomsPartits[i] = sc.next();

            System.out.println("Introdueix el numero de vots de " + nomsPartits[i]);
            vots = sc.nextInt();
            votstsTotals += vots;
            votsPartits[i] = vots;

        }
        System.out.println("En total han hagut " + votstsTotals + " vots entre tots els partits");

        ArrayList<String> nomsPartitsRepresentacio = new ArrayList<>();
        ArrayList<Integer> votsPartitsRepresentacio = new ArrayList<>();
        int[] esconsPartits;



        // Descartar partits polítics sense posibilitat de representació
        float percentatge;
        System.out.println("LLISTA DE PARTITS QUE OPTEN A TENIR REPRESENTACIÓ");
        for (int i = 0; i < numPartits; i++) {
            percentatge = ((float)votsPartits[i] / (float)votstsTotals * 100);

            if (percentatge >= percentatgeMinim){
                nomsPartitsRepresentacio.add(nomsPartits[i]);
                votsPartitsRepresentacio.add(votsPartits[i]);

                System.out.println(nomsPartits[i] + " ha obtingut un total de " + votsPartits[i] + " vots (" + (((float)votsPartitsRepresentacio.get(i) / (float)votstsTotals * 100)) + "%)");
            }
        }


        // Assignar escons
        esconsPartits = assignarEscons(numEscons, votsPartitsRepresentacio);

        System.out.println("RESULTAT DE LES VOTACIONS");
        for (int i = 0; i < esconsPartits.length; i++) {
            System.out.println(nomsPartitsRepresentacio.get(i) + " ha obtingut un total de " + esconsPartits[i]);
        }

    }

    /**
     * Assignar el numero d'escons que ha obtingut cada partit
     * @param numEscons Numero d'escons totals
     * @param votsPartitsRepresentacio ArrayList amb els vots que han obtingut tots els partits polítics que tenen el percentatge minim de vots
     * @return ArrayList amb els escons que obtindria cada partit polític
     */

    private static int[] assignarEscons( int numEscons, ArrayList votsPartitsRepresentacio){
        int[] numEsconsPartits = new int[votsPartitsRepresentacio.size()];
        int[] divisor = new int[votsPartitsRepresentacio.size()];

        for (int i = 0; i < numEsconsPartits.length; i++) {
            numEsconsPartits[i] = 0;
            divisor[i] = 1;
        }

        int vots;
        int div;
        int posicio;
        int [] votsPartitsHondt = new int[votsPartitsRepresentacio.size()];
        for (int i = 0; i < numEscons; i++) {
            for (int j = 0; j < votsPartitsHondt.length; j++) {
                vots = (int)votsPartitsRepresentacio.get(i);
                div = divisor[i];
                votsPartitsHondt[i] = (vots/div);
            }
            posicio = posicioMaxVots(votsPartitsHondt);

            div = divisor[posicio];
            div++;
            divisor[posicio] = div;
            int esc = numEsconsPartits[posicio];
            esc++;
            numEsconsPartits[posicio] = esc;
        }

        return numEsconsPartits;
    }

    /**
     * Obtenir la posició del ArrayList que te el numero més gran de vots
     * @param votsPartitsHondt ArrayList amb els vots que han obtingut tots els partits polítics que tenen el percentatge minim de vots
     * @return Posicio amb el numero maxim de vots
     */

        private static int posicioMaxVots(int[] votsPartitsHondt){
        int max = votsPartitsHondt[0];
        int posicio = 0;
        for (int i = 0; i < votsPartitsHondt.length; i++) {
            if (votsPartitsHondt[i] >= max){
                posicio = i;
            }
        }

        return posicio;
    }
}
