// TipCalculatorController.java
// Controller that handles calculateButton and tipPercentageSlider events
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class TipCalculatorController { 
   // formatters for currency and percentages
   private static final NumberFormat currency = 
      NumberFormat.getCurrencyInstance();
   private static final NumberFormat percent = 
      NumberFormat.getPercentInstance();
   private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
   
   private BigDecimal tipPercentage = new BigDecimal(0.15); // 15% default
   
   // GUI controls defined in FXML and used by the controller's code
   @FXML 
   private TextField amountTextField; 

   @FXML
   private Label tipPercentageLabel; 

   @FXML
   private Slider tipPercentageSlider;

   @FXML
   private TextField tipTextField;

   @FXML
   private TextField totalTextField;

   // calculates and displays the tip and total amounts
   @FXML
   private void calculateButtonPressed(ActionEvent event) {
      try {
         BigDecimal amount = new BigDecimal(amountTextField.getText());
         BigDecimal tip = amount.multiply(tipPercentage);
         BigDecimal total = amount.add(tip);

         tipTextField.setText(currency.format(tip));
         totalTextField.setText(currency.format(total));
      }
      catch (NumberFormatException ex) {
         amountTextField.setText("Enter amount");
         amountTextField.selectAll();
         amountTextField.requestFocus();
      }
   }
   private void update() {
      BigDecimal amount;
      try {
         amount = new BigDecimal(amountTextField.getText());
      } catch (NumberFormatException ex) {
         tipTextField.setText("Invalid Input");
         totalTextField.setText("Invalid Input");
         return;
      }
      BigDecimal tipFactor = BigDecimal.valueOf(tipPercentageSlider.getValue()).divide(HUNDRED, 2, RoundingMode.HALF_UP);
      BigDecimal tip = amount.multiply(tipFactor);
      BigDecimal total = amount.add(tip);
      tipTextField.setText(currency.format(tip));
      totalTextField.setText(currency.format(total));
   }
   @FXML
   private void initialize() {
      // assuming slider value = 100 -> 100%
      tipPercentageLabel.textProperty().bind(tipPercentageSlider.valueProperty().asString("%.0f %%"));

      currency.setRoundingMode(RoundingMode.HALF_UP);
      tipPercentageSlider.valueProperty().addListener(o -> update());
      amountTextField.textProperty().addListener(o -> update());
   }

   // called by FXMLLoader to initialize the controller
//   public void initialize() {
//      // 0-4 rounds down, 5-9 rounds up
//      currency.setRoundingMode(RoundingMode.HALF_UP);
//
//      // listener for changes to tipPercentageSlider's value
//      tipPercentageSlider.valueProperty().addListener(
//         new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> ov,
//               Number oldValue, Number newValue) {
//               tipPercentage =
//                  BigDecimal.valueOf(newValue.intValue() / 100.0);
//               tipPercentageLabel.setText(percent.format(tipPercentage));
//            }
//         }
//      );
//   }
}
