package com.gameszaum.core.menu;

import com.gameszaum.core.menu.event.MenuHandler;
import com.gameszaum.core.menu.helper.MenuHelper;

public interface MenuBuilder {

    void build(MenuHelper menuHelper, MenuHandler menuHandler);

}
