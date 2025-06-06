package hutaomod.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class ButterflySpawner {
    private Hitbox hitbox;
    private float spawnTimer;
    private float spawnIntervalMin = 0.3f;
    private float spawnIntervalMax = 0.6f;
    static Color[] colors = new Color[]{
            Color.YELLOW.cpy(),
            Color.GOLD.cpy(),
            Color.GOLDENROD.cpy(),
            Color.ORANGE.cpy(),
            Color.FIREBRICK.cpy(),
            Color.RED.cpy(),
            Color.SCARLET.cpy(),
            Color.CORAL.cpy(),
            Color.SALMON.cpy(),
            Color.PINK.cpy()
    };

    public ButterflySpawner(Hitbox hitbox) {
        this.hitbox = hitbox;
        this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
    }

    public void update() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        spawnTimer -= delta;
        if (spawnTimer <= 0f) {
            spawnButterfly();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }
    }

    private void spawnButterfly() {
        float x, y;
        if (MathUtils.randomBoolean()) {
            x = hitbox.x + (MathUtils.randomBoolean() ? MathUtils.random(hitbox.width * 0f, hitbox.width * 0.2f) : MathUtils.random(hitbox.width * 0.8f, hitbox.width));
            y = hitbox.y + MathUtils.random(hitbox.height);
        } else {
            x = hitbox.x + MathUtils.random(hitbox.width);
            y = hitbox.y + (MathUtils.randomBoolean() ? MathUtils.random(hitbox.height * 0f, hitbox.height * 0.2f) : MathUtils.random(hitbox.height * 0.8f, hitbox.height));
        }

        float duration = MathUtils.random(1.5f, 2f);
        float scale = MathUtils.random(0.4f, 0.8f);
        Color color = colors[MathUtils.random(colors.length - 1)];

        // Rotate outward: determine angle from center to point
        float angle = MathUtils.random(-45, 45);

        ButterflyEffect butterfly = new ButterflyEffect(x, y, duration, scale, color, angle);
        AbstractDungeon.topLevelEffects.add(butterfly);
    }
}
