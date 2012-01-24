/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String judul;
    @OneToMany
    private List<SubMenu> subMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    @Override
    public String toString() {
        return this.judul;
    }

    /**
     * @return the judul
     */
    public String getJudul() {
        return judul;
    }

    /**
     * @param judul the judul to set
     */
    public void setJudul(String judul) {
        this.judul = judul;
    }

    /**
     * @return the subMenus
     */
    public List<SubMenu> getSubMenus() {
        return subMenus;
    }

    /**
     * @param subMenus the subMenus to set
     */
    public void setSubMenus(List<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }
}
