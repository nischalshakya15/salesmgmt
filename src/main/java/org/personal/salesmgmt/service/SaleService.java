package org.personal.salesmgmt.service;

import org.personal.salesmgmt.domain.Sales;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    Sales save(Sales sale);

    List<Sales> findAll();

    void remove(String goodsId);

    Optional<Sales> findById(String goodsId);

}
