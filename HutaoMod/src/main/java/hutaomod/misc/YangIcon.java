package hutaomod.misc;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hutaomod.utils.PathDefine;

public class YangIcon extends AbstractCustomIcon {
    public static final String ID = "yang";
    private static YangIcon singleton;
    
    public YangIcon() {
        super(ID, ImageMaster.loadImage(PathDefine.UI_PATH + "icons/yang.png"));
    }
    
    public static YangIcon get()
    {
        if (singleton == null) {
            singleton = new YangIcon();
        }
        return singleton;
    }
}
