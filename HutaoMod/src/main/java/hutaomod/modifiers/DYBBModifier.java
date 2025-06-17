package hutaomod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;

public class DYBBModifier extends HuTaoCardModifier {
    public static final String ID = HuTaoMod.makeID(DYBBModifier.class.getSimpleName());
    
    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private final static String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    
    int bbCount = 1;

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String newDesc = GeneralUtil.tryFormat(DESCRIPTIONS[0], bbCount);
        return rawDescription + newDesc;
    }

    @Override
    public void onDieying(AbstractCard card, boolean in) {
        super.onDieying(card, in);
        AbstractMonster monster = ModHelper.betterGetRandomMonster();
        if (monster != null) {
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new BloodBlossomPower(monster, AbstractDungeon.player, bbCount)));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DYBBModifier();
    }
}
