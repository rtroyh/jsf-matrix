package com.gather.jsfmatrix.core.view.uiobject;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.CMDeleteWidgetListener;
import com.gather.jsfmatrix.core.listener.DashBoardPanelCloseListener;
import com.gather.jsfmatrix.core.listener.TitlePopupListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.IViewer;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.util.MapPropertyUtil;
import com.gather.jsfspringcommons.utils.BeanUtil;
import org.apache.log4j.Logger;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.faces.application.FacesMessage;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UIDashBoard implements UIJSFObject {
    private static final Logger LOG = Logger.getLogger(UIDashBoard.class);

    private Dashboard dashBoard;
    private DashboardModel dashBoardModel;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    DashboardModel getDashBoardModel() {
        if (this.dashBoardModel == null) {
            this.dashBoardModel = new DefaultDashboardModel();
        }

        return dashBoardModel;
    }

    void setDashBoardModel(DashboardModel dashBoardModel) {
        this.dashBoardModel = dashBoardModel;
    }

    Dashboard getDashBoard() {
        if (this.dashBoard == null) {
            this.dashBoard = PrimeFacesUIComponentsFactory.createMainDashboard(FacesContext.getCurrentInstance());
            this.dashBoard.setId("myDashBoard" +
                                         FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         "_" +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.dashBoard.setModel(this.getDashBoardModel());
            this.dashBoard.setTransient(true);
        }

        return this.dashBoard;
    }

    public void setDashBoard(Dashboard dashBoard) {
        this.dashBoard = dashBoard;
    }

    @Override
    public UIComponent getComponent() {
        return this.getDashBoard();
    }

    @Override
    public void setComponent(UIComponent component) {
        this.dashBoard = (Dashboard) component;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        LOG.info("INICIO POBLAMIENTO DASHBOARD");

        if (!recipe.isEmpty()) {
            if (recipe.containsKey(Ingredients.APPLICATION_LIST) && recipe.containsKey(Ingredients.UNIDIMENSIONAL_LIST)) {
                this.resetState();

                FacesContext fc = FacesContext.getCurrentInstance();

                List<IMatrixApplication> data = (List<IMatrixApplication>) recipe.get(Ingredients.APPLICATION_LIST);
                List<List<Object>> properties = (List<List<Object>>) recipe.get(Ingredients.UNIDIMENSIONAL_LIST);

                final List<Object> propertiesList = properties.get(0);
                final Boolean validateData = Validator.validateList(data);
                final Boolean validateProperties = Validator.validateList(properties);
                final Boolean validatePropertiesList = Validator.validateList(propertiesList);

                if (validateProperties) {
                    if (Validator.validateList(propertiesList,
                                               4)) {
                        if (Validator.validateString(propertiesList.get(2))) {

                            final String title = propertiesList.get(2).toString();

                            String summary = "";
                            if (Validator.validateString(propertiesList.get(3))) {
                                summary = propertiesList.get(3).toString();
                            }

                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                title,
                                                                summary);
                            fc.addMessage("noticia",
                                          msg);
                        }
                    }
                }

                if (validateData && validateProperties && validatePropertiesList) {

                    final long timeInMillis = Calendar.getInstance().getTimeInMillis();
                    if (Validator.validateInteger(propertiesList.get(0)) && Validator.validateInteger(propertiesList.get(1)) && propertiesList.get(0).equals(2)) {//1=widget, 2=iconos
                        populateWithIcon(fc,
                                         data,
                                         propertiesList,
                                         timeInMillis);
                    } else {
                        populateWithPanels(fc,
                                           data,
                                           propertiesList,
                                           timeInMillis);
                    }
                }
            }
        }
    }

    private void populateWithPanels(FacesContext fc,
                                    List<IMatrixApplication> data,
                                    List<Object> propertiesList,
                                    long timeInMillis) {
        Integer total = (Integer) propertiesList.get(1) - this.getDashBoardModel().getColumnCount();

        for (int x = 0; x < total; x++) {
            this.getDashBoardModel().addColumn(new DefaultDashboardColumn());
        }

        for (IMatrixApplication ma : data) {
            Panel item = null;

            if (!ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JAVA_ID).equals(0)) {
                item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc,
                                                                              ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE).toString());

                item.setStyle(item.getStyle() + " background: white;");
                if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.BORRABLE).equals(1)) {
                    item.setClosable(true);

                    AjaxBehavior ab = getAjaxBehavior(fc);

                    item.addClientBehavior("close",
                                           ab);
                }
            } else {
                try {
                    item = getPanel(fc,
                                    timeInMillis);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }

            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.JSF_CLIENT_ID,
                                                                               item.getId());

            if (!ma.getMatrixApplicationHandler().getPropertyValue(Property.MATRIX_ID).equals(0)) {

                //LA OBTENCION DE VIEWER MULTIPLES CON UN UNICO MANAGEDBEAN DEBE SER HECHO ACA Y SOLO ACA.
                if (ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH) != null) {
                    try {
                        if (BeanUtil.getBean(FacesContext.getCurrentInstance(),
                                             ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH).toString()) != null) {
                            IMatrixApplication maInst = (IMatrixApplication) BeanUtil.getBean(FacesContext.getCurrentInstance(),
                                                                                              ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH).toString());
                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                           maInst.getMatrixApplicationHandler().getApplicationModel().getPropertyMap());
                            IViewer v = maInst.getMatrixApplicationHandler().getIApplicationView().getView(ma.getMatrixApplicationHandler().getPropertyValue(Property.MATRIX_ID).toString());

                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                           v.getUIJSFObject().getApplicationModel().getPropertyMap());

                            v.getUIJSFObject().populate(null);

                            item.getChildren().add(v.getUIJSFObject().getComponent());
                        }
                    } catch (BeanCreationException e) {
                        LOG.warn(e.getMessage());
                    } catch (NoSuchBeanDefinitionException e) {
                        LOG.warn(e.getMessage());
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            }

            this.getDashBoard().getChildren().add(item);

            Integer item_pos = (Integer) ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.POSITION) - 1;
            Integer col_index = item_pos % (Integer) propertiesList.get(1);
            Integer item_index = (item_pos - col_index) / (Integer) propertiesList.get(1);

            this.getDashBoardModel().getColumn(col_index).addWidget(item_index,
                                                                    item.getId());
        }
    }

    private Panel getPanel(FacesContext fc,
                           long timeInMillis) {
        Panel item;
        item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc);
        item.setId("hidden_panels_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        item.setStyle("border: 0; height: " + PrimeFacesUIComponentsFactory.WIDGET_HEIGHT + "px; width: " + PrimeFacesUIComponentsFactory.WIDGET_WIDTH + "px; margin: 5px;");
        item.setTransient(true);
        return item;
    }

    private AjaxBehavior getAjaxBehavior(FacesContext fc) {
        AjaxBehavior ab = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
        ab.addAjaxBehaviorListener(new DashBoardPanelCloseListener());
        ab.setImmediate(true);
        ab.setTransient(true);
        return ab;
    }

    private void populateWithIcon(FacesContext fc,
                                  List<IMatrixApplication> data,
                                  List<Object> propertiesList,
                                  long timeInMillis) {
        Integer total = (Integer) propertiesList.get(1) - this.getDashBoardModel().getColumnCount();

        for (int x = 0; x < total; x++) {
            this.getDashBoardModel().addColumn(new DefaultDashboardColumn());
        }

        for (IMatrixApplication ma : data) {
            Panel item = getPanel(fc,
                                  propertiesList,
                                  timeInMillis);

            HtmlGraphicImage imageBotonQuitar = getHtmlGraphicImage(fc,
                                                                    timeInMillis);

            aplicarImagen(fc,
                          ma,
                          imageBotonQuitar);

            CommandLink botonQuitar = getCommandLinkBotonQuitar(fc,
                                                                timeInMillis,
                                                                ma,
                                                                imageBotonQuitar);

            if (!ma.getMatrixApplicationHandler().getPropertyValue(Property.BORRABLE).equals(1)) {
                botonQuitar.setDisabled(true);
            }

            HtmlPanelGrid panelBotonQuitar = getHtmlPanelGridPanelBotonQuitar(fc,
                                                                              timeInMillis,
                                                                              botonQuitar);

            item.getChildren().add(panelBotonQuitar);

            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.JSF_CLIENT_ID,
                                                                               item.getId());

            if (!ma.getMatrixApplicationHandler().getPropertyValue(Property.MATRIX_ID).equals(0)) {

                //LA OBTENCION DE VIEWER MULTIPLES CON UN UNICO MANAGEDBEAN DEBE SER HECHO ACA Y SOLO ACA.
                if (ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH) != null) {
                    try {
                        Object maBean = BeanUtil.getBean(FacesContext.getCurrentInstance(),
                                                         ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH).toString());
                        if (maBean != null) {

                            IMatrixApplication maInst = (IMatrixApplication) maBean;

                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                           maInst.getMatrixApplicationHandler().getApplicationModel().getPropertyMap());

                            IViewer v = maInst.getMatrixApplicationHandler().getIApplicationView().getView(ma.getMatrixApplicationHandler().getPropertyValue(Property.MATRIX_ID).toString());

                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                           v.getUIJSFObject().getApplicationModel().getPropertyMap());

                            v.getUIJSFObject().populate(null);

                            item.getChildren().add(v.getUIJSFObject().getComponent());

                            String title = ma.getMatrixApplicationHandler().getPropertyValue(Property.TITLE).toString();
                            HtmlOutputText ot = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc,
                                                                                                   title.length() > 20 ? title.substring(0,
                                                                                                                                         19) + "..." : title);
                            ot.setTitle(ma.getMatrixApplicationHandler().getPropertyValue(Property.TITLE).toString());
                            ot.setStyleClass("ui-dashboard-icon-panel");
                            ot.setTransient(true);

                            AjaxBehavior ab = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
                            ab.addAjaxBehaviorListener(new TitlePopupListener());
                            ab.setImmediate(true);
                            ab.setTransient(true);

                            HtmlOutputLink ol = PrimeFacesUIComponentsFactory.createHtmlOutputLink(fc);
                            ol.setId("link_de_panelcito_cli_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
                            ol.setStyle("text-decoration: none;");
                            ol.setTransient(true);
                            ol.setValue("#");
                            ol.setOnmouseup("titleChangeWidgetdialog.show(); return false;");
                            ol.addClientBehavior("click",
                                                 ab);
                            ol.getChildren().add(ot);

                            HtmlPanelGrid panelTexto = getHtmlPanelGridTexto(fc,
                                                                             timeInMillis,
                                                                             ol);

                            item.getChildren().add(panelTexto);
                        }
                    } catch (BeanCreationException e) {
                        LOG.warn(e.getMessage());
                    } catch (NoSuchBeanDefinitionException e) {
                        LOG.warn(e.getMessage());
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            }

            this.getDashBoard().getChildren().add(item);

            Integer item_pos = (Integer) ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.POSITION) - 1;
            Integer col_index = item_pos % (Integer) propertiesList.get(1);
            Integer item_index = (item_pos - col_index) / (Integer) propertiesList.get(1);

            this.getDashBoardModel().getColumn(col_index).addWidget(item_index,
                                                                    item.getId());
        }
    }

    private HtmlPanelGrid getHtmlPanelGridTexto(FacesContext fc,
                                                long timeInMillis,
                                                HtmlOutputLink ol) {
        HtmlPanelGrid panelTexto = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(fc);
        panelTexto.setId("panelTexto_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        panelTexto.setStyle("width: 100%; text-align: center;");
        panelTexto.setColumns(1);
        panelTexto.setCellpadding("0");
        panelTexto.setCellspacing("0");
        panelTexto.setTransient(true);
        panelTexto.getChildren().add(ol);
        return panelTexto;
    }

    private void aplicarImagen(FacesContext fc,
                               IMatrixApplication ma,
                               HtmlGraphicImage imageBotonQuitar) {
        ResourceHandler rh = fc.getApplication().getResourceHandler();

        if (ma.getMatrixApplicationHandler().getPropertyValue(Property.BORRABLE).equals(1)) {
            Resource r = rh.createResource("images/borrar.png",
                                           "gather");

            if (r != null) {
                imageBotonQuitar.setUrl(r.getRequestPath());
            }
        } else {
            Resource r = rh.createResource("images/vacio5x5.png",
                                           "gather");

            if (r != null) {
                imageBotonQuitar.setUrl(r.getRequestPath());
            }
        }
    }

    private HtmlPanelGrid getHtmlPanelGridPanelBotonQuitar(FacesContext fc,
                                                           long timeInMillis,
                                                           CommandLink botonQuitar) {
        HtmlPanelGrid panelBotonQuitar = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(fc);
        panelBotonQuitar.setId("panelBotones_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        panelBotonQuitar.setStyle("float: right; margin: 0px; border:0; width: 15px; height: 15px; text-align: center;");
        panelBotonQuitar.setColumns(1);
        panelBotonQuitar.setCellpadding("0");
        panelBotonQuitar.setCellspacing("0");
        panelBotonQuitar.setTransient(true);
        panelBotonQuitar.getChildren().add(botonQuitar);
        return panelBotonQuitar;
    }

    private CommandLink getCommandLinkBotonQuitar(FacesContext fc,
                                                  long timeInMillis,
                                                  IMatrixApplication ma,
                                                  HtmlGraphicImage imageBotonQuitar) {
        CommandLink botonQuitar = PrimeFacesUIComponentsFactory.createCommandLink(FacesContext.getCurrentInstance());
        botonQuitar.setId("botonQuitarWidget_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        botonQuitar.setStyle("float: right; margin: 0px; border:0; text-decoration: none;");
        botonQuitar.addActionListener(new CMDeleteWidgetListener());
        botonQuitar.getAttributes().put("IMatrixApplicationModel",
                                        ma);
        botonQuitar.setTitle("Quitar icono");
        botonQuitar.setTransient(true);
        botonQuitar.setUpdate("myForm");
        botonQuitar.setProcess("@this");
        botonQuitar.getChildren().add(imageBotonQuitar);
        return botonQuitar;
    }

    private HtmlGraphicImage getHtmlGraphicImage(FacesContext fc,
                                                 long timeInMillis) {
        HtmlGraphicImage imageBotonQuitar = PrimeFacesUIComponentsFactory.createHtmlGraphicImage(FacesContext.getCurrentInstance());
        imageBotonQuitar.setId("imagenQuitarWG_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        imageBotonQuitar.setStyle("width: 15px; height: 15px; margin: 0px; border:0; text-decoration: none;");
        imageBotonQuitar.setTransient(true);
        return imageBotonQuitar;
    }

    private Panel getPanel(FacesContext fc,
                           List<Object> propertiesList,
                           long timeInMillis) {
        Panel item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc);
        item.setId("WIDGET_" + fc.getViewRoot().createUniqueId() + "_" + timeInMillis);
        item.setStyle("height: 140px; width: " + (950 / Integer.valueOf(propertiesList.get(1).toString()) - 10) + "px; margin: 2px;");
        item.setStyleClass("ui-dashboard-icon-panel");
        item.setClosable(false);
        item.setTransient(true);
        item.setToggleable(false);
        return item;
    }

    @Override
    public void resetState() {
        this.getDashBoard().getChildren().clear();
        this.setDashBoardModel(null);
        this.getDashBoard().setModel(this.getDashBoardModel());
    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getApplicationModel().getPropertyValue(property);
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }
}
