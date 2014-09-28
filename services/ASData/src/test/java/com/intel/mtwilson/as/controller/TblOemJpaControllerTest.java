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
import com.intel.mtwilson.as.data.TblOem;

@RunWith(MockitoJUnitRunner.class)
public class TblOemJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblOemJpaController tblOemJpaController = new TblOemJpaController(emf);
    
    private int OEM_ID = 1;
    
    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblOemJpaController).getEntityManager();
        when(em.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCreate() {
        TblOem tblOem = new TblOem(OEM_ID);
        tblOem.setName("DELL");
        tblOemJpaController.create(tblOem);
        verify(em).persist(tblOem);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testEdit() throws NonexistentEntityException, ASDataException {
        TblOem tblOem = new TblOem(OEM_ID);
        tblOem.setName("DELL");
        doReturn(tblOem).when(tblOemJpaController).findTblOem(OEM_ID);
        tblOemJpaController.edit(tblOem);
        verify(em).merge(tblOem);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testDestroy() throws NonexistentEntityException {
        TblOem tblOem = new TblOem(OEM_ID);
        tblOem.setName("DELL");
        doReturn(tblOem).when(em).getReference(TblOem.class, OEM_ID);
        tblOemJpaController.destroy(OEM_ID);
        verify(em).remove(tblOem);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
