package hutaomod.misc;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hutaomod.utils.PathDefine;

import java.util.Collections;
import java.util.List;

public class YinYangIcon extends AbstractCustomIcon {
    public static final String ID = "yy";
    private static YinYangIcon singleton;
    
    public YinYangIcon() {
        super(ID, ImageMaster.loadImage(PathDefine.UI_PATH + "icons/yy.png"));
    }
    
    public static YinYangIcon get()
    {
        if (singleton == null) {
            singleton = new YinYangIcon();
        }
        return singleton;
    }

    @Override
    public List<String> keywordLinks() {
        return Collections.singletonList("hutaomod:阴阳牌");
    }
}
