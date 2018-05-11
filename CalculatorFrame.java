import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.util.*;

/**
	Code modified from Big Java ch 20 section 2_2
	A frame that shows the calculation field and errors using text fields with a button to calculate user input.
*/
public class CalculatorFrame extends JFrame
{
	//Default frame dimensions
	private static final int FRAME_WIDTH = 350;
	private static final int FRAME_HEIGHT = 250;
	
	//Default Operation when program starts
	private static final String DEFAULT_OPERATION = "9 + 9";
	  
	//GUI objects of the window
	private JTextField calculateField;
	private JButton calculateButton;
	private JLabel displayLabel;
	
	private String userOperation;
	private int result;
	
	private Calculator calc;
	
	private int firstOperand;
	private int secondOperand;
	private String operator;

	/**
	* Creates GUI objects for the calculator, and sets the size of window.
	*/
	public CalculatorFrame()
	{  
		createTextField();
		createButton();
		createPanel();

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	/**
	* Creates the field for user calculations and result/error field
	*/
	private void createTextField()
	{
		final int FIELD_WIDTH = 10;
		calculateField = new JTextField(FIELD_WIDTH);
		calculateField.setText(DEFAULT_OPERATION);
		//Result will update and calculate if user presses 'ENTER'
		calculateField.addActionListener(e -> run());
		
		displayLabel = new JLabel("Calculator ready!");
		displayLabel.setOpaque(true);
		displayLabel.setBackground(Color.WHITE);
	}
	
	 /**
	 * Creates a button for user to click to calculate the desired input.
	 */
	private void createButton()
	{
		calculateButton = new JButton("Calculate");      
		
		calculateButton.addActionListener(e -> run());
	}
	
	 /**
	 * Creates the window with GUI objects
	 */
	private void createPanel()
	{
		JPanel panel = new JPanel();
		panel.add(calculateField);
		panel.add(calculateButton);
		panel.add(displayLabel);
		add(panel);
	}
	
	/**
	 * Gets, scans the user input into a valid operation, and then calculates the input(if possible).
	* If an error occurs, the exception is displayed in the display field and calculation is halted.
	 */
	public void run()
	{
		userOperation = calculateField.getText();
		//Remove any leading and ending spaces that may confuse the delimiter
		userOperation = userOperation.trim();
		try(Scanner scan = new Scanner(userOperation).useDelimiter(" "))
		{
			firstOperand = setOperand(scan);
			operator = scan.next();
			//nest another try catch block to catch second operand exceptions
			try
			{
				secondOperand = setOperand(scan);
				calculate(firstOperand, operator, secondOperand);
			}		
			//If second operand is not an integer
			catch(IllegalArgumentException exception)
			{
				displayLabel.setText("Second operand not a valid integer : " + exception.getMessage());
				displayLabel.setBackground(Color.RED);
				//If there is more than 2 operands or operators
				try
				{
					String check = scan.next();
				}
				catch(Exception exception2)
				{
					displayLabel.setText("More than two operands or two operators ");
					displayLabel.setBackground(Color.RED);
				}
				
			}
			//If second operand is missing
			catch(NoSuchElementException exception)
			{
				displayLabel.setText("Second operand is missing.");
				displayLabel.setBackground(Color.RED);
			}
		}
		//If the first operand is not a valid integer
		catch(IllegalArgumentException exception)
		{
			System.out.println("this should work");
			displayLabel.setText("First operand not a valid integer : " + exception.getMessage());
			displayLabel.setBackground(Color.RED);
		}
		//If the operator and second operand are missing
		catch(NoSuchElementException exception)
		{
			displayLabel.setText("The operator and second operand are missing.");
			displayLabel.setBackground(Color.RED);
		}
	}
	/**
	* Sets the first or second operand to the next line following empty spaces in the calculate field.
	* Sends an exception to run() if the operand is invalid.
	* @param scan Scanner object
	* @return operand A valid integer
	 */
	public int setOperand(Scanner scan) throws RuntimeException
	{
		int operand = Integer.parseInt(scan.next());
		return operand;
	}
	/**
	* Calculates the desired operation. This method also deals with dividing by zero, illegal operations or if no operation is specified.
	* @param firstOperand The first integer
	* @param operator The mathenatical operation desired
	* @param secondOperand The second integer
	 */
	public void calculate(int firstOperand, String operator, int secondOperand) throws RuntimeException
	{
		//Try catch catches if the operator exists or not
		try
		{
			switch(operator.charAt(0))
			{
				case '+':
				{
					result = firstOperand + secondOperand;
					displayLabel.setText("Result of " + firstOperand + operator + secondOperand + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				case '-':
				{
					result = firstOperand - secondOperand;
					displayLabel.setText("Result of " + firstOperand + operator + secondOperand + userOperation + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				case '*':
				{
					result = firstOperand * secondOperand;
					displayLabel.setText("Result of " + firstOperand + operator + secondOperand+ userOperation + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				case '/':
				{
					try
					{
						result = firstOperand / secondOperand;
					}
					//If dividing by zero
					catch(ArithmeticException exception)
					{
						displayLabel.setText("Cannot divide by 0");
						displayLabel.setBackground(Color.RED);
					}
					displayLabel.setText("Result of " + firstOperand + operator + secondOperand + userOperation + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				case '%' :
				{
					result = firstOperand % secondOperand;
					displayLabel.setText("Result of " + firstOperand + operator + secondOperand + userOperation + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				case '^':
				{
					result = (int)Math.pow(firstOperand, secondOperand);
					displayLabel.setText("Result of "+ firstOperand + operator + secondOperand + userOperation + " is " + result);
					displayLabel.setBackground(Color.WHITE);
					break;
				}
				//Otherwise, user has input illegal operator
				default :
				{
					try
					{
						throw new IllegalOperatorException("Illegal Operator");
					}
					catch(IllegalOperatorException exception)
					{
						displayLabel.setText(exception.getMessage() + " : " + operator);
						displayLabel.setBackground(Color.RED);
					}
				}
			}
		}
		catch(StringIndexOutOfBoundsException exception)
		{
			displayLabel.setText("No operation specified.");
			displayLabel.setBackground(Color.RED);
		}
	}
}
