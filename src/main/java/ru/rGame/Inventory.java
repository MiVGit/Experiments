package ru.rGame;

import ru.rGame.objects.*;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public Armor armor;
    public Weapon weapon;
    public List<ItemObject> inventory = new ArrayList<>();

    public void drop(Person person, int itemIndex) {
        if (itemIndex == 0 && armor != null) {
            dropArmor(person);
        }
        if (itemIndex == 1 && weapon !=null) {
            dropWeapon(person);
        }
        if (itemIndex > 1) {
            dropInventoryObject(person, itemIndex);
        }
    }

    private void dropArmor(Person person) {
        armor.x = person.x;
        armor.y = person.y;
        LevelManager.getInstance().addGameObject(armor);
        armor = null;
    }

    private void dropWeapon(Person person) {
        weapon.x = person.x;
        weapon.y = person.y;
        LevelManager.getInstance().addGameObject(weapon);
        weapon = null;
    }

    private void dropInventoryObject (Person person, int itemIndex) {
        if (itemIndex-1<=person.getInventory().inventory.size()) {
            GameObject inventoryObject = person.getInventory().inventory.get(itemIndex - 2);
            if (inventoryObject != null) {
                inventoryObject.x = person.x;
                inventoryObject.y = person.y;
                LevelManager.getInstance().addGameObject(inventoryObject);
                person.getInventory().inventory.remove(itemIndex-2);
            }
        }
    }

    public int getSize() {
        int size = 5;
        return size;
    }
}
