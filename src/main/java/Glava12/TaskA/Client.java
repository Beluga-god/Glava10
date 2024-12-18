package Glava12.TaskA;

import java.util.Random;

public class Client implements Runnable {
    private final String name;
    private final CallCenter callCenter;
    private boolean CalledBack;

    public Client(String name, CallCenter callCenter)
    {
        this.name = name;
        this.callCenter = callCenter;
        this.CalledBack = false;
    }

    public String getName()
    {
        return name;
    }

    // Клиент перезванивает в CallCenter
    public void callAgain(CallCenter callCenter) {
        if (!CalledBack)    // Проверяем, перезвонил ли клиент
        {
            try
            {
                Thread.sleep(new Random().nextInt(3000) + 1000);
                System.out.println(name + " перезванивает.");
                CalledBack = true;  // Устанавливаем флаг, что клиент перезвонил
                callCenter.handleCall(this);    // Клиент перезванивает
            } catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                System.out.println(name + " передумал перезванивать.");
            }
        } else
        {
            System.out.println(name + " уже не перезвонит.");
        }
    }

    @Override
    public void run()
    {
        callCenter.handleCall(this);
    }
}