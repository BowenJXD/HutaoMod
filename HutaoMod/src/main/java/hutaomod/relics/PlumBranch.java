package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hutaomod.actions.RandomCardFromDrawPileToHandAction;

public class PlumBranch extends HuTaoRelic {
    public static final String ID = PlumBranch.class.getSimpleName();
    
    public PlumBranch() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL 
                && damageAmount >= AbstractDungeon.player.currentHealth 
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            addToBot(new RandomCardFromDrawPileToHandAction());
            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }
}
