import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) {
        //создаем магазины, указываем размер и максимальное значение массива
        Shop shop1 = new Shop(10, 1000);
        Shop shop2 = new Shop(15, 1500);
        Shop shop3 = new Shop(20, 2000);

        LongAdder longAdder = new LongAdder();//при. для себя: это класс, не переменная, он собирает все числа и складывает их при вызове метода sum()

        Thread shop1Thread = new Thread(() -> shop1.getProfit().forEach(longAdder::add));//назначаем потоку проход по всему листу и добавляем каждый элемент в longAdder
        Thread shop2Thread = new Thread(() -> shop2.getProfit().forEach(longAdder::add));
        Thread shop3Thread = new Thread(() -> shop3.getProfit().forEach(longAdder::add));

        shop1Thread.start();
        shop2Thread.start();
        shop3Thread.start();

        //останавливаем потоки по завершению
        try {
            shop1Thread.join();
            shop2Thread.join();
            shop3Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //подсчитываем выручку
        System.out.println("Общая выручка: " + longAdder.sum());
    }
}