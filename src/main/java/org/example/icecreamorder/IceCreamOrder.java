package org.example.icecreamorder;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// CST-283
// Aaron Pelto
// Winter 2024

// This program defines an interface for an ice cream order.
// Make the following changes:

// Add two topping checkboxes to the list
// Add one more base ice cream type
// Add the following labels:
//  Add a label "Toppings" over the vertical topping checkbox list
//  Add a label "Base Ice Cream" to the left of the radio button set
// Add another button to reset an order. The button should:
// Erase any order summary label content
// Reset to "Vanilla" ice cream
// Uncheck all toppings
// Increase the window length and width to accommodate changes

public class IceCreamOrder extends Application
{
    // Fields for the cost of ice cream and toppings
    private final double ICE_CREAM_COST   = 3.00;
    private final double PER_TOPPING_COST = 0.50;
    // Fields for the RadioButtons, Checkboxes, Strings, Buttons and Labels
    private RadioButton vanilla,chocolate,strawberry, CookiesAndCream, ChocolateChip, ButterPecan, ChocolateChipCookieDough, MintChocolateChip, Carmel, Neapolitan;
    private CheckBox addOns[];
    private String addOnNames[] = {"Sprinkles", "Nuts", "Whipped Cream", "Oreos", "Strawberries", "Fresh Fruit", "Hot Fudge", "Caramel", "Strawberry Sauce", "Chocolate Sauce"};
    private Button orderButton;
    private Button resetButton;
    private Label iceCreamLabel, toppingsLabel;
    private Label baseIceCreamLabel, toppingsTitleLabel;

