/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.User;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface UserDAO {
    public void insert(User user)throws Exception;
    public void update(User user)throws Exception;
    public void delete(int id)throws Exception;
    public User get(int id)throws Exception;
    public List<User> gets()throws Exception;
    public User login(String userName,String password)throws Exception;
}
