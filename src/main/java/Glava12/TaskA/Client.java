package Glava12.TaskA;

import java.util.Random;

public class Client implements Runnable {
    private final String name;
    private final CallCenter callCenter;

    public Client(String name, CallCenter callCenter)
    {
        this.name = name;
        this.callCenter = callCenter;
    }

    public String getName()
    {
        return name;
    }

    // Клиент перезванивает в CallCenter
    public void callAgain(CallCenter callCenter)
    {
        try {
            Thread.sleep(new Random().nextInt(3000) + 1000); // Имитация ожидания перед повторным звонком
            System.out.println(name + " перезванивает.");
            callCenter.handleCall(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " передумал перезванивать.");
        }
    }

    @Override
    public void run()
    {
        callCenter.handleCall(this);
    }
}