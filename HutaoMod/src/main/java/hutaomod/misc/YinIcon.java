package hutaomod.misc;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hutaomod.utils.PathDefine;

public class YinIcon extends AbstractCustomIcon {
    public static final String ID = "yin";
    private static YinIcon singleton;
    
    public YinIcon() {
        super(ID, ImageMaster.loadImage(PathDefine.UI_PATH + "icons/yin.png"));
    }
    
    public static YinIcon get()
    {
        if (singleton == null) {
            singleton = new YinIcon();
        }
        return singleton;
    }
}
