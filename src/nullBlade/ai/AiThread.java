package nullBlade.ai;

public class AiThread extends Thread {

    private AI[] thisThreadsAI;
    private int thr;
    volatile private boolean waiting=true;

    public AiThread (AI[] thisThreadsAI, int thr) {
        super("thread" + thr);
        this.thisThreadsAI=thisThreadsAI;
        this.thr=thr;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    @Override
    public void run() {
        while (true) {
            while (waiting) {}
            long currentTime = System.currentTimeMillis();

            for (AI a : thisThreadsAI) a.e();

            for (int i = 0; 10 > i; i++) {
                for (AI a : thisThreadsAI) {
                    boolean going = true;
                    a.money += a.houses * 3;
                    while (going) {
                        if (a.money < 5) {
                            break;
                        }

                        float[] houseArray = new float[16];
                        int o = 0;
                        for (char c : Integer.toBinaryString(a.houses).toCharArray()) {
                            houseArray[o] = "1".equals(c + "") ? 1 : 0;
                            o++;
                        }

                        float[] inputArray = new float[64];

                        float[] moneyArray = new float[32];
                        o = 0;
                        for (char c : Integer.toBinaryString(a.money).toCharArray()) {
                            moneyArray[o] = "1".equals(c + "") ? 1 : 0;
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

                        boolean buyHouse = a.getExit(inputArray)[0][0] == 0 ? false : true;

                        if (buyHouse) {
                            a.money -= 5;
                            a.houses += 1;

                        } else {
                            going = false;
                        }
                    }
                }
            }
            System.out.println("Thread on number: " + thr + " finished after: " + (System.currentTimeMillis() - currentTime) + "ms");
            waiting = true;
        }
    }

}
