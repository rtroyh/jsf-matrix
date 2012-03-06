package com.gather.jsfmatrix.core.view.managedBean;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Matrix;
import com.gather.jsfmatrix.core.MatrixApplicationFactory;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.RecipeFactory;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.DockServices;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIDock;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;
import com.gather.jsfmatrix.core.view.uiobject.UIObjectType;

public class DockBean extends BaseJSFView {
    private static final Logger LOG = Logger.getLogger(DockBean.class);
    public static final String BASE_COMPONENT = "basedock";
    private DataSource ds;
    private IApplicationModel applicationModel;
    private Matrix matrix;
    private DockServices dockService;
    private EnumMap<UIObjectType, String> map = new EnumMap<UIObjectType, String>(UIObjectType.class);

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

    public Matrix getMatrix() {
        if (this.matrix == null) {
            this.matrix = new Matrix();
        }
        return this.matrix;
    }

    public void setMatrixBean(Matrix matrix) {
        this.matrix = matrix;
    }

    public EnumMap<UIObjectType, String> getMap() {
        if (this.map.get(UIObjectType.JSF) == null) {
            this.map.put(UIObjectType.JSF,
                         "FUNCIONA");
        }
        return map;
    }

    public void setMap(EnumMap<UIObjectType, String> map) {
        this.map = map;
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
        } catch (BeansException e) {
            LOG.error(e.getMessage());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public ViewType getViewType() {
        return ViewType.CORE;
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        this.getUIObject().resetState();
        this.getUIObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                               this.getMatrix().getApplications()));
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
