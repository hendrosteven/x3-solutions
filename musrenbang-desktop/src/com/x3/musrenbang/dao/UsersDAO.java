/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Users;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface UsersDAO extends GeneralDAO {

    public Users login(String username, String password);

    public Users get(int id);

    public List<Users> gets();
}
