package hutaomod.powers.arcaneLegends;

import hutaomod.actions.ClairvoirAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class GHSYXLPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(GHSYXLPower.class.getSimpleName());
    
    public GHSYXLPower() {
        super(POWER_ID);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        int si = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
        if (si > 0) {
            flash();
            ModHelper.addToBotAbstract(() -> addToBot(new ClairvoirAction(si)));
        }
    }
}
