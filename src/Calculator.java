import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame {

    private static final int CAL_WIDTH = 600;
    private static final int CAL_HEIGHT = 450;

    private static final int TEXT_WIDTH = 100;
    private static final int TEXT_HEIGHT = 300;

    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 20;

    static Calculator cal = new Calculator();

    Calculate c = new Calculate(this);

    TextArea textArea = new TextArea();
    Panel panelPrint = new Panel();

    Button calculate = new Button("计算");
    Button clear = new Button("清空");

    Label digits = new Label("保留小数位数：");
    TextField textDigits = new TextField();

    private Label average = new Label("Arg: ");
    private Label uncertainA = new Label("σx: ");
    private Label stdDev0 = new Label("s: ");
    private Label stdDev = new Label("σ: ");
    private Label errorLabel = new Label();

    private Dialog errorDialog = new Dialog(this, "你娃出错了!");

    FlowLayout flow = new FlowLayout(FlowLayout.TRAILING, 20, 40);

    public void launchFrame() {

        setLocation(50, 50);
        setSize(CAL_WIDTH, CAL_HEIGHT);
        setBackground(Color.GRAY);
        setTitle("物理实验数据处理神器V1.0");
        this.setResizable(false);

        errorDialog.setBounds(200, 200, 200, 140);
        errorDialog.setResizable(false);
        errorDialog.add(errorLabel);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        errorDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                errorDialog.setVisible(false);
            }
        });

        Layout();
        setVisible(true);

    }

    public void Layout() {
        setLayout(null);
        textArea.setBounds(50, 75, TEXT_WIDTH, TEXT_HEIGHT);
        textArea.setBackground(Color.WHITE);
        panelPrint.setBounds(350, 75, 200, 300);
        panelPrint.setBackground(this.getBackground());
        panelPrint.setLayout(null);

        calculate.setBounds(275, 150, 50, 30);
        clear.setBounds(275, 275, 50, 30);

        digits.setBounds(50, 400, 80, 20);
        textDigits.setBounds(135, 400, 80, 20);

        this.average.setBounds(0, 0, LABEL_WIDTH, LABEL_HEIGHT);
        this.stdDev0.setBounds(0, 30, LABEL_WIDTH, LABEL_HEIGHT);
        this.stdDev.setBounds(0, 60, LABEL_WIDTH, LABEL_HEIGHT);
        this.uncertainA.setBounds(0, 90, LABEL_WIDTH, LABEL_HEIGHT);

        errorLabel.setBounds(30, 30, 200, 200);

        add(textArea);
        add(panelPrint);
        add(calculate);
        add(clear);
        add(digits);
        add(textDigits);

        panelPrint.add(average);
        panelPrint.add(stdDev0);
        panelPrint.add(stdDev);
        panelPrint.add(uncertainA);

        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.calculate();
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

    }

    public void clear() {
        textArea.getText();
        textDigits.getText();
        textArea.setText("");
        textDigits.setText("");

        resultsReset();

    }

    public void resultsReset() {
        this.average.setText("Arg: ");
        this.stdDev0.setText("s: ");
        this.stdDev.setText("σ: ");
        this.uncertainA.setText("σx: ");
    }

    public String getDigits() {
        return textDigits.getText();
    }

    public static void main(String[] args) {
        cal.launchFrame();
    }


    public void setAverage(Double d) {
        this.average.setText(this.average.getText() + d.toString());
    }

    public void setUncertainA(Double d) {
        this.uncertainA.setText(this.uncertainA.getText() + d.toString());
    }

    public void setStdDev0(Double d) {
        this.stdDev0.setText(this.stdDev0.getText() + d.toString());
    }

    public void setStdDev(Double d) {
        this.stdDev.setText(this.stdDev.getText() + d.toString());
    }

    public void error(String str) {
        errorLabel.setText(str);
        errorDialog.setVisible(true);
    }

}