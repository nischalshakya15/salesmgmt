package org.personal.salesmgmt.service.impl;

import org.personal.salesmgmt.domain.Sales;
import org.personal.salesmgmt.exceptions.custom.ResourceNotFoundException;
import org.personal.salesmgmt.service.SaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleServiceImpl implements SaleService {

    private static final List<Sales> sales = new ArrayList<>();

    static {
        sales.add(new Sales("G001", "Note book", 2.30, 100, 23.00));
        sales.add(new Sales("G002", "Pencils", 0.50, 1000, 500.00));
        sales.add(new Sales("G005", "Marker", 1.80, 200, 360.00));
    }

    @Override
    public Sales save(Sales sale) {
        sale.setTotalSales(sale.getPricePerUnit() * sale.getQuantity());
        sales.add(sale);
        return sales.get(sales.size() - 1);
    }

    @Override
    public List<Sales> findAll() {
        return sales;
    }

    @Override
    public void remove(String goodsId) {
        Optional<Sales> optionalSale = findById(goodsId);
        if (optionalSale.isPresent()) {
            sales.remove(optionalSale.get());
        } else {
            throw new ResourceNotFoundException("Sales with given Goods ID not found");
        }
    }

    @Override
    public Sales update(Sales sale) {
        Optional<Sales> optionalSale = findById(sale.getGoodsId());
        optionalSale.ifPresent(value -> sales.set(sales.indexOf(value), sale));
        return sale;
    }

    @Override
    public Optional<Sales> findById(String goodsId) {
        return sales.stream().filter(s -> s.getGoodsId().equals(goodsId)).findAny();
    }
}
