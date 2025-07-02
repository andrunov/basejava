import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DeadLock {

    static class Account {

        private String name;

        private double amount;

        public Account(String name, double amount) {
            this.name = name;
            this.amount = amount;
            System.out.println("Создан счет " + this);
        }

        public void deposit(double value) {
            System.out.printf("Зачисляем %s на счет %s%n", value, name);
            amount = amount + value;
        }

        public synchronized double withdraw(double value) {
            System.out.printf("Выводим %s со счета %s%n", value, name);
            if (value <= amount) {
                amount = amount - value;
                return value;
            } else {
                throw new RuntimeException(String.format("Amount=%s not enough to withdraw value=%s", amount, value));
            }
        }

        public synchronized void sillyBlock(double amount, Account account) {
            withdraw(amount);
            account.withdraw(amount);
        }

        @Override
        public String toString() {
            return String.format("%s остаток = %s", name, amount);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Account account_1 = new Account("Deposit 1", 100);
        Account account_2 = new Account("Deposit 2", 50);
        System.out.println();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                account_1.sillyBlock(10, account_2);

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                account_2.sillyBlock(15, account_1);
            }
        }).start();

        Thread.sleep(100);

        System.out.println();
        System.out.println("Проверяем счета - должно успеть списаться 10 с \"Deposit 1\" и 15 с \"Deposit 2\"");
        System.out.println("Текущее состояние счета " + account_1);
        System.out.println("Текущее состояние счета " + account_2);

    }
}
