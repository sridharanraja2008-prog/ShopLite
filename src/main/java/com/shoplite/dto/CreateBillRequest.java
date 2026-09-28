package com.shoplite.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CreateBillRequest {

    @NotEmpty(message = "Bill must contain at least one item")
    @Valid
    private List<BillItemRequest> items;

    public CreateBillRequest() {
    }

    public CreateBillRequest(List<BillItemRequest> items) {
        this.items = items;
    }

    public List<BillItemRequest> getItems() {
        return items;
    }

    public void setItems(List<BillItemRequest> items) {
        this.items = items;
    }
}
