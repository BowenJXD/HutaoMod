package hutaomod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.PathDefine;
import org.apache.logging.log4j.Level;

public abstract class HuTaoPower extends AbstractPower {
    public String[] DESCRIPTIONS;
    public boolean upgraded;
    public PowerStrings powerStrings;
    public Integer limit;

    public HuTaoPower(String id, AbstractCreature owner, int Amount, PowerType type, boolean upgraded){
        this.ID = id;
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(id);
        this.name = powerStrings.NAME;
        this.DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.owner = owner;
        this.amount = Amount;
        this.type = type;
        this.upgraded = upgraded;
        this.loadRegion(this.getClass().getSimpleName());
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void updateDescription() {
        try {
            description = String.format(DESCRIPTIONS[upgraded && DESCRIPTIONS.length > 1 ? 1 : 0], amount);
        } catch (Exception e) {
            try {
                description = DESCRIPTIONS[upgraded && DESCRIPTIONS.length > 1 ? 1 : 0];
            } catch (Exception e2) {
                HuTaoMod.logger.warn("Error while updating {} description", ID);
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (limit != null && amount >= limit) {
            amount = limit;
            this.onLimitReached();
        }
    }
    
    public void onLimitReached(){}

    public void remove(int val) {
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this, val));
        }
    }

    @Override
    protected void loadRegion(String fileName) {
        try {
            String path128 = String.format(PathDefine.POWER_PATH + "%s128.png", fileName);
            String path48 = String.format(PathDefine.POWER_PATH + "%s48.png", fileName);
            this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 48, 48);
        } catch (Exception e) {
            String path128 = PathDefine.POWER_PATH + "InfoPower128.png";
            String path48 = PathDefine.POWER_PATH + "InfoPower48.png";
            this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 48, 48);
            HuTaoMod.logger.warn("Error while loading {} region", ID);
        }
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        SubscriptionManager.getInstance().triggerPrePowerTrigger(this);
    }
}
