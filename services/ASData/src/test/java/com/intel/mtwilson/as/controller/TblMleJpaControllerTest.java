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

import com.intel.mtwilson.as.data.TblMle;

@RunWith(MockitoJUnitRunner.class)
public class TblMleJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblMleJpaController tblMleJpaController = new TblMleJpaController(emf);

    private int MLE_ID = 1;

    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblMleJpaController).getEntityManager();
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
        TblMle tblMle = new TblMle(MLE_ID, "XEN", "4.3", "PCR", "VMM", "18");
        tblMleJpaController.create(tblMle);
        verify(em).persist(tblMle);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }
}
