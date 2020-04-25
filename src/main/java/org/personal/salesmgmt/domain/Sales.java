package org.personal.salesmgmt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sales {

    private String goodsId;

    private String description;

    private Double pricePerUnit;

    private Integer quantity;

    private Double totalSales;
}
