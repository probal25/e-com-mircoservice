package com.probal.inventoryservice;

import com.probal.inventoryservice.model.Inventory;
import com.probal.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone_13");
            inventory.setQuantity(100);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iphone_13_red");
            inventory1.setQuantity(50);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iphone_13_blue");
            inventory2.setQuantity(0);

            inventoryRepository.saveAll(List.of(inventory,inventory1,inventory2));
        };
    }*/

}
