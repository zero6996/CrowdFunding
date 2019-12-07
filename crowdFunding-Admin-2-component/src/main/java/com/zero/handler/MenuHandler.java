package com.zero.handler;

import com.zero.entity.Menu;
import com.zero.entity.ResultEntity;
import com.zero.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/24 14:20
 * Modified By:
 */

@RestController
@RequestMapping("/menu")
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/remove/{menuId}")
    public ResultEntity<String> removeMenuById(@PathVariable("menuId") Integer menuId){
        menuService.removeMenuById(menuId);
        return ResultEntity.successWithoutData();
    }

    /**
     * 更新menu信息
     * @param menu
     * @return
     */
    @RequestMapping("/update")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 根据id查询menu数据
     * @param menuId
     * @return
     */
    @RequestMapping("/get/{menuId}")
    public ResultEntity<Menu> getMenuById(@PathVariable("menuId") Integer menuId){
        Menu menu = menuService.getMenuById(menuId);
        return ResultEntity.successWithoutData(menu);
    }


    @RequestMapping("/save")
    public ResultEntity<String> save(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/get/whole/tree")
    public ResultEntity<Menu> getWholeTree(){
        // 1. 查询所有的树形节点用于组装
        List<Menu> menuList = menuService.getAll();
        // 2. 将list<Menu>转换为Map<id,Menu>
        Map<Integer,Menu> map = new HashMap<>(16);
        for (Menu menu : menuList) {
            map.put(menu.getId(),menu);
        }
        // 3. 声明变量存储根节点对象
        Menu rootNode = null;
        // 4. 遍历list<Menu>
        for (Menu menu : menuList) {
            // 5. 获取当前Menu对象的pid属性
            Integer pid = menu.getPid();
            // 6. 判断是否为null
            if (pid == null){
                // 7. 如果pid为null，说明当前节点是根节点
                rootNode = menu;
                // 8. 根节点没有父节点，故无需找父节点组装
                continue;
            }
            // 9. pid不为null，表示有父节点，找到父节点，将当前节点组装进去
            Menu fatherNode = map.get(pid);
            // 10. 将子节点menu添加进父节点的子节点集合中
            fatherNode.getChildren().add(menu);
        }
        return ResultEntity.successWithoutData(rootNode);
    }
}
