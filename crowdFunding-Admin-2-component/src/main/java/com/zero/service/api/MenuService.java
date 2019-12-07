package com.zero.service.api;

import com.zero.entity.Menu;

import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/24 14:38
 * Modified By:
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    Menu getMenuById(Integer menuId);

    void removeMenuById(Integer menuId);
}
