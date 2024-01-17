// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.resources.joysticks;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.resources.math.Math;

/** Add your docs here. */
public class HighAltitudeGuitarHeroJoystick {
    Joystick joystick;

    public enum DriveLayout {
        ALEX_S
    }

    public enum ButtonType {
        GREEN,
        RED,
        YELLOW,
        BLUE,
        ORANGE,
        COMBO,
        BACK,
        START,
        POV_N,
        POV_NE,
        POV_E,
        POV_SE,
        POV_S,
        POV_SW,
        POV_W,
        POV_NW,
        POV_NULL,
        VOLUME_1,
        VOLUME_2
    }

    public enum AxisType {
        PICKUP_SWITCH,
        VOLUME_RAMP,
        POV_X,
        POV_Y
    }

    private HashMap<Integer, JoystickButton> availableJoystickButtons;
    private HashMap<Integer, POVButton> availablePOVButtons;
    private HashMap<Integer, Trigger> availableAxisButtons;

    private HashMap<ButtonType, Trigger> joystickButtonConfiguration;

    private HashMap<AxisType, Integer> axisConfiguration;
    private HashMap<AxisType, Double> axisDeadzoneConfiguration;
    private HashMap<AxisType, Double> axisMultiplierConfiguration;

    DriveLayout currentDriveLayout;

    public HighAltitudeGuitarHeroJoystick(int port) {
        joystick = new Joystick(port);
        configureGuitar();
        configureDefaultDeadzoneAndMultiplier(0, 1);

        currentDriveLayout = DriveLayout.ALEX_S;
    }

    void configureGuitar() {
        availableJoystickButtons = new HashMap<Integer, JoystickButton>();
        for (int i = 1; i <= joystick.getButtonCount(); i++) {
            availableJoystickButtons.put(i, new JoystickButton(joystick, i));
        }

        availablePOVButtons = new HashMap<Integer, POVButton>();
        availablePOVButtons.put(-1, new POVButton(joystick, -1));
        for (int i = 0; i <= 360; i += 45) {
            availablePOVButtons.put(i, new POVButton(joystick, i));
        }

        availableAxisButtons = new HashMap<Integer, Trigger>();
        for (int i = 0; i < 6; i++) {
            int currentPort = i;
            BooleanSupplier booleanSupplier = () -> isAxisPressed(currentPort);
            availableAxisButtons.put(i, new Trigger(booleanSupplier));
        }

        axisConfiguration = new HashMap<AxisType, Integer>();
        axisConfiguration.put(AxisType.PICKUP_SWITCH, 2);
        axisConfiguration.put(AxisType.VOLUME_RAMP, 4);

        joystickButtonConfiguration = new HashMap<ButtonType, Trigger>();
        joystickButtonConfiguration.put(ButtonType.GREEN, availableJoystickButtons.get(1));
        joystickButtonConfiguration.put(ButtonType.RED, availableJoystickButtons.get(2));
        joystickButtonConfiguration.put(ButtonType.YELLOW, availableJoystickButtons.get(4));
        joystickButtonConfiguration.put(ButtonType.BLUE, availableJoystickButtons.get(3));
        joystickButtonConfiguration.put(ButtonType.ORANGE, availableJoystickButtons.get(5));
        joystickButtonConfiguration.put(ButtonType.BACK, availableJoystickButtons.get(7));
        joystickButtonConfiguration.put(ButtonType.START, availableJoystickButtons.get(8));
        joystickButtonConfiguration.put(ButtonType.COMBO, availableJoystickButtons.get(9));

        joystickButtonConfiguration.put(ButtonType.POV_NULL, availablePOVButtons.get(-1));
        joystickButtonConfiguration.put(ButtonType.POV_N, availablePOVButtons.get(0));
        joystickButtonConfiguration.put(ButtonType.POV_NE, availablePOVButtons.get(45));
        joystickButtonConfiguration.put(ButtonType.POV_E, availablePOVButtons.get(90));
        joystickButtonConfiguration.put(ButtonType.POV_SE, availablePOVButtons.get(135));
        joystickButtonConfiguration.put(ButtonType.POV_S, availablePOVButtons.get(180));
        joystickButtonConfiguration.put(ButtonType.POV_SW, availablePOVButtons.get(225));
        joystickButtonConfiguration.put(ButtonType.POV_W, availablePOVButtons.get(270));
        joystickButtonConfiguration.put(ButtonType.POV_NW, availablePOVButtons.get(315));
    }

    private void configureDefaultDeadzoneAndMultiplier(double deadzone, double multiplier) {
        axisDeadzoneConfiguration = new HashMap<AxisType, Double>();
        axisMultiplierConfiguration = new HashMap<AxisType, Double>();
        for (AxisType axisType : AxisType.values()) {
            axisDeadzoneConfiguration.put(axisType, deadzone);
            axisMultiplierConfiguration.put(axisType, multiplier);
        }
    }

