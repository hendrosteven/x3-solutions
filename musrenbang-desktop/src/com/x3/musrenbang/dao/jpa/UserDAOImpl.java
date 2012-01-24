/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.UsersDAO;
import com.x3.musrenbang.entity.Users;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class UserDAOImpl extends GeneralDAOImpl implements UsersDAO {

    public Users login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Users get(int id) {
        return getJpaTemplate().find(Users.class, id);
    }

    public List<Users> gets() {
        return getJpaTemplate().find("select u from Users u");
    }

}
