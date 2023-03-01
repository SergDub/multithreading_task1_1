import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        List<Future> futureList = new ArrayList<>();
        int maxValue = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);

        long startTs = System.currentTimeMillis();

        for (String text : texts) {
            futureList.add(threadPool.submit(new CallableTask(text)));
        }

        for (Future future : futureList) {
            maxValue = Math.max((int) future.get(), maxValue);
        }
        System.out.println(" Максимальный интервал значений " + maxValue);
        threadPool.shutdown();
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
