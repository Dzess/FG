package pl.alife.fg.core.gp.data;

import ec.gp.GPData;

public class IntegerData extends GPData {

    public int Y;

    @Override
    public void copyTo(GPData gpd) {
        ((DoubleData) gpd).Y = this.Y;
    }

}
