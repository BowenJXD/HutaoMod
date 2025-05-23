package hutaomod.misc;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hutaomod.utils.PathDefine;

import java.util.Collections;
import java.util.List;

public class BloodBlossomIcon extends AbstractCustomIcon {
    public static final String ID = "bb";
    private static BloodBlossomIcon singleton;
    
    public BloodBlossomIcon() {
        super(ID, ImageMaster.loadImage(PathDefine.UI_PATH + "icons/bb.png"));
    }
    
    public static BloodBlossomIcon get()
    {
        if (singleton == null) {
            singleton = new BloodBlossomIcon();
        }
        return singleton;
    }

    @Override
    public List<String> keywordLinks() {
        return Collections.singletonList("hutaomod:血梅香");
    }
}
