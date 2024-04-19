/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ramiro
 */
@Entity
@Table(name = "lenguaje")
@NamedQueries({
    @NamedQuery(name = "Lenguaje.findAll", query = "SELECT l FROM Lenguaje l"),
    @NamedQuery(name = "Lenguaje.findById", query = "SELECT l FROM Lenguaje l WHERE l.id = :id"),
    @NamedQuery(name = "Lenguaje.findByNombre", query = "SELECT l FROM Lenguaje l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Lenguaje.findByCaracteristicas", query = "SELECT l FROM Lenguaje l WHERE l.caracteristicas = :caracteristicas")})
public class Lenguaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "caracteristicas")
    private String caracteristicas;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipo idTipo;

    public Lenguaje() {
    }

    public Lenguaje(Integer id) {
        this.id = id;
    }

    public Lenguaje(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lenguaje)) {
            return false;
        }
        Lenguaje other = (Lenguaje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Lenguaje[ id=" + id + " ]";
    }
    
}
