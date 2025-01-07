package com.example.hxds.bff.customer.feign.fallback;

import com.example.hxds.bff.customer.controller.form.DeleteCustomerCarByIdForm;
import com.example.hxds.bff.customer.controller.form.InsertCustomerCarForm;
import com.example.hxds.bff.customer.controller.form.LoginForm;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.controller.form.SearchCustomerCarListForm;
import com.example.hxds.bff.customer.feign.CstServiceApi;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Component;

@Component
public class CstServiceApiFallback implements CstServiceApi {
    @Override
    public R registerNewCustomer(RegisterNewCustomerForm form) {
        return R.error("服务端异常，触发客户端降级");
    }

    @Override
    public R login(LoginForm form) {
        return R.error("服务端异常，触发客户端降级");
    }

    @Override
    public R insertCustomerCar(InsertCustomerCarForm form) {
        return R.error("服务端异常，触发客户端降级");
    }

    @Override
    public R searchCustomerCarList(SearchCustomerCarListForm form) {
        return R.error("服务端异常，触发客户端降级");
    }

    @Override
    public R deleteCustomerCarById(DeleteCustomerCarByIdForm form) {
        return R.error("服务端异常，触发客户端降级");
    }
}