    /**
     * Will return the RAW value of the given {@link AxisType}. The mapping that
     * relates {@link AxisType} with the corresponding port is
     * {@link axisConfiguration}. PS4 triggers will return a value from -1 to 1
     * instead of returning from 0-1.
     * 
     * @param axisType The desired axis
     * @return Raw axis value
     */
    public double getRawAxis(AxisType axisType) {
        if (axisType.equals(AxisType.POV_X))
            return getPovXAxis();
        if (axisType.equals(AxisType.POV_Y))
            return getPovYAxis();
        try {
            return joystick.getRawAxis(axisConfiguration.get(axisType));
        } catch (NullPointerException e) {
            DriverStation.reportError("Axis " + axisType + " not found. Returning 0.", true);
        }
        return 0;
    }

    /**
     * Will return the RAW value of the given axis port. PS4 triggers will return a
     * value from -1 to 1 instead of returning from 0-1.
     * 
     * @param axisType The port of the desired axis
     * @return Raw axis value
     */
    public double getRawAxis(int axisPort) {
        return joystick.getRawAxis(axisPort);
    }

    /**
     * Will return the PROCESSED value of the chosen axis, applying both the set
     * <b>deadzone</b> and <b>multiplier</b>. PS4 Triggers are given in standard 0
     * to 1 instead of -1 to 1. Use {@link #setAxisDeadzone(AxisType, double)} and
     * {@link #setAxisMultiplier(AxisType, double)} to modify these values.
     * 
     * @param axis The desired axis
     * @return Processed axis value
     */
    public double getAxis(AxisType axis) {
        double input = getRawAxis(axis);
        double deadzonedInput = Math.applyDeadzone(input, axisDeadzoneConfiguration.get(axis));
        double multipliedInput = deadzonedInput * axisMultiplierConfiguration.get(axis);
        return multipliedInput;
    }

    /**
     * Treats the POV axis as if it were another axis.
     * 
     * @return the 'raw' x-value of the POV. Use {@link #getAxis()} to obtain a
     *         value with deadzone/multiplier applied.
     */

    public double getPovXAxis() {
        double x = Math.sin(Math.toRadians(joystick.getPOV()));
        return joystick.getPOV() == -1 || Math.abs(x) < 0.1 ? 0 : x;
    }

    /**
     * Treats the POV axis as if it were another axis.
     * 
     * @return the 'raw' y-value of the POV. Use {@link #getAxis()} to obtain a
     *         value with deadzone/multiplier applied.
     */

    public double getPovYAxis() {
        double y = Math.cos(Math.toRadians(joystick.getPOV()));
        return joystick.getPOV() == -1 || Math.abs(y) < 0.1 ? 0 : y;
    }

    public boolean isAxisPressed(AxisType axisType) {
        return Math.abs(getAxis(axisType)) > 0.5;
    }

    public boolean isAxisPressed(int axisPort) {
        return Math.abs(getRawAxis(axisPort)) > 0.5;
    }

    public void setAxisDeadzone(AxisType axis, double deadzone) {
        axisDeadzoneConfiguration.put(axis, Math.abs(deadzone));
    }

    public void setAxisMultiplier(AxisType axis, double multiplier) {
        axisMultiplierConfiguration.put(axis, multiplier);
    }

    public Trigger getButtonObj(ButtonType buttonType) {
        return joystickButtonConfiguration.get(buttonType);
    }

    public JoystickButton getJoystickButtonObj(int port) {
        return availableJoystickButtons.get(port);
    }

    public POVButton getPOVButtonObj(int angle) {
        return availablePOVButtons.get(angle);
    }

    public Trigger getAxisButtonObj(int axis) {
        return availableAxisButtons.get(axis);
    }

    public Joystick getJoystick() {
        return joystick;
    }

    // METHODS FOR ASSOCIATING COMMANDS YES THEY'RE A LOT BUT WE'D RATHER HAVE IT
    // THIS WAY

    /**
     * Starts the given command whenever the button changes from 'unpressed' to
     * 'pressed'.
     * Won't cancel the command.
     * 
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void onTrue(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.onTrue(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * Starts the given command whenever the condition changes from 'unpressed' to
     * 'pressed'.
     * Cancels the given command whenever the condition changes from 'pressed' to
     * 'unpressed'.
     *
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void whileTrue(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.whileTrue(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * When the condition changes from 'unpressed' to 'pressed', starts the command
     * if it's not running
     * and cancels the command if it's already running.
     *
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void toggleOnTrue(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.toggleOnTrue(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * Starts the given command whenever the condition changes from 'pressed' to
     * 'unpressed'.
     * Won't cancel the command.
     *
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void onFalse(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.onFalse(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * Starts the given command whenever the condition changes from 'pressed' to
     * 'unpressed'.
     * Cancels the given command whenever the condition changes from 'unpressed' to
     * 'pressed'.
     *
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void whileFalse(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.whileFalse(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * When the condition changes from 'pressed' to 'unpressed', starts the command
     * if it's not running
     * and cancels the command if it's already running.
     *
     * @param buttonType the button which will trigger the command
     * @param command    command to be assigned to button
     */
    public void toggleOnFalse(ButtonType buttonType, Command command) {
        try {
            Trigger chosenButton = joystickButtonConfiguration.get(buttonType);
            chosenButton.toggleOnFalse(command);
        } catch (NullPointerException e) {
            reportButtonError(buttonType, command);
        }
    }

