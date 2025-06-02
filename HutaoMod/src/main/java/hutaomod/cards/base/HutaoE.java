package hutaomod.cards.base;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.relics.PapilioCharontis;

public class HutaoE extends HuTaoCard {
    public static final String ID = HutaoE.class.getSimpleName();
    
    public HutaoE() {
        super(ID, HuTao.PlayerColorEnum.HUTAO_RED);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, block + si));
        if (upgraded && yyTime > 0) {
            addToBot(new AddTemporaryHPAction(p, p, si * yyTime));
        }
    }
}
