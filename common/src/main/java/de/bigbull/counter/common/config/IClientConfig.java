package de.bigbull.counter.common.config;

public interface IClientConfig {
    boolean showDayOverlay();
    void setShowDayOverlay(boolean value);
    boolean showDayOverlayAlways();
    double dayOverlayX();
    void setDayOverlayX(double value);
    double dayOverlayY();
    void setDayOverlayY(double value);
    double dayOverlaySize();
    int dayOverlayTextColor();

    boolean showDeathListOverlay();
    void setShowDeathListOverlay(boolean value);
    boolean showDeathListOverlayAlways();
    double deathListOverlayX();
    void setDeathListOverlayX(double value);
    double deathListOverlayY();
    void setDeathListOverlayY(double value);
    double deathListOverlaySize();
    int deathListOverlayWidth();
    DeathListOverlayStyle deathListOverlayStyle();
    enum DeathListOverlayStyle {CLASSIC, BOXED, TABLE}
    int deathListTextColor();
    int firstPlaceColor();
    int secondPlaceColor();
    int thirdPlaceColor();

    boolean showDeathSelfOverlay();
    void setShowDeathSelfOverlay(boolean value);
    boolean showDeathSelfOverlayAlways();
    double deathSelfOverlayX();
    void setDeathSelfOverlayX(double value);
    double deathSelfOverlayY();
    void setDeathSelfOverlayY(double value);
    double deathSelfSize();
    int deathSelfTextColor();

    boolean showTimeOverlay();
    void setShowTimeOverlay(boolean value);
    boolean showTimeOverlayAlways();
    double timeOverlayX();
    void setTimeOverlayX(double value);
    double timeOverlayY();
    void setTimeOverlayY(double value);
    double timeOverlaySize();
    int timeOverlayTextColor();

    boolean showCoordsOverlay();
    void setShowCoordsOverlay(boolean value);
    boolean showCoordsOverlayAlways();
    double coordsOverlayX();
    void setCoordsOverlayX(double value);
    double coordsOverlayY();
    void setCoordsOverlayY(double value);
    double coordsOverlaySize();
    int coordsOverlayTextColor();


    boolean showPingAsText();
    int pingColorGood();
    int pingColorMedium();
    int pingColorBad();

    boolean showEmojis();

    void save();
}
