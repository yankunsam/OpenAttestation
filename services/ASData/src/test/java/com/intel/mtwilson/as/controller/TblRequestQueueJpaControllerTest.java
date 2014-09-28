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
import com.intel.mtwilson.as.data.TblRequestQueue;

@RunWith(MockitoJUnitRunner.class)
public class TblRequestQueueJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblRequestQueueJpaController tblRequestQueueJpaController = new TblRequestQueueJpaController(emf);
    
    private int RequestQueue_ID = 1;

    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblRequestQueueJpaController).getEntityManager();
        when(em.getTransaction()).thenReturn(transaction);
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

    @Test
    public void testCreate() {
        TblRequestQueue tblRequestQueue = new TblRequestQueue(RequestQueue_ID);
        tblRequestQueueJpaController.create(tblRequestQueue);
        verify(em).persist(tblRequestQueue);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testEdit() throws NonexistentEntityException, ASDataException {
        TblRequestQueue tblRequestQueue = new TblRequestQueue(RequestQueue_ID);
        doReturn(tblRequestQueue).when(tblRequestQueueJpaController).findTblRequestQueue(RequestQueue_ID);
        tblRequestQueueJpaController.edit(tblRequestQueue);
        verify(em).merge(tblRequestQueue);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testDestroy() throws NonexistentEntityException {
        TblRequestQueue tblRequestQueue = new TblRequestQueue(RequestQueue_ID);
        doReturn(tblRequestQueue).when(em).getReference(TblRequestQueue.class, RequestQueue_ID);
        tblRequestQueueJpaController.destroy(RequestQueue_ID);
        verify(em).remove(tblRequestQueue);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }
}
