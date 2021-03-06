package com.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IN")
public class InvoiceIn extends Invoice {

  @Override
  public Integer processProductQuantity(InvoiceItem invoiceItem, InventoryState inventoryState) {
    return inventoryState.getQuantity() + invoiceItem.getProductQuantity();
  }
}
