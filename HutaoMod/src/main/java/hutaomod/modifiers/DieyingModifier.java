package hutaomod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisParticle;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.RandomCardFromDrawPileToHandAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;

public class DieyingModifier extends HuTaoCardModifier {
    public static final String ID = HuTaoMod.makeID(DieyingModifier.class.getSimpleName());
    
    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private final static String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    
    int damage = 3;
    int block = 2;

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
        String newDesc = "";
        switch (card.type) {
            case ATTACK:
                newDesc = GeneralUtil.tryFormat(DESCRIPTIONS[0], damage);
                break;
            case SKILL:
                newDesc = GeneralUtil.tryFormat(DESCRIPTIONS[1], block);
                break;
            default:
                newDesc = DESCRIPTIONS[2];
                break;
        }
        return rawDescription + newDesc;
    }

    @Override
    public void onDieying(AbstractCard card, boolean in) {
        super.onDieying(card, in);
        switch (card.type) {
            case ATTACK:
                AbstractMonster m = ModHelper.betterGetRandomMonster();
                if (m != null) {
                    addToBot(new VFXAction(new HemokinesisParticle(CardGroup.DISCARD_PILE_X, CardGroup.DISCARD_PILE_Y, m.hb.cX, m.hb.cY, AbstractDungeon.player.flipHorizontal)));
                    addToBot(new CardDamageAction(m, damage, card, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            case SKILL:
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
                break;
            default:
                addToBot(new RandomCardFromDrawPileToHandAction());
                break;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DieyingModifier();
    }
}
