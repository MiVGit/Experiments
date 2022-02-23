package ru.rGame.enums;

public enum MainMenuItems {
    START_NEW_GAME("MM_Start","Начать новую игру"),
    INFO("MM_Info","     Об игре     "),
    EXIT("MM_Exit","      Выход      ");

    final String menuItemName;
    final String getMenuItemTitle;

    MainMenuItems(String menuItemName, String menuItemTitle) {
        this.menuItemName = menuItemName;
        this.getMenuItemTitle = menuItemTitle;
    }

    public String[] getMenuItem () {
        return new String[] {this.menuItemName, this.getMenuItemTitle};
    }
}

