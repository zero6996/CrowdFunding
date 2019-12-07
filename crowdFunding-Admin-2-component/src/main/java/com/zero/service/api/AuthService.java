package com.zero.service.api;

import com.zero.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @Author: zero
 * @Description: Date: Create in 2019/11/27 16:57
 * Modified By:
 */
public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdList(Integer roleId);

    void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap);
}
