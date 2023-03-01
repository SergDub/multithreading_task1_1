import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        List<Thread> threadList = new ArrayList<>();

        long startTs = System.currentTimeMillis();

        for (String text : texts) {
            threadList.add(new Thread(
                    () -> {
                        int maxSize = 0;
                        for (int i = 0; i < text.length(); i++) {
                            for (int j = 0; j < text.length(); j++) {
                                if (i >= j) {
                                    continue;
                                }
                                boolean bFound = false;
                                for (int k = i; k < j; k++) {
                                    if (text.charAt(k) == 'b') {
                                        bFound = true;
                                        break;
                                    }
                                }
                                if (!bFound && maxSize < j - i) {
                                    maxSize = j - i;
                                }
                            }
                        }
                        System.out.println(text.substring(0, 100) + "->" + maxSize);
                    }
            ));
        }

        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }


        long endTS = System.currentTimeMillis();

        System.out.println("Time: " + (endTS - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


}
