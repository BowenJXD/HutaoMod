package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.PlayCardAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.ModHelper;

import java.util.Objects;

public class HMFJSDYM extends HuTaoCard {
    public static final String ID = HMFJSDYM.class.getSimpleName();

    public HMFJSDYM() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(p, this, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        ModHelper.findCards(c -> Objects.equals(c.cardID, HuTaoMod.makeID(HutaoA.ID))).forEach(r -> {
            r.card.exhaustOnUseOnce = true;
            addToTop(new PlayCardAction(r.card, m));
            if (upgraded) addToTop(new UpgradeSpecificCardAction(r.card));
        });
    }
}
