package com.x.print.test_support;

import com.x.print.PrintApplication;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Component;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class for UI integration tests.
 */
@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "MainScreen", screenBasePackages = "com.x.print.screen")
@ContextConfiguration(classes = {PrintApplication.class, UiTestAssistConfiguration.class})
@SuppressWarnings({"unchecked"})
public abstract class UiIntegrationTest {

    Screens screens;

    @BeforeEach
    void setUpScreens(Screens screens) {
        this.screens = screens;
        screens.removeAll();
    }

    /**
     * @return {@code Screens} instance to be used in tests
     */
    protected Screens getScreens() {
        return screens;
    }

    /**
     * Returns an opened screen by its class.
     * Throws an exception if not found.
     */
    protected <T> T findOpenScreen(Class<T> screenClass) {
        Screen screen = screens.getOpenedScreens().getActiveScreens().stream()
                .filter(it -> screenClass.isAssignableFrom(it.getClass()))
                .findFirst()
                .orElseThrow();

        assertThat(screen)
                .isInstanceOf(screenClass);

        return (T) screen;
    }

    /**
     * Returns a component defined in the screen by the component id.
     * Throws an exception if not found.
     */
    protected <T> T findComponent(Screen screen, String componentId) {
        Component component = screen.getWindow().getComponent(componentId);
        assertThat(component).isNotNull();
        return (T) component;
    }
}
