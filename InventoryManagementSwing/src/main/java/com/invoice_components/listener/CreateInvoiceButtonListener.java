package com.invoice_components.listener;

import com.constant.InvoiceType;
import com.dao.InvoiceDao;
import com.entity.Invoice;
import com.entity.InvoiceIN;
import com.entity.InvoiceItem;
import com.entity.InvoiceOUT;
import com.google.common.collect.ImmutableMap;
import com.invoice_components.combo_box.InvoiceTypeComboBox;
import com.invoice_components.table.InvoiceTable;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import static com.invoice_components.listener.CreateInvoiceButtonListener.CREATE_INVOICE_BUTTON_LISTENER_BEAN;

@Component(CREATE_INVOICE_BUTTON_LISTENER_BEAN)
public class CreateInvoiceButtonListener implements ActionListener {

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;
    private InvoiceTable invoiceTable;
    private InvoiceTypeComboBox invoiceTypeComboBox;
    private Map<InvoiceType, Invoice> invoiceMap = ImmutableMap.of(InvoiceType.IN, new InvoiceIN(),
            InvoiceType.OUT, new InvoiceOUT());
    public static final String CREATE_INVOICE_BUTTON_LISTENER_BEAN = "createInvoiceButtonListener";
    private static final String TAB_CHAR = "\t";

    @Override
    public void actionPerformed(ActionEvent e) {
        Invoice invoice = invoiceMap.get(invoiceTypeComboBox.getSelectedItem());
        Set<InvoiceItem> invoiceItems = new HashSet<>(invoiceTable.getInvoiceItems());
        if (invoiceItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your invoice is empty!");
            return;
        }
        StringBuilder message = invoiceInfo(invoiceItems);
        invoice.setInvoiceItems(invoiceItems);
        invoiceDao.saveInvoice(invoice);
        invoiceTable.setInvoiceItems(new ArrayList<>());
        invoiceTable.refreshModel();
        JOptionPane.showMessageDialog(null,message);
    }

    public StringBuilder invoiceInfo(Set<InvoiceItem> invoiceItems){
        StringBuilder message = new StringBuilder("You invoice is : ");
        for (InvoiceItem invoiceItem : invoiceItems){
            message.append(TAB_CHAR)
                    .append(" product - ' " + invoiceItem.getProduct().getProductName() + " '")
                    .append(" with quantity - ' " + invoiceItem.getProductQuantity() + " ';")
                    .append(TAB_CHAR);
        }
        return message;
    }

    public void setInvoiceTable(InvoiceTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public void setInvoiceTypeComboBox(InvoiceTypeComboBox invoiceTypeComboBox) {
        this.invoiceTypeComboBox = invoiceTypeComboBox;
    }
}
