import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Ponto {
    int x, y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Ponto outro) {
        return this.x == outro.x && this.y == outro.y;
    }
}

public class Main {
    static final int LARGURA = 20;
    static final int ALTURA = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        ArrayList<Ponto> cobra = new ArrayList<>();
        cobra.add(new Ponto(LARGURA/2, ALTURA/2)); 

        Ponto comida = new Ponto(rand.nextInt(LARGURA), rand.nextInt(ALTURA));

        char direcao = 'D'; 
        boolean gameOver = false;
        int pontos = 0;

        while (!gameOver) {
            
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // campo
            for (int y = 0; y < ALTURA; y++) {
                for (int x = 0; x < LARGURA; x++) {
                    Ponto p = new Ponto(x, y);
                    boolean desenhou = false;
                    // cobra
                    for (Ponto c : cobra) {
                        if (p.equals(c)) {
                            System.out.print("O");
                            desenhou = true;
                            break;
                        }
                    }
                    // comida
                    if (p.equals(comida) && !desenhou) {
                        System.out.print("X");
                        desenhou = true;
                    }
                    if (!desenhou) System.out.print(".");
                }
                System.out.println();
            }
            System.out.println("Pontos: " + pontos);
            System.out.println("Movimente: W (cima) A (esq) S (baixo) D (dir)");

            // ler comando
            String comando = sc.nextLine().toUpperCase();
            if (comando.length() > 0) direcao = comando.charAt(0);

            // calcular nova cabeça
            Ponto cabeca = cobra.get(0);
            int nx = cabeca.x;
            int ny = cabeca.y;
            switch (direcao) {
                case 'W': ny--; break;
                case 'S': ny++; break;
                case 'A': nx--; break;
                case 'D': nx++; break;
            }
            Ponto novaCabeca = new Ponto(nx, ny);

          
            if (nx < 0 || nx >= LARGURA || ny < 0 || ny >= ALTURA) {
                gameOver = true;
                break;
            }

            
            for (Ponto c : cobra) {
                if (novaCabeca.equals(c)) {
                    gameOver = true;
                    break;
                }
            }
            if (gameOver) break;

            // mover cobra
            cobra.add(0, novaCabeca);

            // comer comida
            if (novaCabeca.equals(comida)) {
                pontos++;
                comida = new Ponto(rand.nextInt(LARGURA), rand.nextInt(ALTURA));
            } else {
                cobra.remove(cobra.size()-1); 
            }
        }

        System.out.println("\nGAME OVER! Pontos: " + pontos);
        sc.close();
    }
}
