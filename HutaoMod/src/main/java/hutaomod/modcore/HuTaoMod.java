package hutaomod.modcore;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import hutaomod.characters.HuTao;
import hutaomod.misc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public final class HuTaoMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber, PostInitializeSubscriber, StartGameSubscriber {
    public static String MOD_NAME = "HuTaoMod";

    public static final Color HUTAO_RED = new Color(255.0F / 255.0F, 141.0F / 255.0F, 227.0F / 255.0F, 1.0F);

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "HuTaoResources/img/char/Character_Button.png";
    // 人物选择界面的背景立绘
    private static final String MY_CHARACTER_PORTRAIT = "HuTaoResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "HuTaoResources/img/512/bg_attack.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "HuTaoResources/img/512/bg_power.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "HuTaoResources/img/512/bg_skill.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "HuTaoResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "HuTaoResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "HuTaoResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "HuTaoResources/img/1024/bg_skill.png";
    // 卡牌预览界面的能量图标
    private static final String BIG_ORB = "HuTaoResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENERGY_ORB = "HuTaoResources/img/char/cost_orb.png";

    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    String lang = "zhs";
    
    public HuTaoMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(HuTao.PlayerColorEnum.HUTAO_RED, HUTAO_RED, HUTAO_RED, HUTAO_RED, HUTAO_RED, HUTAO_RED, HUTAO_RED, HUTAO_RED, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENERGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
        updateLanguage();
    }
    
    // 注解需要调用的方法，必须写
    public static void initialize() {
        new HuTaoMod();
    }
    
    @Override
    public void receiveEditCards() {
        addIcons();
        BaseMod.addDynamicVariable(new SiVariable());
        new AutoAdd(MOD_NAME)
                .packageFilter("hutaomod.cards")
                .setDefaultSeen(true)
                .cards();
    }

    private static void addIcons() {
        CustomIconHelper.addCustomIcon(YinIcon.get());
        CustomIconHelper.addCustomIcon(YangIcon.get());
        CustomIconHelper.addCustomIcon(YinYangIcon.get());
        CustomIconHelper.addCustomIcon(SiIcon.get());
        CustomIconHelper.addCustomIcon(BloodBlossomIcon.get());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new HuTao(CardCrawlGame.playerName), HuTaoMod.MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, HuTao.PlayerColorEnum.HUTAO);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        updateLanguage();

        String json = Gdx.files.internal("HuTaoResources/localization/" + lang + "/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        if (json == null || json.isEmpty()) return;
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword(MOD_NAME.toLowerCase(), keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(MOD_NAME)
                .packageFilter("hsrmod.relics")
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelic(relic, RelicType.SHARED);
                });
    }

    @Override
    public void receiveEditStrings() {
        updateLanguage();
        // 这里添加注册本地化文本
        BaseMod.loadCustomStringsFile(CardStrings.class, "HuTaoResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "HuTaoResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "HuTaoResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "HuTaoResources/localization/" + lang + "/powers.json");
        // BaseMod.loadCustomStringsFile(EventStrings.class, "HuTaoResources/localization/" + lang + "/events.json");
        // BaseMod.loadCustomStringsFile(MonsterStrings.class, "HuTaoResources/localization/" + lang + "/monsters.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "HuTaoResources/localization/" + lang + "/ui.json");

    }

    @Override
    public void receivePostInitialize() {

    }

    @Override
    public void receiveAddAudio() {
    }


    @Override
    public void receiveStartGame() {

    }

    public void updateLanguage() {
        lang = "zhs";
    }

    public static String makeID(String name) {
        if (name.endsWith("Power") || name.endsWith("Relic") || name.endsWith("Event")) {
            name = name.substring(0, name.length() - 5);
        }
        return MOD_NAME + ":" + name;
    }
}
