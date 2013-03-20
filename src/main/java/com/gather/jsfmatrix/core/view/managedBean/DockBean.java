package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.*;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.DockServices;
import com.gather.jsfmatrix.core.view.JSFViewer;
import com.gather.jsfmatrix.core.view.uiobject.UIDock;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DockBean extends JSFViewer {
    private static final Logger LOG = Logger.getLogger(DockBean.class);

    private final DataSource ds;
    private IApplicationModel applicationModel;
    private Matrix matrix;
    private DockServices dockService;

    public DockBean(DataSource ds) {
        super(new UIDock());
        this.ds = ds;
    }

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    Matrix getMatrix() {
        if (this.matrix == null) {
            this.matrix = new Matrix();
        }

        return this.matrix;
    }

    public void setMatrixBean(Matrix matrix) {
        this.matrix = matrix;
    }

    private DockServices getDockServices() {
        if (this.dockService == null) {
            this.dockService = new DockServices(this.ds);
        }

        return this.dockService;
    }

    private void build() {
        LOG.info("INICIO CONSTRUCCION DOCK");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null && lb.getUser().getId() != null) {
                this.getDockServices().getList().resetParameter();
                this.getDockServices().getList().addParameter("1",
                                                              lb.getUser().getId());
                this.getDockServices().getList().executeQuery();

                List<List<Object>> data = this.getDockServices().getList().getResultSetasListofList();

                this.getMatrix().getApplications().clear();

                if (Validator.validateList(data)) {
                    for (List<Object> lo : data) {
                        IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
                        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.PAQUETE,
                                                                                           lo.get(0));
                        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.TITLE,
                                                                                           lo.get(1));
                        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.ICON_PATH,
                                                                                           lo.get(2));

                        this.getMatrix().getApplications().add(ma);
                    }
                }
            }
        } catch (BeansException e) {
            LOG.error(e.getMessage());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        this.getUIJSFObject().resetState();
        this.getUIJSFObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                               this.getMatrix().getApplications()));
    }
}
