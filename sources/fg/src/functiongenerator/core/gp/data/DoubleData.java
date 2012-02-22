package functiongenerator.core.gp.data;

import ec.gp.GPData;

public class DoubleData extends GPData {

    public double Y;

    @Override
    public void copyTo(GPData gpd) {
        ((DoubleData) gpd).Y = this.Y;
    }

}
