package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Client;

import java.util.List;

public interface ClientDao {

    List<Client> findAllClient();

    Client findFirstByOrderByUdDesc();
    Client findByClientName(String userName);

    Long getClientCount();

    Client findClientById(int theId);

    void deleteClientById(int theId);

    Client saveClient(Client theClient);
}
