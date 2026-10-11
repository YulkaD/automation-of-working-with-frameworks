import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Customer ivan = new Customer(1, "Іван Іванко", "ivan@mail.com");
        Customer olga = new Customer(2, "Ольга Коваль", "olga@mail.com");
        Customer max = new Customer(3, "Максим Петренко", "max@mail.com");
        Product laptop = new Product(1, "Ноутбук", ProductCategory.ELECTRONICS, 30000);
        Product phone = new Product(2, "Смартфон", ProductCategory.ELECTRONICS, 15000);
        Product tshirt = new Product(3, "Футболка", ProductCategory.CLOTHING, 500);
        Product book = new Product(4, "Книга", ProductCategory.BOOKS, 800);
        Product lamp = new Product(5, "Лампа", ProductCategory.HOME, 700);

        Order order1 = new Order(11, ivan, Arrays.asList(laptop, book), OrderStatus.NEW);
        Order order2 = new Order(12, ivan, Arrays.asList(tshirt, lamp), OrderStatus.DELIVERED);
        Order order3 = new Order(13, olga, Arrays.asList(phone), OrderStatus.PROCESSING);
        Order order4 = new Order(14, olga, Arrays.asList(book, tshirt, lamp), OrderStatus.NEW);
        Order order5 = new Order(15, ivan, Arrays.asList(phone, tshirt), OrderStatus.CANCELLED);

        OrderService service = new OrderService();

        printTitle("1. Додаємо замовлення (addOrder)");
        service.addOrder(order1);
        service.addOrder(order2);
        service.addOrder(order3);
        service.addOrder(order4);
        service.addOrder(order5);
        System.out.println("Додано 5 замовлень.");

        printTitle("2. Пошук замовлення за id");
        System.out.println("Шукаємо id=13:");
        System.out.println(service.findOrderById(13));
        System.out.println("Шукаємо id=999:");
        System.out.println(service.findOrderById(999));

        printTitle("3. Замовлення клієнта");
        System.out.println("Замовлення клієнта з id=1 (Іван):");
        printOrders(service.findOrdersByCustomer(1));

        printTitle("4. Замовлення за статусом");
        System.out.println("Замовлення зі статусом NEW:");
        printOrders(service.findOrdersByStatus(OrderStatus.NEW));

        printTitle("5. Замовлення з товаром");
        System.out.println("Замовлення, де є товар з id=4 (Книга ):");
        printOrders(service.findOrdersContainingProduct(4));

        printTitle("6. Сума замовлення");
        System.out.println("Сума замовлення 11: " + service.calculateOrderTotal(11) + " грн");
        System.out.println("Сума замовлення 14: " + service.calculateOrderTotal(14) + " грн");

        printTitle("7. Клієнти, які мають замовлення ");
        for (Customer c : service.findCustomersWithOrders()) {
            System.out.println(c);
        }
        System.out.println("(Максим Петренко не зустрічається, бо в нього немає замовлень)");

        printTitle("8. Сортування за сумою");
        for (Order o : service.sortOrdersByTotalPrice()) {
            System.out.println("Замовлення №" + o.getId() + " - " + service.calculateOrderTotal(o.getId()) + " грн");
        }

        printTitle("9. Кількість замовлень за статусами");
        Map<OrderStatus, Long> counts = service.countOrdersByStatus();
        for (OrderStatus status : counts.keySet()) {
            System.out.println(status + ": " + counts.get(status));
        }

        printTitle("10. Видалення замовлення");
        System.out.println("Видаляємо замовлення 15...");
        service.removeOrder(15);
        System.out.println("Пошук замовлення 15 після видалення: " + service.findOrderById(15));
        System.out.println("Статуси після видалення:");
        System.out.println(service.countOrdersByStatus());
    }

    private static void printTitle(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    private static void printOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("(нічого не знайдено)");
            return;
        }
        for (Order o : orders) {
            System.out.println(o);
        }
    }
}
 