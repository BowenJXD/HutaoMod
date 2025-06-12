package hutaomod.modcore;

import basemod.EasyConfigPanel;

public class HuTaoModConfig extends EasyConfigPanel {
    public static boolean doShowTutorial = true;
    
    public HuTaoModConfig() {
        super(HuTaoMod.MOD_NAME, HuTaoMod.makeID(HuTaoModConfig.class.getSimpleName()));
    }
}
