package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.buffs.KQZZPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class KQZZ extends HuTaoCard {
    public static final String ID = KQZZ.class.getSimpleName();

    public KQZZ() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, (upgraded ? block : 0) + si));
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 0));
        if (yyTime > 0)
            addToBot(new ApplyPowerAction(p, p, new KQZZPower(p, yyTime)));
    }
}
