import javax.swing.JFrame;

/**
   This program displays a functional calculator
   @author James Young
   @course CPSC 1181
   @instructor Gladys Monagan
*/
public class CalculatorViewer
{  
   public static void main(String[] args)
   {  
      JFrame frame = new CalculatorFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