    /**
     * Starts the given command whenever the condition of ALL chosen buttons
     * is 'pressed'. Won't cancel the command.
     *
     * @param command command to be assigned to button
     * @param buttons these are the buttons which will trigger the command
     */
    public void onTrueCombo(Command command, ButtonType... buttons) {
        int n = 0;
        Trigger triggerList;

        // Cycle through the given buttons until one of them ISN'T null.
        while (n < buttons.length) {
            if (joystickButtonConfiguration.get(buttons[n]) != null) {
                break;
            } else
                reportButtonErrorCombo(buttons[n], command);
            n++;
        }
        // If we've reached end of list and all of them were null, exit the method.
        if (n == buttons.length)
            return;

        // Otherwise, triggerlist will become the first button that isn't null
        triggerList = joystickButtonConfiguration.get(buttons[n]);

        // Add all additional buttons that aren't null.
        for (int i = n; i < buttons.length; i++) {
            try {
                Trigger chosenButton = joystickButtonConfiguration.get(buttons[i]);
                triggerList = triggerList.and(chosenButton);
            } catch (NullPointerException e) {
                reportButtonErrorCombo(buttons[i], command);
            }
        }
        // Assign the command
        triggerList.onTrue(command);

    }

    /**
     * Starts the given command whenever the condition of ALL chosen buttons
     * is 'pressed'. Cancells the command when at least one of the buttons is
     * 'unpressed'.
     *
     * @param command command to be assigned to button
     * @param buttons these are the buttons which will trigger the command
     */
    public void whileTrueCombo(Command command, ButtonType... buttons) {
        int n = 0;
        Trigger triggerList;

        // Cycle through the given buttons until one of them ISN'T null.
        while (n < buttons.length) {
            if (joystickButtonConfiguration.get(buttons[n]) != null) {
                break;
            } else
                reportButtonErrorCombo(buttons[n], command);
            n++;
        }
        // If we've reached end of list and all of them were null, exit the method.
        if (n == buttons.length)
            return;

        // Otherwise, triggerlist will become the first button that isn't null
        triggerList = joystickButtonConfiguration.get(buttons[n]);

        // Add all additional buttons that aren't null.
        for (int i = n; i < buttons.length; i++) {
            try {
                Trigger chosenButton = joystickButtonConfiguration.get(buttons[i]);
                triggerList = triggerList.and(chosenButton);
            } catch (NullPointerException e) {
                reportButtonErrorCombo(buttons[i], command);
            }
        }
        // Assign the command
        triggerList.whileTrue(command);
    }

    /**
     * When all buttons are 'pressed', starts the command if it's not running
     * and cancels the command if it's already running.
     *
     * @param command command to be assigned to button
     * @param buttons these are the buttons which will trigger the command
     */
    public void toggleOnTrueCombo(Command command, ButtonType... buttons) {
        int n = 0;
        Trigger triggerList;

        // Cycle through the given buttons until one of them ISN'T null.
        while (n < buttons.length) {
            if (joystickButtonConfiguration.get(buttons[n]) != null) {
                break;
            } else
                reportButtonErrorCombo(buttons[n], command);
            n++;
        }
        // If we've reached end of list and all of them were null, exit the method.
        if (n == buttons.length)
            return;

        // Otherwise, triggerlist will become the first button that isn't null
        triggerList = joystickButtonConfiguration.get(buttons[n]);

        // Add all additional buttons that aren't null.
        for (int i = n; i < buttons.length; i++) {
            try {
                Trigger chosenButton = joystickButtonConfiguration.get(buttons[i]);
                triggerList = triggerList.and(chosenButton);
            } catch (NullPointerException e) {
                reportButtonErrorCombo(buttons[i], command);
            }
        }
        // Assign the command
        triggerList.toggleOnTrue(command);
    }

    private void reportButtonError(ButtonType b, Command c) {
        DriverStation.reportWarning("Button " + b + " not found! The command " + c + " won't be assigned.", true);
    }

    private void reportButtonErrorCombo(ButtonType b, Command c) {
        DriverStation.reportWarning("Button " + b + " not found when assigning combo for " + c, true);
    }

    public double getDriveX() {
        switch (currentDriveLayout) {
            case ALEX_S:
                double speed = getAxis(AxisType.PICKUP_SWITCH);
                double direction;
                if (getButtonObj(ButtonType.GREEN).getAsBoolean())
                    direction = -1;
                else if (getButtonObj(ButtonType.YELLOW).getAsBoolean())
                    direction = 1;
                else
                    direction = 0;

                return speed * direction;
            default:
                return 0;
        }
    }

    public double getDriveY() {
        switch (currentDriveLayout) {
            case ALEX_S:
                double speed = getAxis(AxisType.PICKUP_SWITCH);
                double direction;
                if (getButtonObj(ButtonType.POV_N).getAsBoolean())
                    direction = 1;
                else if (getButtonObj(ButtonType.POV_S).getAsBoolean())
                    direction = -1;
                else
                    direction = 0;

                return speed * direction;
            default:
                return 0;
        }
    }

}
