package com.gather.jsfmatrix.core.view;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectItems;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;

import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.contextmenu.ContextMenu;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.dock.Dock;
import org.primefaces.component.editor.Editor;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.stack.Stack;

import com.gather.jsfmatrix.core.listener.TitlePopupListener;
import com.sun.faces.application.ApplicationImpl;

public class PrimeFacesUIComponentsFactory {

    public static final int WIDGET_WIDTH = 315;
    public static final int WIDGET_HEIGHT = 220;
    public static final int CALENDAR_WIDTH = 300;

    public static UIOutput createUIOutput(FacesContext fc) {
        Application application = fc.getApplication();

        return (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
    }

    public static MenuItem createMenuItem(FacesContext fc) {
        Application application = fc.getApplication();

        return (MenuItem) application.createComponent(MenuItem.COMPONENT_TYPE);
    }

    public static HtmlOutcomeTargetLink createHtmlOutcomeTargetLink(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlOutcomeTargetLink) application.createComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE);
    }

    public static Editor createEditor(FacesContext fc) {
        Application application = fc.getApplication();

        return (Editor) application.createComponent(Editor.COMPONENT_TYPE);
    }

    public static ContextMenu createContextMenu(FacesContext fc) {
        Application application = fc.getApplication();

        return (ContextMenu) application.createComponent(ContextMenu.COMPONENT_TYPE);
    }

    public static HtmlPanelGrid createHtmlPanelGrid(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
    }

    public static Column createColumn(FacesContext fc) {
        Application application = fc.getApplication();

        return (Column) application.createComponent(Column.COMPONENT_TYPE);
    }

    public static Column createColumn(FacesContext fc,
                                      UIComponent child) {
        Column c = PrimeFacesUIComponentsFactory.createColumn(fc);
        c.getChildren().add(child);

        return c;
    }

    public static HtmlInputTextarea createHtmlInputTextarea(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlInputTextarea) application.createComponent(HtmlInputTextarea.COMPONENT_TYPE);
    }

    public static HtmlInputText createHtmlInputText(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlInputText) application.createComponent(HtmlInputText.COMPONENT_TYPE);
    }

    public static HtmlOutputLink createHtmlOutputLink(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlOutputLink) application.createComponent(HtmlOutputLink.COMPONENT_TYPE);
    }

    public static HtmlOutputText createInclude(FacesContext fc,
                                               String url) {
        HtmlOutputText ot = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc);
        ot.setEscape(false);

        String output = "<ui:include ";
        output += "src=\"./app/" +
                url +
                "\"";

        output += "></ui:include>";

        ot.setValue(output);

        return ot;
    }

    public static HtmlOutputText createIFrame(FacesContext fc,
                                              String url) {
        HtmlOutputText ot = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc);
        ot.setEscape(false);

        String output = "<iframe ";
        output += "src=\"./app/" +
                url +
                "/widget.xhtml" +
                "\"";

        output += " height=\"100%\"";
        output += " width=\"100%\"";

        output += " scrolling=\"no\"";

        output += " />";

        ot.setValue(output);

        return ot;
    }

    public static HtmlOutputText createHtmlOutputText(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
    }

    public static HtmlOutputText createHtmlOutputText(FacesContext fc,
                                                      String text) {
        HtmlOutputText o = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc);
        o.setId(fc.getViewRoot().createUniqueId() +
                        "_" +
                        java.util.Calendar.getInstance().getTimeInMillis());
        o.setValue(text);

        return o;
    }

    public static CommandLink createCommandLink(FacesContext fc) {
        Application application = fc.getApplication();

        return (CommandLink) application.createComponent(CommandLink.COMPONENT_TYPE);
    }

    public static CommandButton createCommandButton(FacesContext fc) {
        Application application = fc.getApplication();

        return (CommandButton) application.createComponent(CommandButton.COMPONENT_TYPE);
    }

    public static HtmlCommandLink createHtmlCommandLink(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlCommandLink) application.createComponent(HtmlCommandLink.COMPONENT_TYPE);
    }

    public static HtmlGraphicImage createHtmlGraphicImage(FacesContext fc) {
        Application application = fc.getApplication();

        return (HtmlGraphicImage) application.createComponent(HtmlGraphicImage.COMPONENT_TYPE);
    }

    public static Editor createEditor(FacesContext fc,
                                      Integer width,
                                      Integer height) {
        Editor e = PrimeFacesUIComponentsFactory.createEditor(fc);

        return e;
    }

    public static Dock createDock(FacesContext fc) {
        Application application = fc.getApplication();

        return (Dock) application.createComponent(Dock.COMPONENT_TYPE);
    }

    public static Dashboard createDashboard(FacesContext fc) {
        Application application = fc.getApplication();

        return (Dashboard) application.createComponent(Dashboard.COMPONENT_TYPE);
    }

    public static Dashboard createMainDashboard(FacesContext fc) {
        Dashboard d = PrimeFacesUIComponentsFactory.createDashboard(fc);

        return d;
    }

    public static Panel createPanel(FacesContext fc) {
        ApplicationImpl application = (ApplicationImpl) fc.getApplication();

        return (Panel) application.createComponent(Panel.COMPONENT_TYPE);
    }

    public static Panel createDefaultWidgetPanel(FacesContext fc) {
        Panel p = PrimeFacesUIComponentsFactory.createPanel(fc);
        p.setId("panel" +
                        fc.getViewRoot().createUniqueId() +
                        "_" +
                        java.util.Calendar.getInstance().getTimeInMillis());
        p.setStyle("height: " +
                           WIDGET_HEIGHT +
                           "px; width: " +
                           WIDGET_WIDTH +
                           "px; margin: 5px;");

        return p;
    }

    public static Panel createDefaultWidgetPanel(FacesContext fc,
                                                 String title) {
        Panel p = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc);
        p.setId("panelcito_" +
                        fc.getViewRoot().createUniqueId() +
                        "_" +
                        java.util.Calendar.getInstance().getTimeInMillis());
        p.setTransient(true);

        HtmlOutputText ot = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc,
                                                                               title.length() > 30
                                                                                       ? title.substring(0,
                                                                                                         29) +
                                                                                       "..."
                                                                                       : title.toString());
        ot.setId(fc.getViewRoot().createUniqueId() +
                         "_" +
                         java.util.Calendar.getInstance().getTimeInMillis());
        ot.setTitle(title);
        ot.setTransient(true);

        AjaxBehavior ab = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
        ab.addAjaxBehaviorListener(new TitlePopupListener());
        ab.setImmediate(true);

        HtmlOutputLink ol = PrimeFacesUIComponentsFactory.createHtmlOutputLink(fc);
        ol.setId("link_de_panelcito_cli" +
                         fc.getViewRoot().createUniqueId() +
                         java.util.Calendar.getInstance().getTimeInMillis());
        ol.setTransient(true);
        ol.setValue("#");
        ol.setStyle("text-decoration: none;");
        ol.setOnmouseup("titleChangeWidgetdialog.show(); return false;");

        ol.addClientBehavior("click",
                             ab);
        ol.getChildren().add(ot);

        p.getFacets().put("header",
                          ol);

        return p;
    }

    public static Panel createDefaultWidgetPanel(FacesContext fc,
                                                 String header,
                                                 String footer) {
        Panel p = PrimeFacesUIComponentsFactory.createDefaultWidgetPanel(fc,
                                                                         header);

        HtmlOutputText otf = PrimeFacesUIComponentsFactory.createHtmlOutputText(fc,
                                                                                footer);
        otf.setId(fc.getViewRoot().createUniqueId() +
                          java.util.Calendar.getInstance().getTimeInMillis());
        otf.setTransient(true);

        p.getFacets().put("footer",
                          otf);

        return p;
    }

    public static Calendar createCalendar(FacesContext fc) {
        Application application = fc.getApplication();

        return (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
    }

    public static Calendar createDefaultCalendar(FacesContext fc) {
        Calendar c = PrimeFacesUIComponentsFactory.createCalendar(fc);
        c.setMode("inline");

        return c;
    }

    public static Stack createStack(FacesContext fc) {
        Application application = fc.getApplication();

        Stack d = (Stack) application.createComponent(Stack.COMPONENT_TYPE);

        return d;
    }

    public static BreadCrumb createbreadCrumb(FacesContext fc) {
        Application application = fc.getApplication();

        BreadCrumb d = (BreadCrumb) application.createComponent(BreadCrumb.COMPONENT_TYPE);

        return d;
    }

    public static InputMask createInputMask(FacesContext fc) {
        Application application = fc.getApplication();

        InputMask d = (InputMask) application.createComponent(InputMask.COMPONENT_TYPE);

        return d;
    }

    public static HtmlOutputLabel createHtmlOutputLabel(FacesContext fc) {
        Application application = fc.getApplication();

        HtmlOutputLabel d = (HtmlOutputLabel) application.createComponent(HtmlOutputLabel.COMPONENT_TYPE);

        return d;
    }

    public static HtmlSelectOneMenu createHtmlSelectOneMenu(FacesContext fc) {
        Application application = fc.getApplication();

        HtmlSelectOneMenu d = (HtmlSelectOneMenu) application.createComponent(HtmlSelectOneMenu.COMPONENT_TYPE);

        return d;
    }

    public static UISelectItems createUISelectItems(FacesContext fc) {
        Application application = fc.getApplication();

        UISelectItems d = (UISelectItems) application.createComponent(UISelectItems.COMPONENT_TYPE);

        return d;
    }

    public static Dialog createDialog(FacesContext fc) {
        Application application = fc.getApplication();

        Dialog d = (Dialog) application.createComponent(Dialog.COMPONENT_TYPE);

        return d;
    }

    public static DataTable createDataTable(FacesContext fc) {
        Application application = fc.getApplication();

        DataTable d = (DataTable) application.createComponent(DataTable.COMPONENT_TYPE);

        return d;
    }

    public static UISelectItems creatUISelectItems(FacesContext fc) {
        Application application = fc.getApplication();

        UISelectItems d = (UISelectItems) application.createComponent(UISelectItems.COMPONENT_TYPE);

        return d;
    }
}
