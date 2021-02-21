package nullBlade.ai;

import java.util.Random;

public class Main {
    public static Random thread;



    public static void main(String[] args) {
        thread = new Random();

        AI[] AiArray = new AI[512];

        AiThread[] threads = new AiThread[4];

        for (int i = 0 ; AiArray.length > i ; i++) {
            AiArray[i] = new AI();
            AiArray[i].generateArrays(new int[] {
                    64, 128, 256, 128, 64, 2, 2
            });
            AiArray[i].learningMultiplier = thread.nextFloat()/(1028*thread.nextFloat());
        }

        for (int thr = 0 ; threads.length > thr ; thr++) {
            AI[] thisThreadsAI = new AI[AiArray.length/threads.length];

            int o1 = 0;
            for (int th = thr*(AiArray.length/threads.length) ; th < (thr+1)*(AiArray.length/threads.length) ; th++) {
                thisThreadsAI[o1] = AiArray[th];
                o1++;
            }

            threads[thr] = new AiThread(thisThreadsAI, thr);
            threads[thr].start();
        }

        System.out.println("Starting training");
        while (true) {
            for (AiThread th : threads) {
                th.setWaiting(false);
            }

            wh : while(true) {
                for (AiThread th : threads) {
                    if (!th.isWaiting()) {
                        continue wh;
                    }
                }
                break;
            }

            AI current = AiArray[0];
            for (AI a : AiArray ){
                if (a.money + (a.houses * 1.025) > current.money + (current.houses * 1.025)) {
                    current = a;
                }
            }
            System.out.println("=----=----=----=----=");
            System.out.println("max money: " + current.money);
            System.out.println("max houses: " + current.houses);
            for (AI a : AiArray) {
                for (int x = 0; a.neurons.length > x ; x++) {
                    for (int y = 0; a.neurons[x].length > y ; y++) {
                        for (int z = 0; a.neurons[x][y].length > z ; z++) {
                            a.neurons[x][y][z] = ((a.neurons[x][y][z]/4) + current.neurons[x][y][z])/1.25f;

                        }
                    }
                }
                a.money = 10;
                a.houses = 0;
            }

        }
        //System.out.println("Ai has stopped training");

    }
}
