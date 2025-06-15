package hutaomod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hutaomod.actions.BloodBurnAction;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.GeneralUtil;

public class RattanToyHuTao extends HuTaoRelic implements ClickableRelic {
    public static final String ID = RattanToyHuTao.class.getSimpleName();
    
    public RattanToyHuTao() {
        super(ID, RelicTier.STARTER);
        counter = 1;
        updateDescription(getUpdatedDescription());
    }

    @Override
    public String getUpdatedDescription() {
        return GeneralUtil.tryFormat(DESCRIPTIONS[0], counter);
    }

    @Override
    public void onRightClick() {
        if (isObtained && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            addToBot(new BloodBurnAction(counter));
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SiPower.POWER_ID, 1));
            setCounter(counter+1);
            updateDescription(getUpdatedDescription());
        }
    }
}
