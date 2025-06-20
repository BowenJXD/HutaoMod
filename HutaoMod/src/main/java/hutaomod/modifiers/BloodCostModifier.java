package hutaomod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import hutaomod.actions.BloodBurnAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.GeneralUtil;

public class BloodCostModifier extends HuTaoCardModifier {
    public static final String ID = HuTaoMod.makeID(BloodCostModifier.class.getSimpleName());

    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private final static String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    
    public int costCache = 0;

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
        String newDesc = GeneralUtil.tryFormat(DESCRIPTIONS[0], card.cost);
        return newDesc + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        costCache = card.cost;
        card.modifyCostForCombat(-card.cost);
    }
    
    @Override
    public void onRemove(AbstractCard card) {
        super.onRemove(card);
        card.modifyCostForCombat(costCache);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        addToBot(new BloodBurnAction(costCache));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BloodCostModifier();
    }
}
