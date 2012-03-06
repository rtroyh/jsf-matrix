package com.gather.jsfmatrix.core.view.uiobject;

import java.util.List;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import com.gather.jsfmatrix.core.listener.DashBoardPanelCloseListener;
import org.apache.log4j.Logger;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.CMDeleteWidgetListener;
import com.gather.jsfmatrix.core.listener.TitlePopupListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.Viewer;
import com.gather.jsfmatrix.util.MapPropertyUtil;
import com.gather.jsfspringcommons.utils.BeanUtil;

public class UIDashBoard implements UIJSFObject {
    private static final Logger LOG = Logger.getLogger(UIDashBoard.class);
    private IApplicationModel applicationModel;
    private Dashboard dashBoard;
    private DashboardModel dashBoardModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public DashboardModel getDashBoardModel() {
        if (this.dashBoardModel == null) {
            this.dashBoardModel = new DefaultDashboardModel();
        }

        return dashBoardModel;
    }

    public void setDashBoardModel(DashboardModel dashBoardModel) {
        this.dashBoardModel = dashBoardModel;
    }

    public Dashboard getDashBoard() {
        if (this.dashBoard == null) {
            this.dashBoard = PrimeFacesUIComponentsFactory.createMainDashboard(FacesContext.getCurrentInstance());
            this.dashBoard.setId("myDashBoard" +
                                         FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         "_" +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.dashBoard.setModel(this.getDashBoardModel());
            //this.dashBoard.setOnReorderUpdate("mainDashBoard");
            this.dashBoard.setTransient(true);

            /*
             * FacesContext fc = FacesContext.getCurrentInstance(); MethodExpression methodExpression = fc.getApplication().getExpressionFactory().createMethodExpression(
             * fc.getELContext(), "#{portalBean.dashboardBean.handleReorder}", null, new Class[] { DashboardReorderEvent.class });
             * this.dashBoard.setReorderListener(methodExpression);
             */
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

                List<IMatrixApplication> data = (List<IMatrixApplication>) recipe.get(Ingredients.APPLICATION_LIST);
                List<List<Object>> properties = (List<List<Object>>) recipe.get(Ingredients.UNIDIMENSIONAL_LIST);

                FacesContext fc = FacesContext.getCurrentInstance();

                if (Validator.validateList(data) &&
                        Validator.validateList(properties) &&
                        Validator.validateList(properties.get(0))) {

                    if (Validator.validateInteger(properties.get(0).get(0)) &&
                            Validator.validateInteger(properties.get(0).get(1)) &&
                            properties.get(0).get(0).equals(2)) {//1=widget, 2=iconos

                        Integer total = (Integer) properties.get(0).get(1) -
                                this.getDashBoardModel().getColumnCount();

                        for (int x = 0; x < total; x++) {
                            this.getDashBoardModel().addColumn(new DefaultDashboardColumn());
                        }

                        for (IMatrixApplication ma : data) {
                            Panel item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc);
                            item.setId("WIDGET_" +
                                               fc.getViewRoot().createUniqueId() +
                                               "_" +
                                               java.util.Calendar.getInstance().getTimeInMillis());
                            item.setStyle("border: 0px solid #ABABAB; padding: 0px; height: 140px; width: " +
                                                  (950 / Integer.valueOf(properties.get(0).get(1).toString()) - 10) +
                                                  "px; margin: 2px;");
                            item.setClosable(false);
                            item.setTransient(true);
                            item.setToggleable(false);

                            HtmlGraphicImage image = PrimeFacesUIComponentsFactory.createHtmlGraphicImage(FacesContext.getCurrentInstance());
                            image.setId("imagenQuitarWG_" +
                                                FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                                "_" +
                                                java.util.Calendar.getInstance().getTimeInMillis());
                            image.setStyle("width: 15px; height: 15px; margin: 0px; border:0; text-decoration: none;");
                            image.setTransient(true);

                            CommandLink botonQuitar = PrimeFacesUIComponentsFactory.createCommandLink(FacesContext.getCurrentInstance());
                            botonQuitar.setId("botonQuitarWidget_" +
                                                      FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                                      "_" +
                                                      java.util.Calendar.getInstance().getTimeInMillis());
                            botonQuitar.setStyle("float: right; margin: 0px; border:0; text-decoration: none;");
                            botonQuitar.addActionListener(new CMDeleteWidgetListener());
                            botonQuitar.getAttributes().put("IMatrixApplicationModel",
                                                            ma);
                            botonQuitar.setTitle("Quitar icono");
                            botonQuitar.setTransient(true);
                            botonQuitar.getChildren().add(image);

                            if (ma.getMatrixApplicationHandler().getPropertyValue(Property.BORRABLE).equals(1)) {
                                image.setUrl("./images/borrar.png");
                            } else {
                                image.setUrl("./images/vacio5x5.png");
                                botonQuitar.setDisabled(true);
                            }

                            HtmlPanelGrid panelLink = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(fc);
                            panelLink.setId("panelBotones_" +
                                                    FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                                    "_" +
                                                    java.util.Calendar.getInstance().getTimeInMillis());
                            panelLink.setColumns(1);
                            panelLink.setCellpadding("0");
                            panelLink.setCellspacing("0");
                            panelLink.setStyle("float: right; margin: 0px; border:0; width: 15px; height: 15px; text-align: center;");
                            panelLink.setTransient(true);
                            panelLink.getChildren().add(botonQuitar);

                            item.getChildren().add(panelLink);

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

                                            Viewer v = maInst.getMatrixApplicationHandler().getIApplicationView().getView(ma.getMatrixApplicationHandler().getPropertyValue(
                                                    Property.MATRIX_ID).toString());

                                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                                           v.getUIObject().getApplicationModel().getPropertyMap());

                                            v.getUIObject().populate(null);

                                            item.getChildren().add((UIComponent) v.getUIObject().getComponent());

                                            String title = ma.getMatrixApplicationHandler().getPropertyValue(Property.TITLE).toString();
                                            HtmlOutputText ot = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc,
                                                                                                                   title.length() > 20
                                                                                                                           ? title.substring(0,
                                                                                                                                             19) +
                                                                                                                           "..."
                                                                                                                           : title.toString());
                                            ot.setTitle(ma.getMatrixApplicationHandler().getPropertyValue(Property.TITLE).toString());
                                            ot.setTransient(true);

                                            AjaxBehavior ab = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
                                            ab.addAjaxBehaviorListener(new TitlePopupListener());
                                            ab.setImmediate(true);
                                            ab.setTransient(true);

                                            HtmlOutputLink ol = PrimeFacesUIComponentsFactory.createHtmlOutputLink(fc);
                                            ol.setId("link_de_panelcito_cli" +
                                                             fc.getViewRoot().createUniqueId() +
                                                             "_" +
                                                             java.util.Calendar.getInstance().getTimeInMillis());
                                            ol.setTransient(true);
                                            ol.setValue("#");
                                            ol.setStyle("text-decoration: none;");
                                            ol.setOnmouseup("titleChangeWidgetdialog.show(); return false;");

                                            ol.addClientBehavior("click",
                                                                 ab);
                                            ol.getChildren().add(ot);

                                            HtmlPanelGrid panelTexto = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(fc);
                                            panelTexto.setId("panelTexto_" +
                                                                     FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                                                     "_" +
                                                                     java.util.Calendar.getInstance().getTimeInMillis());
                                            panelTexto.setColumns(1);
                                            panelTexto.setCellpadding("0");
                                            panelTexto.setCellspacing("0");
                                            panelTexto.setTransient(true);
                                            panelTexto.setStyle("width: 100%; text-align: center;");
                                            panelTexto.getChildren().add(ol);

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
                            Integer col_index = item_pos %
                                    (Integer) properties.get(0).get(1);
                            Integer item_index = (item_pos - col_index) /
                                    (Integer) properties.get(0).get(1);

                            this.getDashBoardModel().getColumn(col_index).addWidget(item_index,
                                                                                    item.getId());
                        }
                    } else {
                        Integer total = (Integer) properties.get(0).get(1) -
                                this.getDashBoardModel().getColumnCount();

                        for (int x = 0; x < total; x++) {
                            this.getDashBoardModel().addColumn(new DefaultDashboardColumn());
                        }

                        for (IMatrixApplication ma : data) {
                            Panel item = null;

                            if (!ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JAVA_ID).equals(0)) {
                                item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc,
                                                                                              ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE).toString());

                                item.setStyle(item.getStyle() +
                                                      " background: white;");
                                if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.BORRABLE).equals(1)) {
                                    item.setClosable(true);

                                    AjaxBehavior ab = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
                                    ab.addAjaxBehaviorListener(new DashBoardPanelCloseListener());
                                    ab.setImmediate(true);
                                    ab.setTransient(true);

                                    item.addClientBehavior("close",
                                                           ab);
                                }
                            } else {
                                try {
                                    item = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc);
                                    item.setId("hidden_panels_" +
                                                       fc.getViewRoot().createUniqueId() +
                                                       "_" +
                                                       java.util.Calendar.getInstance().getTimeInMillis());
                                    item.setStyle("border: 0; height: " +
                                                          PrimeFacesUIComponentsFactory.WIDGET_HEIGHT +
                                                          "px; width: " +
                                                          PrimeFacesUIComponentsFactory.WIDGET_WIDTH +
                                                          "px; margin: 5px;");
                                    item.setTransient(true);
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
                                            Viewer v = maInst.getMatrixApplicationHandler().getIApplicationView().getView(ma.getMatrixApplicationHandler().getPropertyValue(
                                                    Property.MATRIX_ID).toString());

                                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                                           v.getUIObject().getApplicationModel().getPropertyMap());

                                            v.getUIObject().populate(null);

                                            item.getChildren().add((UIComponent) v.getUIObject().getComponent());
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
                            Integer col_index = item_pos %
                                    (Integer) properties.get(0).get(1);
                            Integer item_index = (item_pos - col_index) /
                                    (Integer) properties.get(0).get(1);

                            this.getDashBoardModel().getColumn(col_index).addWidget(item_index,
                                                                                    item.getId());
                        }
                    }

                }

            }
        }
    }

    @Override
    public void resetState() {
        this.getDashBoard().getChildren().clear();
        this.setDashBoardModel(null);
        this.getDashBoard().setModel(this.getDashBoardModel());
    }

    @Override
    public void setComponent(Object o) {
        this.dashBoard = (Dashboard) o;
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
