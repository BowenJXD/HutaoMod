package hutaomod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShadowEffect extends AbstractGameEffect {
    private final Sprite shadowSprite;
    private final float speed, sX, sY;

    public ShadowEffect(Texture texture, float x, float y, float speed, float duration, Color color) {
        this.shadowSprite = new Sprite(texture);
        this.shadowSprite.setOriginCenter();
        this.shadowSprite.setPosition(x, y);
        this.shadowSprite.setRotation(rotation);
        this.shadowSprite.setScale(scale * Settings.scale);
        this.sX = x;
        this.sY = y;
        this.speed = speed;

        this.color = color.cpy(); // black shadow with adjustable alpha
        this.startingDuration = this.duration = duration;
        this.renderBehind = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        shadowSprite.setColor(color); // Ensure sprite uses the shadow color
        shadowSprite.setPosition(
                sX + Interpolation.pow3Out.apply(startingDuration, 0, duration) * speed * Settings.scale,
                sY + Interpolation.linear.apply(startingDuration, 0, duration) * speed * Settings.scale
        );
        shadowSprite.draw(sb);
    }

    @Override
    public void dispose() {
        // No resource cleanup necessary
    }
}
