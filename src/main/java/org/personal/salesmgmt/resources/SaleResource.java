package org.personal.salesmgmt.resources;

import org.personal.salesmgmt.domain.Sales;
import org.personal.salesmgmt.exceptions.custom.ResourceNotFoundException;
import org.personal.salesmgmt.service.SaleService;
import org.personal.salesmgmt.service.impl.SaleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(SaleResource.BASE_URL)
public class SaleResource {

    public static final String BASE_URL = "/api/sales";

    private final SaleService saleService = new SaleServiceImpl();

    @GetMapping
    public ResponseEntity<List<Sales>> findAll() {
        final List<Sales> sales = saleService.findAll();
        return ResponseEntity.ok().body(sales);
    }

    @PostMapping
    public ResponseEntity<Sales> create(@RequestBody Sales sales) throws URISyntaxException {
        Sales sale = saleService.save(sales);
        return ResponseEntity.created(new URI("/api/sales")).body(sale);
    }

    @PutMapping("/{goodsId}")
    public ResponseEntity<Sales> update(@RequestBody Sales sales, @PathVariable String goodsId) throws URISyntaxException {
        Optional<Sales> sale = saleService.findById(goodsId);
        if (sale.isPresent()) {
            sales = saleService.update(sales);
            return ResponseEntity.created(new URI("/api/sales")).body(sales);
        }
        throw new ResourceNotFoundException("Sales resource with given id not found");
    }

    @GetMapping("/{goodsId}")
    public ResponseEntity<Sales> findById(@PathVariable String goodsId) {
        Optional<Sales> sale = saleService.findById(goodsId);
        if (sale.isPresent()) {
            return ResponseEntity.ok().body(sale.get());
        }
        throw new ResourceNotFoundException("Sales resource with given id not found");
    }

    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> remove(@PathVariable String goodsId) {
        saleService.remove(goodsId);
        return ResponseEntity.ok().body("Sales Deleted Successfully");
    }

}
