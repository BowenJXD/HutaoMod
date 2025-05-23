package hutaomod.misc;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hutaomod.utils.PathDefine;

import java.util.Collections;
import java.util.List;

public class SiIcon extends AbstractCustomIcon {
    public static final String ID = "si";
    private static SiIcon singleton;
    
    public SiIcon() {
        super(ID, ImageMaster.loadImage(PathDefine.UI_PATH + "icons/si.png"));
    }
    
    public static SiIcon get()
    {
        if (singleton == null) {
            singleton = new SiIcon();
        }
        return singleton;
    }

    @Override
    public List<String> keywordLinks() {
        return Collections.singletonList("hutaomod:死气");
    }
}
