package me.iwareq.fakeinventories.util;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.item.Item;

@FunctionalInterface
public interface ItemHandler {

    // This interface defines a method to handle items in inventory events
    
    void handle(Item item, InventoryTransactionEvent event);
}