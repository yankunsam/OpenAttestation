package com.intel.mtwilson.as.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.intel.mtwilson.as.controller.exceptions.ASDataException;
import com.intel.mtwilson.as.controller.exceptions.NonexistentEntityException;
import com.intel.mtwilson.as.data.TblOs;

@RunWith(MockitoJUnitRunner.class)
public class TblOsJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblOsJpaController tblOsJpaController = new TblOsJpaController(emf);

    private int OS_ID = 1;
    
    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblOsJpaController).getEntityManager();
        when(em.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCreate() {
        TblOs tblOs = new TblOs(OS_ID, "Fedora", "20");
        tblOsJpaController.create(tblOs);
        verify(em).persist(tblOs);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testEdit() throws NonexistentEntityException, ASDataException {
        TblOs tblOs = new TblOs(OS_ID, "Fedora", "21");
        doReturn(tblOs).when(tblOsJpaController).findTblOs(OS_ID);
        tblOsJpaController.edit(tblOs);
        verify(em).merge(tblOs);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }
    
    @Test
    public void testDestroy() throws NonexistentEntityException {
        TblOs tblOs = new TblOs(OS_ID, "Fedora", "20");
        doReturn(tblOs).when(em).getReference(TblOs.class, OS_ID);
        tblOsJpaController.destroy(OS_ID);
        verify(em).remove(tblOs);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @After
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
