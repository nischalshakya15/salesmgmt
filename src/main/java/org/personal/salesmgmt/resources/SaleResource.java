package org.personal.salesmgmt.resources;

import org.personal.salesmgmt.domain.Sales;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(SaleResource.BASE_URL)
public class SaleResource {

    public static final String BASE_URL = "/api/sales";

    private static final List<Sales> sales = new ArrayList<>();

    static {
        sales.add(new Sales("G001", "Note book", 2.30, 100, 23.00));
        sales.add(new Sales("G002", "Pencils", 0.50, 1000, 500.00));
        sales.add(new Sales("G005", "Marker", 1.80, 200, 360.00));
    }

    @GetMapping
    public ResponseEntity<List<Sales>> findAll() {
        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("/users")
    public ResponseEntity<String> forUser() {
        return ResponseEntity.ok().body("user");
    }


    @GetMapping("/admin")
    public ResponseEntity<String> forAdmin() {
        return ResponseEntity.ok().body("admin");
    }
}
