package com.zero.service.impl;

import com.zero.entity.Menu;
import com.zero.entity.MenuExample;
import com.zero.mapper.MenuMapper;
import com.zero.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/24 14:38
 * Modified By:
 */

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void removeMenuById(Integer menuId) {
        menuMapper.deleteByPrimaryKey(menuId);
    }
}
