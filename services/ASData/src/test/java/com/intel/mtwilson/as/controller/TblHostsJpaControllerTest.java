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
import com.intel.mtwilson.as.controller.exceptions.IllegalOrphanException;
import com.intel.mtwilson.as.controller.exceptions.NonexistentEntityException;
import com.intel.mtwilson.as.data.TblHosts;
import com.intel.mtwilson.crypto.CryptographyException;

@RunWith(MockitoJUnitRunner.class)
public class TblHostsJpaControllerTest {
    @Mock
    private EntityManagerFactory emf;
    
    @Mock
    private EntityManager em;
    
    @Mock
    private EntityTransaction transaction;
    
    @Spy
    private TblHostsJpaController tblHostsJpaController = new TblHostsJpaController(emf, true);
    
    private int HOST_ID = 1;

    @Before
    public void setUp() throws Exception {
        doReturn(em).when(tblHostsJpaController).getEntityManager();
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
    public void testCreate() throws CryptographyException {
        TblHosts tblHost = new TblHosts(HOST_ID);
        tblHostsJpaController.create(tblHost);
        verify(em).persist(tblHost);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testEdit() throws IllegalOrphanException, NonexistentEntityException, ASDataException {
        TblHosts tblHost = new TblHosts(HOST_ID);
        TblHosts persistentTblHosts = new TblHosts(HOST_ID);
        persistentTblHosts.setDescription("test");
        doReturn(persistentTblHosts).when(em).find(TblHosts.class, HOST_ID);
        doReturn(tblHost).when(tblHostsJpaController).findTblHosts(HOST_ID);
        tblHostsJpaController.edit(tblHost);
        verify(em).merge(tblHost);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testDestroy() throws IllegalOrphanException, NonexistentEntityException {
        TblHosts tblHost = new TblHosts(HOST_ID);
        doReturn(tblHost).when(em).getReference(TblHosts.class, HOST_ID);
        tblHostsJpaController.destroy(HOST_ID);
        verify(em).remove(tblHost);
        verify(em).close();
        verify(transaction).begin();
        verify(transaction).commit();
    }
}
