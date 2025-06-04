package hutaomod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

public class GZTFXZZY extends HuTaoCard {
    public static final String ID = GZTFXZZY.class.getSimpleName();
    
    public GZTFXZZY() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }
}
