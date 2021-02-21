package nullBlade.ai;

public class AI {

    public int houses;
    public int money = 10;

//-------------------------------------------------------------------------------------------------------------------------------
public float learningMultiplier = 0;

    public float[][][] neurons = new float[][][]{
            {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}
    };


    public float[][] values = new float[][]{
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };


    public int finalValue;

    public float[][] getExit(float[] input) {
        float[][] is = values.clone();

        is[0] = input;

        float[] finall = new float[finalValue];

        int x = 0, y = 0, z = 0;
        for (float[][] x_ : neurons) {
            for (float[] y_ : x_) {
                for (float z_ : y_) {
                    if (x < is.length-2) {
                        is[x+1][z] += is[x][y] * z_;
                    } else {
                        finall[z] += is[x][y] * z_;
                    }
                    z++;
                }
                y++;
                z = 0;
            }
            x++;
            y = 0;
        }
        float[][] curhigh = new float[finall.length][2];
        y=0;
        x=0;
        for (float x_ : finall) {
            curhigh[x][0] = x;
            curhigh[x][1] = x_;
            x++;
        }

        for (int i = 0 ; i < curhigh.length + 1 ; i++) {
            for (float[] y_ : curhigh) {
                if (y < curhigh.length - 1) {
                    if (curhigh[y][1] < curhigh[y + 1][1]) {
                        float[] a = curhigh[y].clone();
                        curhigh[y][1] = curhigh[y + 1][1];
                        curhigh[y][0] = curhigh[y + 1][0];
                        curhigh[y + 1] = a;
                    }
                }
                y++;
            }
            y=0;
        }

        return curhigh;
    }
    public void generateArrays(int[] size) {
        float[][][] arr = new float[size.length - 1][][];
        float[][] arrr = new float[size.length - 1][];
        for (int i = 0; size.length - 1 > i; i++) {
            if (size.length - 1 == i) {
            } else {
                float[][] in = new float[size[i]][size[i + 1]];
                arr[i] = in;
                arrr[i] = new float[size[i]];
            }
        }

        int x = 0, y = 0, z = 0;
        for (float[] x_ : arrr) {
            for (float y_ : x_) {
                arrr[x][y] = 0;
                y++;
            }
            x++;
            y = 0;
        }


        x = 0; y = 0;
        for (float[][] x_ : arr) {
            for (float[] y_ : x_) {
                for (float z_ : y_) {
                    arr[x][y][z] = 0;
                    z++;
                }
                y++;
                z = 0;
            }
            x++;
            y = 0;
        }

        neurons = arr;
        values = arrr;
        finalValue = size[size.length - 1];
    }

    public void e() {
        for (int x=0; x < neurons.length; x++) {
            for (int y=0; y < neurons[x].length; y++) {
                for (int z=0; z < neurons[x][y].length; z++) {
                    neurons[x][y][z] += Main.thread.nextBoolean() ? Main.thread.nextFloat()*learningMultiplier : -Main.thread.nextFloat()*learningMultiplier;
                    neurons[x][y][z] = Utils.clamp(neurons[x][y][z], 0, 1.5f);
                }
            }
        }
    }


}
