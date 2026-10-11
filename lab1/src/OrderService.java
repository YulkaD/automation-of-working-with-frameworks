import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderService {


    private List<Order>orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(long orderId) {
        Order order = findOrderById(orderId);
        if (order != null) {
            orders.remove(order);
        }
    }

    public Order findOrderById(long orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public List<Order>findOrdersByCustomer(long customerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().getId() == customerId) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Order>findOrdersByStatus(OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == status) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Order>findOrdersContainingProduct(long productId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                if (product.getId() == productId) {
                    result.add(order);
                    break;
                }
            }
        }
        return result;
    }

    public double calculateOrderTotal(long orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Замовлення з id=" + orderId + " не знайдено");
        }
        double total = 0;
        for (Product product : order.getProducts()) {
            total += product.getPrice();
        }
        return total;
    }

    public Set<Customer>findCustomersWithOrders() {
        Set<Customer> customers = new LinkedHashSet<>();
        for (Order order : orders) {
            customers.add(order.getCustomer());
        }
        return customers;
    }

    public List<Order>sortOrdersByTotalPrice() {
        List<Order>sorted = new ArrayList<>(orders);
        sorted.sort(Comparator.comparingDouble(order -> calculateOrderTotal(order.getId())));
        return sorted;
    }

    public Map<OrderStatus, Long>countOrdersByStatus() {
        Map<OrderStatus, Long>result = new EnumMap<>(OrderStatus.class);

        for (OrderStatus status : OrderStatus.values()) {
            result.put(status, 0L);
        }

        for (Order order : orders) {
            result.put(order.getStatus(), result.get(order.getStatus()) + 1);
        }
        return result;
    }
}
