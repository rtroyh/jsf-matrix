package com.gather.jsfmatrix.core.view.managedBean;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.service.LoginService;
import com.gather.springcommons.services.IResultSetProvider;

public class LoginBean {

    private DataSource ds;
    private LoginService loginService;
    private String userName;
    private String password;

    public LoginBean(DataSource ds) {
        this.ds = ds;
    }

    private LoginService getLoginService() {
        if (loginService == null) {
            loginService = new LoginService(this.ds);
        }

        return loginService;
    }

    public String login() {
        try {
            IResultSetProvider rp = this.getLoginService().sesionService(this.getUserName());

            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");
            if (rp != null) {
                if (Validator.validateList(rp.getResultSetasListofList()) &&
                        Validator.validateList(rp.getResultSetasListofList().get(0)) &&
                        Validator.validateNumber(rp.getResultSetasListofList().get(0).get(0))) {

                    lb.getUser().setId(Long.valueOf(rp.getResultSetasListofList().get(0).get(0).toString()));
                    return "/secured/home.jsf?faces-redirect=true";
                }
            }

        } catch (DataAccessException e) {
            Logger.getLogger(LoginBean.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(LoginBean.class).error(e.getMessage());
        }

        return "/login.jsf?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
