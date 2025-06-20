package hutaomod.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.characters.HuTao;
import hutaomod.external.signature.card.AbstractSignatureCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.*;

import java.util.ArrayList;
import java.util.Objects;

public abstract class HuTaoCard extends AbstractSignatureCard implements SpawnModificationCard {
    protected int upCost;
    protected String upDescription;
    protected int upDamage;
    protected int upBlock;
    protected int upMagicNumber;

    public int si;
    public boolean isSiModified;

    public int yyTime = 0;
    public YYState yy;

    public boolean inHand = false;

    public CardStrings cardStrings;

    protected static final Color WHITE_BORDER_GLOW_COLOR;
    protected static final Color BLACK_BORDER_GLOW_COLOR;
    protected static final Color ORANGE_BORDER_GLOW_COLOR;
    protected static final Color RED_BORDER_GLOW_COLOR;

    public HuTaoCard(String id, String imgPath, CardColor color) {
        super(HuTaoMod.makeID(id),
                DataManager.getInstance().getCardData(id, CardDataCol.Name),
                imgPath,
                DataManager.getInstance().getCardDataInt(id, CardDataCol.Cost),
                DataManager.getInstance().getCardData(id, CardDataCol.Description),
                CardType.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Type)),
                color,
                CardRarity.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Rarity)),
                CardTarget.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Target))
        );
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        this.damage = this.baseDamage = DataManager.getInstance().getCardDataInt(id, CardDataCol.Damage);
        this.block = this.baseBlock = DataManager.getInstance().getCardDataInt(id, CardDataCol.Block);
        this.magicNumber = this.baseMagicNumber = DataManager.getInstance().getCardDataInt(id, CardDataCol.MagicNumber);

        this.upCost = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeCost);
        this.upDescription = DataManager.getInstance().getCardData(id, CardDataCol.UpgradeDescription);
        this.upDamage = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeDamage);
        this.upBlock = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeBlock);
        this.upMagicNumber = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeMagicNumber);

        String yinyang = DataManager.getInstance().getCardData(id, CardDataCol.YinYang);
        if (yinyang.contains("阴")) yy = YYState.YIN;
        else if (yinyang.contains("阳")) yy = YYState.YANG;
        else if (yinyang.contains("?")) yy = YYState.YINYANG;
        else yy = YYState.NONE;

        CommonKeywordIconsField.useIcons.set(this, true);
        assetUrl = "HuTaoMod/" + id + "_s_p.png";
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, HuTaoMod.HUTAO_CORAL.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    public HuTaoCard(String id, CardColor color) {
        this(id, PathDefine.CARD_PATH + id + ".png", color);
    }

    public HuTaoCard(String id) {
        this(id, PathDefine.CARD_PATH + id + ".png", HuTao.PlayerColorEnum.HUTAO_RED);
    }

    @Override
    public void loadCardImage(String img) {
        try {
            super.loadCardImage(img);
        } catch (Exception e) {
            HuTaoMod.logger.error("Failed to load card image: {}", img);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (upCost != DataManager.NULL_INT) {
                updateCost(upCost - cost);
            }
            if (!Objects.equals(upDescription, "")) {
                this.rawDescription = upDescription;
                initializeDescription();
            }
            if (upDamage != DataManager.NULL_INT) {
                upgradeDamage(upDamage - baseDamage);
            }
            if (upBlock != DataManager.NULL_INT) {
                upgradeBlock(upBlock - baseBlock);
            }
            if (upMagicNumber != DataManager.NULL_INT) {
                upgradeMagicNumber(upMagicNumber - baseMagicNumber);
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        si = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
        isSiModified = si != 0;
    }

    public void onMove(CardGroup group, boolean in) {
        if (group.type == CardGroup.CardGroupType.HAND) {
            if (in) {
                inHand = true;
                onEnterHand();
            } else {
                inHand = false;
                onLeaveHand();
            }
        }
        if (group.type == CardGroup.CardGroupType.DISCARD_PILE) {
            onDieying(in);
        }
        if (group.type == CardGroup.CardGroupType.MASTER_DECK && in
                && hasTag(CustomEnum.ARCANE_LEGEND)
                && group.group.stream().anyMatch(c -> c.hasTag(CustomEnum.ARCANE_LEGEND) && c != this)) {
            ModHelper.addEffectAbstract(() -> RelicEventHelper.purgeCards(this));
        }
    }

    public void onEnterHand() {}

    public void onLeaveHand() {}

    public void onDieying(boolean in) {}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m) && cost != -2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        yyTime = checkYinYang(true);
        onUse(p, m, yyTime);
        useYY(p, m);
    }

    public void useYY(AbstractPlayer p, AbstractMonster m) {
        switch (yy) {
            case YIN:
                addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
                break;
            case YANG:
                if (p.hasPower(SiPower.POWER_ID))
                    addToBot(new ReducePowerAction(p, p, SiPower.POWER_ID, 1));
                break;
            case YINYANG:
                if (p.hasPower(SiPower.POWER_ID)) {
                    addToBot(new ReducePowerAction(p, p, SiPower.POWER_ID, 1));
                } else {
                    addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
                }
        }
    }

    public abstract void onUse(AbstractPlayer p, AbstractMonster m, int yyTime);

    public int checkYinYang(boolean onUse) {
        int result = 0;
        int y = ModHelper.getPowerCount(AbstractDungeon.player, SiPower.POWER_ID);
        boolean dying = CacheManager.getBool(CacheManager.Key.DYING);
        if (yy == YYState.YIN && dying != SiPower.isDying(y + 1)) result = 1;
        else if (yy == YYState.YANG && dying != SiPower.isDying(y - 1)) result = 1;
        else if (yy == YYState.YINYANG && si <= 0) result = 1;
        else if (yy == YYState.YINYANG && dying != SiPower.isDying(y - 1)) result = 1;
        return SubscriptionManager.getInstance().triggerCheckYinYang(this, result, onUse);
    }

    protected void addToTop(AbstractGameAction... actions) {
        for (int i = actions.length - 1; i >= 0; i--) {
            addToTop(actions[i]);
        }
    }

    public int compareHandYY() {
        int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
        int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
        return Integer.compare(yangCount, yinCount);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        yyTime = checkYinYang(false);
        if (hasTag(CustomEnum.YIN_YANG) && yyTime > 0) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return checkSpawnable();
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return checkSpawnable();
    }

    boolean checkSpawnable() {
        return !hasTag(CustomEnum.ARCANE_LEGEND) || AbstractDungeon.player.masterDeck.group.stream().noneMatch(c -> c.hasTag(CustomEnum.ARCANE_LEGEND));
    }

    public static boolean isYin(AbstractCard card) {
        if (card instanceof HuTaoCard) {
            return ((HuTaoCard) card).yy == YYState.YIN;
        }
        return false;
    }

    public static boolean isYang(AbstractCard card) {
        if (card instanceof HuTaoCard) {
            return ((HuTaoCard) card).yy == YYState.YANG;
        }
        return false;
    }

    public enum YYState {
        NONE,
        YIN,
        YANG,
        YINYANG,
    }

    static {
        WHITE_BORDER_GLOW_COLOR = Color.WHITE.cpy();
        BLACK_BORDER_GLOW_COLOR = Color.DARK_GRAY.cpy();
        ORANGE_BORDER_GLOW_COLOR = Color.CORAL.cpy();
        RED_BORDER_GLOW_COLOR = Color.RED.cpy();
    }
}
