/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Lenguaje;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Tipo;

/**
 *
 * @author ramiro
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) {
        if (tipo.getLenguajeCollection() == null) {
            tipo.setLenguajeCollection(new ArrayList<Lenguaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Lenguaje> attachedLenguajeCollection = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeCollectionLenguajeToAttach : tipo.getLenguajeCollection()) {
                lenguajeCollectionLenguajeToAttach = em.getReference(lenguajeCollectionLenguajeToAttach.getClass(), lenguajeCollectionLenguajeToAttach.getId());
                attachedLenguajeCollection.add(lenguajeCollectionLenguajeToAttach);
            }
            tipo.setLenguajeCollection(attachedLenguajeCollection);
            em.persist(tipo);
            for (Lenguaje lenguajeCollectionLenguaje : tipo.getLenguajeCollection()) {
                Tipo oldIdTipoOfLenguajeCollectionLenguaje = lenguajeCollectionLenguaje.getIdTipo();
                lenguajeCollectionLenguaje.setIdTipo(tipo);
                lenguajeCollectionLenguaje = em.merge(lenguajeCollectionLenguaje);
                if (oldIdTipoOfLenguajeCollectionLenguaje != null) {
                    oldIdTipoOfLenguajeCollectionLenguaje.getLenguajeCollection().remove(lenguajeCollectionLenguaje);
                    oldIdTipoOfLenguajeCollectionLenguaje = em.merge(oldIdTipoOfLenguajeCollectionLenguaje);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getId());
            Collection<Lenguaje> lenguajeCollectionOld = persistentTipo.getLenguajeCollection();
            Collection<Lenguaje> lenguajeCollectionNew = tipo.getLenguajeCollection();
            List<String> illegalOrphanMessages = null;
            for (Lenguaje lenguajeCollectionOldLenguaje : lenguajeCollectionOld) {
                if (!lenguajeCollectionNew.contains(lenguajeCollectionOldLenguaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lenguaje " + lenguajeCollectionOldLenguaje + " since its idTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Lenguaje> attachedLenguajeCollectionNew = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeCollectionNewLenguajeToAttach : lenguajeCollectionNew) {
                lenguajeCollectionNewLenguajeToAttach = em.getReference(lenguajeCollectionNewLenguajeToAttach.getClass(), lenguajeCollectionNewLenguajeToAttach.getId());
                attachedLenguajeCollectionNew.add(lenguajeCollectionNewLenguajeToAttach);
            }
            lenguajeCollectionNew = attachedLenguajeCollectionNew;
            tipo.setLenguajeCollection(lenguajeCollectionNew);
            tipo = em.merge(tipo);
            for (Lenguaje lenguajeCollectionNewLenguaje : lenguajeCollectionNew) {
                if (!lenguajeCollectionOld.contains(lenguajeCollectionNewLenguaje)) {
                    Tipo oldIdTipoOfLenguajeCollectionNewLenguaje = lenguajeCollectionNewLenguaje.getIdTipo();
                    lenguajeCollectionNewLenguaje.setIdTipo(tipo);
                    lenguajeCollectionNewLenguaje = em.merge(lenguajeCollectionNewLenguaje);
                    if (oldIdTipoOfLenguajeCollectionNewLenguaje != null && !oldIdTipoOfLenguajeCollectionNewLenguaje.equals(tipo)) {
                        oldIdTipoOfLenguajeCollectionNewLenguaje.getLenguajeCollection().remove(lenguajeCollectionNewLenguaje);
                        oldIdTipoOfLenguajeCollectionNewLenguaje = em.merge(oldIdTipoOfLenguajeCollectionNewLenguaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getId();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Lenguaje> lenguajeCollectionOrphanCheck = tipo.getLenguajeCollection();
            for (Lenguaje lenguajeCollectionOrphanCheckLenguaje : lenguajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the Lenguaje " + lenguajeCollectionOrphanCheckLenguaje + " in its lenguajeCollection field has a non-nullable idTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
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

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
