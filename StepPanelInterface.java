package gui;

import model.AppState;

/**
 * Common contract for all wizard step panels.
 * NOTE: named validateStep() to avoid conflict with java.awt.Container.validate()
 */
public interface StepPanelInterface {
    void   refresh(AppState state);
    String validateStep();
    void   collectData(AppState state);
}
