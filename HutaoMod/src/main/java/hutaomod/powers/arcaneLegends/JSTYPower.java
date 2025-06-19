package hutaomod.powers.arcaneLegends;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.modcore.HuTaoMod;
import hutaomod.modifiers.DieyingModifier;
import hutaomod.powers.PowerPower;
import hutaomod.utils.CacheManager;

public class JSTYPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(JSTYPower.class.getSimpleName());
    
    boolean dyingCache = false;
    
    public JSTYPower() {
        super(POWER_ID);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        dyingCache = CacheManager.getBool(CacheManager.Key.DYING);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        dyingCache = CacheManager.getBool(CacheManager.Key.DYING);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (CacheManager.getBool(CacheManager.Key.DYING) && !dyingCache) {
            CardModifierManager.addModifier(card, new DieyingModifier());
        }
    }
}
