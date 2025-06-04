package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.CardDataCol;
import hutaomod.utils.DataManager;
import hutaomod.utils.ModHelper;

public class ZN extends HuTaoCard {
    public static final String ID = ZN.class.getSimpleName();

    public ZN() {
        super(ID);
    }
    
    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(magicNumber);
        upgradeBlock(magicNumber);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = DataManager.getInstance().getCardData(ID, CardDataCol.Name) + "+" + this.timesUpgraded;
        initializeTitle();
        this.initializeDescription();
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new GainBlockAction(p, p, block));
        AbstractMonster m = ModHelper.betterGetRandomMonster();
        if (m != null) {
            addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        AbstractCard c = this.makeStatEquivalentCopy();
        c.upgrade();
        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
    }
}
