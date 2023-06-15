package com.x.print.ui.screen.main;

import com.vaadin.ui.UI;
import io.jmix.ui.AppUI;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.theme.ThemeVariantsManager;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;

    @Autowired
    protected ThemeVariantsManager heliumThemeVariantsManager;
    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    @Subscribe("popupButton.logoutAction")
    public void onPopupButtonLogoutAction(Action.ActionPerformedEvent event) {
        AppUI ui = AppUI.getCurrent();
        if (ui == null) {
            throw new IllegalStateException("Logout button is not attached to UI");
        }
        ui.getApp().logout();
    }

    @Subscribe("fullScreenButton")
    public void onFullScreenButtonClick(Button.ClickEvent event) {
        UI.getCurrent().getPage().getJavaScript().execute(
                "var dom = document.getElementsByTagName('body')[0];" +
                        "if (document.fullscreenElement === null) {\n" +
                        "if (dom.requestFullscreen) {\n" +
                        "    dom.requestFullscreen();\n" +
                        "} else if (dom.msRequestFullscreen) {\n" +
                        "    dom.msRequestFullscreen();\n" +
                        "} else if (dom.mozRequestFullScreen) {\n" +
                        "    dom.mozRequestFullScreen();\n" +
                        "} else if (dom.webkitRequestFullscreen) {\n" +
                        "    dom.webkitRequestFullscreen();\n" +
                        "}" +
                        "  }\n" +
                        "  else\n" +
                        " {\n" +
                        "  if (document.exitFullscreen) {\n" +
                        "      document.exitFullscreen();\n" +
                        "  } else if (document.msExitFullscreen) {\n" +
                        "      document.msExitFullscreen();\n" +
                        "  } else if (document.mozCancelFullScreen) {\n" +
                        "      document.mozCancelFullScreen();\n" +
                        "  } else if (document.webkitExitFullscreen) {\n" +
                        "      document.webkitExitFullscreen();\n" +
                        "  }" +
                        " }");
    }


}
