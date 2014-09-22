import java.math.BigDecimal;

public class Calculate {

    //List<String> data = new ArrayList<String>();
    //List<double> d = new ArrayList<double>();
    private Calculator cal = null;

    private int dataSize = 0;
    private double[] data;
    public double arg = 0;
    private double stdDev = 0;
    private double stdDev0 = 0;
    private double uncertainA = 0;
    private double uncertainB = 0;
    private double meaEorrer = 0;
    private int scale = 0;

    private static final double[] factorT ={1.00, 1.00, 1.84, 1.32, 1.20, 1.14, 1.11, 1.09, 1.08, 1.07, 1.06, 1.05, 1.03};

    public Calculate(Calculator ca) {
        this.cal = ca;
    }

    public void calculate() {

        reset();

        if(!strToDouble()) {
            if(dataSize == 0) cal.error("亲，你的输入为空或有非法输入哦~");
            else cal.error("亲，你有非法输入哦~");
            this.reset();
            return;
        }

        if(!getScale()) {
            cal.error("亲，请确保保留精度为一位数字~");
            this.reset();
            return;
        }

        if(dataSize > 0) {
            calArg();
            stdDev0();
            stdDev();
            uncertainA();
            showResults();
        }

    }

    private void stdDev0() {
        double sum = 0;
        for(int i = 0; i < dataSize; i++) {
            sum += (data[i] - arg) * (data[i] - arg);
        }
        stdDev0 = round(Math.sqrt(sum / (dataSize - 1) ), scale);
    }

    private void stdDev() {
        if(dataSize <= 12)
            stdDev = round(stdDev0 * factorT[dataSize], scale);
        else
            stdDev = stdDev0;
    }

    private void uncertainA() {
        uncertainA = round(stdDev / Math.sqrt(dataSize), scale);
    }

    private boolean strToDouble() {
        boolean bRight = true;
        String str = cal.textArea.getText();
        String[] dataString = str.split("\n");
        data = new double[dataString.length];
        System.out.println(dataString.length);

        //判断输入的合法性，并转换成double类型
        for(int i = 0; i < dataString.length; i++) {
            dataString[i] = dataString[i].trim();
            if(dataString[i].equals("")) {
                System.out.println("empty line");
                continue;
            }

            if(isNumber(dataString[i])) {
                data[dataSize] = Double.parseDouble(dataString[i]);
                System.out.println(data[dataSize]);
                dataSize++;

            }
            else {
                bRight = false;
            }
        }
        return bRight;
    }

    private void calArg() {
        double sum = 0;
        for(int i = 0; i < dataSize; i++) {
            sum += data[i];
        }
        arg = round(sum / dataSize, scale);
        System.out.println("arg:" + arg);
    }

    private boolean isNumber(String str) {
        for(int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if( !( (num >= '0' && num <= '9') || num == '.') ) return false;
        }
        return true;
    }

    public double round(double v, int scale) {
        if (scale < 0) {
            System.out.println("精度错误！");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private boolean getScale() {
        String str = cal.getDigits().trim();
        if(str.length() == 1 && (str.charAt(0) >= '0' && str.charAt(0) <= '9')) {
            scale = Integer.parseInt(str);
            return true;
        }
        return false;
    }

    private void showResults() {
        cal.setAverage(arg);
        cal.setStdDev0(stdDev0);
        cal.setStdDev(stdDev);
        cal.setUncertainA(uncertainA);
    }

    public void reset() {
        this.dataSize = 0;
        this.scale = 0;
        this.stdDev = 0;
        this.stdDev0 = 0;
        this.uncertainA = 0;
        this.data = null;
        cal.resultsReset();
    }

}