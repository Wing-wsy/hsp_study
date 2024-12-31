package com.example.hxds.bff.customer.service;

import com.example.hxds.bff.customer.controller.form.ConfirmArriveStartPlaceForm;
import com.example.hxds.bff.customer.controller.form.CreateNewOrderForm;
import com.example.hxds.bff.customer.controller.form.DeleteUnAcceptOrderForm;
import com.example.hxds.bff.customer.controller.form.HasCustomerCurrentOrderForm;
import com.example.hxds.bff.customer.controller.form.SearchOrderByIdForm;
import com.example.hxds.bff.customer.controller.form.SearchOrderForMoveByIdForm;
import com.example.hxds.bff.customer.controller.form.SearchOrderStatusForm;

import java.util.HashMap;

public interface OrderService {
    public HashMap createNewOrder(CreateNewOrderForm form);

    public Integer searchOrderStatus(SearchOrderStatusForm form);

    public String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);

    public HashMap hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form);

    public HashMap searchOrderForMoveById(SearchOrderForMoveByIdForm form);

    public boolean confirmArriveStartPlace(ConfirmArriveStartPlaceForm form);

    public HashMap searchOrderById(SearchOrderByIdForm form);


}