    public static void main(String[] args)
    {
        // Launch the application.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        // Instantiate radio button set.  Add to horizontal box.  Assuming vanilla as default choice.
        vanilla = createIceCreamRadioButton("Vanilla");
        chocolate = createIceCreamRadioButton("Chocolate");
        strawberry = createIceCreamRadioButton("Strawberry");
        CookiesAndCream = createIceCreamRadioButton("Cookies and Cream");
        ChocolateChip = createIceCreamRadioButton("Chocolate Chip");
        ButterPecan = createIceCreamRadioButton("Butter Pecan");
        ChocolateChipCookieDough = createIceCreamRadioButton("Chocolate Chip Cookie Dough");
        MintChocolateChip = createIceCreamRadioButton("Mint Chocolate Chip");
        Carmel = createIceCreamRadioButton("Carmel");
        Neapolitan = createIceCreamRadioButton("Neapolitan");

        // Add the RadioButtons to a ToggleGroup.
        ToggleGroup radioGroup = new ToggleGroup();
        chocolate.setToggleGroup(radioGroup);
        vanilla.setToggleGroup(radioGroup);
        strawberry.setToggleGroup(radioGroup);
        CookiesAndCream.setToggleGroup(radioGroup);
        ChocolateChip.setToggleGroup(radioGroup);
        ButterPecan.setToggleGroup(radioGroup);
        ChocolateChipCookieDough.setToggleGroup(radioGroup);
        MintChocolateChip.setToggleGroup(radioGroup);
        Carmel.setToggleGroup(radioGroup);
        Neapolitan.setToggleGroup(radioGroup);


        // Build box containing radio button set
        HBox iceCreamBox = new HBox(10,vanilla,chocolate,strawberry, CookiesAndCream, ChocolateChip, ButterPecan, ChocolateChipCookieDough, MintChocolateChip, Carmel, Neapolitan);

        // Instantiate ice cream add-on check boxes. Add to vertical box.
        VBox addOnBox = new VBox(10);
        addOns = new CheckBox[addOnNames.length];
        for (int i = 0; i < addOnNames.length; i++)
        {
            // Instantiate checkbox, assign associated label, and relate to a common listener.
            addOns[i] =  new CheckBox(addOnNames[i]);
            addOnBox.getChildren().add(addOns[i]);
            addOns[i].setOnAction(event ->
            {
                handleCheckBoxChange();
            });
        }
        VBox.setMargin(addOnBox, new Insets(0,0,0,20));   // Add inset space - top, left, bottom, right

        // Instantiate order button.  Assign to listener method
        orderButton =  new Button("Order");
        orderButton.setOnAction(new ButtonClickHandler());

        resetButton = new Button("Reset Order");
        resetButton.setOnAction(event -> {
            iceCreamLabel.setText("Vanilla Ice Cream");
            toppingsLabel.setText("");
            vanilla.setSelected(true);
            for (CheckBox addOn : addOns) {
                addOn.setSelected(false);
            }
        });

        // Instantiate labels
        iceCreamLabel = new Label("Vanilla Ice Cream");
        toppingsLabel = new Label();
        baseIceCreamLabel = new Label("Base Ice Cream");
        toppingsTitleLabel = new Label("Toppings");

        // Define actions for buttons
        setIceCreamAction(chocolate, "Chocolate");
        setIceCreamAction(vanilla, "Vanilla");
        setIceCreamAction(strawberry, "Strawberry");
        setIceCreamAction(CookiesAndCream, "Cookies and Cream");
        setIceCreamAction(ChocolateChip, "Chocolate Chip");
        setIceCreamAction(ButterPecan, "Butter Pecan");
        setIceCreamAction(ChocolateChipCookieDough, "Chocolate Chip Cookie Dough");
        setIceCreamAction(MintChocolateChip, "Mint Chocolate Chip");
        setIceCreamAction(Carmel, "Carmel");
        setIceCreamAction(Neapolitan, "Neapolitan");

        // Complete building left side of GUI
        VBox guiBox = new VBox(30,baseIceCreamLabel,iceCreamBox,toppingsTitleLabel,addOnBox,orderButton,resetButton);

        // Build right side of GUI with label object
        VBox labelBox = new VBox(20,iceCreamLabel,toppingsLabel);
        labelBox.setAlignment(Pos.CENTER);

        // Define main layout by combining left GUI and right labels
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(guiBox);
        mainLayout.setCenter(labelBox);

        // Set up overall scene
        Scene scene = new Scene(mainLayout, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Delta Ice Cream");
        primaryStage.show();
    }

    // Handle checkbox click event.  Any change of state for any checkbox will invoke
    // this method.  The method then will redefine the string for ice cream toppings.
    // This string is then "set" in the designated GUI label.
    public void handleCheckBoxChange()
    {
        String toppingsString = "";
        for(int i = 0; i < addOnNames.length; i++)
        {
            if (addOns[i].isSelected())
                toppingsString += addOnNames[i] + "\n";
        }
        toppingsLabel.setText(toppingsString);
    }

    private void setIceCreamAction(RadioButton button, String flavor)
    {
        button.setOnAction(event ->
        {
            iceCreamLabel.setText(flavor + " Ice Cream");
        });
    }

    private RadioButton createIceCreamRadioButton(String flavor)
    {
        RadioButton button = new RadioButton(flavor);
        if (flavor.equals("Vanilla")) {
            button.setSelected(true);
        }
        return button;
    }

    private double calculateTotalCost()
    {
        double totalOrderCost = ICE_CREAM_COST;     // Assign base cost - assuming no toppings

        // Add per topping cost for each topping checked
        for(int i = 0; i < addOnNames.length; i++)
        {
            if (addOns[i].isSelected())
                totalOrderCost += PER_TOPPING_COST;
        }
        return totalOrderCost;
    }

    // Handle button click event.  Collect values in operand text fields, add
    // them, and display them via a JavaFX Alert dialog box.
    class ButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            // Create order summary
            StringBuilder orderSummary = new StringBuilder();

            // Add ice cream cost
            orderSummary.append("Order\n\t").append(iceCreamLabel.getText()).append(": \t$").append(String.format("%.2f", ICE_CREAM_COST)).append("\n");

            // Add topping cost
            orderSummary.append("Toppings\n");
            double totalToppingsCost = 0.0;
            for (int i = 0; i < addOnNames.length; i++) {
                if (addOns[i].isSelected()) {
                    double toppingCost = PER_TOPPING_COST;
                    totalToppingsCost += toppingCost;
                    orderSummary.append("\t").append(addOnNames[i]).append(": \t$").append(String.format("%.2f", toppingCost)).append("\n");
                }
            }

            // Add total cost
            double totalOrderCost = ICE_CREAM_COST + totalToppingsCost;
            orderSummary.append("\nTotal Cost:\n\t\t\t$").append(String.format("%.2f", totalOrderCost));

            // Display order summary
            // Create and display an alert dialog box
            // with the order summary
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ice Cream Order");
            alert.setHeaderText("ORDER");
            alert.setContentText(orderSummary.toString());

            alert.showAndWait();
        }
    }
}