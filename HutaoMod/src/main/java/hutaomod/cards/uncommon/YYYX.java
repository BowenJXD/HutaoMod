package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

import java.util.List;

public class YYYX extends HuTaoCard {
    public static final String ID = YYYX.class.getSimpleName();

    public YYYX() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        List<ModHelper.FindResult> results = ModHelper.findCards(c -> c.hasTag(CardTags.STARTER_STRIKE));
        int strengthCount = results.stream().mapToInt(r -> r.group.type == CardGroup.CardGroupType.DRAW_PILE ? 1 : 0).sum();
        int dexterityCount = results.stream().mapToInt(r -> r.group.type == CardGroup.CardGroupType.DISCARD_PILE ? 1 : 0).sum();
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, strengthCount)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, dexterityCount)));
    }
}
