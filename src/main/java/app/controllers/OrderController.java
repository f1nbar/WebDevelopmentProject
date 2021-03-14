package app.controllers;

import app.entities.Order;
import app.entities.Product;
import app.entities.ProductOrder;
import app.entities.User;
import app.keys.ProductOrderKey;
import app.repositories.OrderRepository;
import app.repositories.ProductOrderRepository;
import app.repositories.ProductRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductOrderRepository productOrderRepository;

    @GetMapping("/orders")
    public @ResponseBody List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/setShipped")
    public @ResponseBody Order setShipped(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Shipped");
        orderRepository.save(order);
        return order;
    }

    @GetMapping("/orders/setCancelled")
    public @ResponseBody Order setCancelled(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Cancelled");
        orderRepository.save(order);
        return order;
    }

    @GetMapping("/orderHistory")
    public @ResponseBody List<Order> getOrderHistory(@RequestParam Long customerId) {
        List<Order> orderHistory = new ArrayList<Order>();
        int i=0;
        for(Order order:orderRepository.findAll()) {
            System.out.println(order.getCustomer().getUsername());
            if (i>5) {
                break;
            }
            if (order.getCustomer().getId().equals(customerId)) {
                orderHistory.add(order);
            }
            i++;
        }
        return orderHistory;
    }

    @GetMapping("/checkout")
    public String getCheckout() {
        return "checkout.html";
    }

    @PostMapping("/checkout")
    public @ResponseBody String postCheckout(@RequestBody List<String> orderInfo, @RequestParam Long customerId) {
//        List<String> orderInfo = orderInfoObj.get("arr");
//        for (String s : orderInfo) {
//            System.out.println(s);
//        }
        String address = orderInfo.get(0);
        int orderTotal = 0;
        List<Product> products = new ArrayList<Product>();
        List<Integer> quantities = new ArrayList<Integer>();
        for (String str:orderInfo) {
            String productId = str.split("_")[0];
            System.out.println(productId);
            if (productRepository.findById(productId).isPresent()) {
                Product product = productRepository.findById(productId).get();
                System.out.println(product.getProductName());
                quantities.add(Integer.parseInt(str.split("_")[1]));
                products.add(product);
                orderTotal += product.getPrice();
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String dateOrdered = dtf.format(now);
        User customer = userRepository.findById(customerId).get();
        Order order = new Order(customer, address, "Confirmed", dateOrdered, orderTotal);
        orderRepository.save(order);
        System.out.println("Making Product Orders");
        for (int i=0; i<products.size(); i++) {
            System.out.println(products.get(i).getProductName());
            System.out.println(order.getOrderId());
            ProductOrder productOrder = new ProductOrder(products.get(i), order, quantities.get(i));
            productOrderRepository.save(productOrder);
        }

        return "Success";
    }
}
