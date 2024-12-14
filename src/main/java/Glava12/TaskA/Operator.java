package Glava12.TaskA;

public class Operator {
    private final String name;

    public Operator(String name)
    {
        this.name = name;
    }

    // Обработка вызова клиента
    public void handle(Client client)
    {
        System.out.println(name + " начал обслуживать " + client.getName());
        try {
            Thread.sleep(2000); // Имитация обработки вызова
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " был прерван во время обработки вызова.");
        }
        System.out.println(name + " закончил обслуживание " + client.getName());
    }
}