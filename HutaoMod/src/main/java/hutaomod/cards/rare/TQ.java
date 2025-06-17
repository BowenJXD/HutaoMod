package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.PlayCardAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.utils.ModHelper;

public class TQ extends HuTaoCard {
    public static final String ID = TQ.class.getSimpleName();

    public TQ() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (yyTime > 0) {
            ModHelper.addToBotAbstract(() -> {
                if (ModHelper.check(m)) {
                    AbstractCard tmp = makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = current_x;
                    tmp.current_y = current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                    tmp.baseDamage = baseDamage * (int) Math.pow(2, yyTime);
                    tmp.calculateCardDamage(m);

                    tmp.purgeOnUse = true;
                    addToTop(new PlayCardAction(tmp, m, false));
                }
            });
        }
    }
}
