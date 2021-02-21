package nullBlade.ai;

import java.util.Random;

public class Main {
    public static Random thread;



    public static void main(String[] args) {
        thread = new Random();

        AI[] AiArray = new AI[256];
        for (int i = 0 ; AiArray.length > i ; i++) {
            AiArray[i] = new AI();
            AiArray[i].generateArrays(new int[] {
                    64, 128, 256, 128, 64, 2, 2
            });
            AiArray[i].learningMultiplier = thread.nextFloat()/(1028*thread.nextFloat());
        }

        while (true) {
            for (AI a : AiArray) a.e();

            for (int i = 0 ; 10 > i ; i++) {
                for (AI a : AiArray) {
                    boolean going = true;
                    a.money += a.houses * 3;
                    while (going) {
                        if (a.money < 5) {
                            break;
                        }

                        float[] houseArray = new float[16];
                        int o = 0;
                        for (char c : Integer.toBinaryString(a.houses).toCharArray()) {
                            houseArray[o] = "1".equals(c+"") ? 1 : 0;
                            o++;
                        }

                        float[] inputArray = new float[64];

                        float[] moneyArray = new float[32];
                        o = 0;
                        for (char c : Integer.toBinaryString(a.money).toCharArray()) {
                            moneyArray[o] = "1".equals(c+"") ? 1 : 0;
                            o++;
                        }

                        float[] turnArray = new float[16];
                        o = 0;
                        for (char c : Integer.toBinaryString(i).toCharArray()) {
                            moneyArray[o] = c == '1' ? 1 : 0;
                            o++;
                        }

                        o = 0;
                        for (float f : houseArray) {
                            inputArray[o] = f;
                            o++;
                        }

                        for (float f : moneyArray) {
                            inputArray[o] = f;
                            o++;
                        }

                        for (float f : turnArray) {
                            inputArray[o] = f;
                            o++;
                        }

                        boolean buyHouse = a.getExit(inputArray)[0][0] == 0? false : true;

                        if (buyHouse) {
                            a.money -= 5;
                            a.houses += 1;

                        } else {
                            going = false;
                        }
                    }
                }
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
            };

        }
        //System.out.println("Ai has stopped training");

    }
}
