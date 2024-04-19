/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Lenguaje;
import models.Tipo;

/**
 *
 * @author ramiro
 */
public class LenguajeJpaController implements Serializable {

    public LenguajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lenguaje lenguaje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo idTipo = lenguaje.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                lenguaje.setIdTipo(idTipo);
            }
            em.persist(lenguaje);
            if (idTipo != null) {
                idTipo.getLenguajeCollection().add(lenguaje);
                idTipo = em.merge(idTipo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lenguaje lenguaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lenguaje persistentLenguaje = em.find(Lenguaje.class, lenguaje.getId());
            Tipo idTipoOld = persistentLenguaje.getIdTipo();
            Tipo idTipoNew = lenguaje.getIdTipo();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                lenguaje.setIdTipo(idTipoNew);
            }
            lenguaje = em.merge(lenguaje);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getLenguajeCollection().remove(lenguaje);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getLenguajeCollection().add(lenguaje);
                idTipoNew = em.merge(idTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lenguaje.getId();
                if (findLenguaje(id) == null) {
                    throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lenguaje lenguaje;
            try {
                lenguaje = em.getReference(Lenguaje.class, id);
                lenguaje.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.", enfe);
            }
            Tipo idTipo = lenguaje.getIdTipo();
            if (idTipo != null) {
                idTipo.getLenguajeCollection().remove(lenguaje);
                idTipo = em.merge(idTipo);
            }
            em.remove(lenguaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lenguaje> findLenguajeEntities() {
        return findLenguajeEntities(true, -1, -1);
    }

    public List<Lenguaje> findLenguajeEntities(int maxResults, int firstResult) {
        return findLenguajeEntities(false, maxResults, firstResult);
    }

    private List<Lenguaje> findLenguajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lenguaje.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Lenguaje findLenguaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getLenguajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lenguaje> rt = cq.from(Lenguaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
