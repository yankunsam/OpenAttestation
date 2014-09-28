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
import com.intel.mtwilson.as.data.TblPcrManifest;

@RunWith(MockitoJUnitRunner.class)
public class TblPcrManifestJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblPcrManifestJpaController tblPcrManifestJpaController = new TblPcrManifestJpaController(emf);
    
    private int PcrManifest_ID = 1; 
    
    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblPcrManifestJpaController).getEntityManager();
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
        TblPcrManifest tblPcrManifest = new TblPcrManifest(PcrManifest_ID, "0", "31B97D97B4679917EC3C1D943635693FFBAB4143");
        tblPcrManifestJpaController.create(tblPcrManifest);
        verify(em).persist(tblPcrManifest);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testEdit() throws NonexistentEntityException, ASDataException {
        TblPcrManifest tblPcrManifest = new TblPcrManifest(PcrManifest_ID, "0", "31B97D97B4679917EC3C1D943635693FFBAB4143");
        doReturn(tblPcrManifest).when(em).find(TblPcrManifest.class, PcrManifest_ID);
        doReturn(tblPcrManifest).when(em).getReference(TblOs.class, PcrManifest_ID);
        tblPcrManifestJpaController.edit(tblPcrManifest);
        verify(em).merge(tblPcrManifest);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testDestroy() throws NonexistentEntityException {
        TblPcrManifest tblPcrManifest = new TblPcrManifest(PcrManifest_ID, "0", "31B97D97B4679917EC3C1D943635693FFBAB4143");
        doReturn(tblPcrManifest).when(em).getReference(TblPcrManifest.class, PcrManifest_ID);
        tblPcrManifestJpaController.destroy(PcrManifest_ID);
        verify(em).remove(tblPcrManifest);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }
}
