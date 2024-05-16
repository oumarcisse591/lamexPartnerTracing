package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);
}
