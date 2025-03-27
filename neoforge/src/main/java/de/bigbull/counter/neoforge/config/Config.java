package de.bigbull.counter.neoforge.config;

import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.common.config.IServerConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final Client CLIENT = new Client(CLIENT_BUILDER);
    public static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static final Server SERVER = new Server(SERVER_BUILDER);
    public static final ModConfigSpec SERVER_SPEC = SERVER_BUILDER.build();

    public static class Client implements IClientConfig {
        public static ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY_ALWAYS;
        public static ModConfigSpec.DoubleValue DAY_OVERLAY_X;
        public static ModConfigSpec.DoubleValue DAY_OVERLAY_Y;
        public static ModConfigSpec.DoubleValue DAY_OVERLAY_SIZE;
        public static ModConfigSpec.IntValue DAY_OVERLAY_TEXT_COLOR;

        public static ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY_ALWAYS;
        public static ModConfigSpec.DoubleValue DEATH_LIST_X;
        public static ModConfigSpec.DoubleValue DEATH_LIST_Y;
        public static ModConfigSpec.DoubleValue DEATH_LIST_SIZE;
        public static ModConfigSpec.IntValue DEATH_OVERLAY_WIDTH;
        public static ModConfigSpec.EnumValue<IClientConfig.DeathListOverlayStyle> DEATH_OVERLAY_STYLE;
        public static ModConfigSpec.IntValue DEATH_LIST_TEXT_COLOR;
        public static ModConfigSpec.IntValue FIRST_PLACE_COLOR;
        public static ModConfigSpec.IntValue SECOND_PLACE_COLOR;
        public static ModConfigSpec.IntValue THIRD_PLACE_COLOR;

        public static ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY_ALWAYS;
        public static ModConfigSpec.DoubleValue DEATH_SELF_X;
        public static ModConfigSpec.DoubleValue DEATH_SELF_Y;
        public static ModConfigSpec.DoubleValue DEATH_SELF_SIZE;
        public static ModConfigSpec.IntValue DEATH_SELF_TEXT_COLOR;

        public static ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY_ALWAYS;
        public static ModConfigSpec.DoubleValue TIME_OVERLAY_X;
        public static ModConfigSpec.DoubleValue TIME_OVERLAY_Y;
        public static ModConfigSpec.DoubleValue TIME_OVERLAY_SIZE;
        public static ModConfigSpec.IntValue TIME_OVERLAY_TEXT_COLOR;

        public static ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY_ALWAYS;
        public static ModConfigSpec.DoubleValue COORDS_OVERLAY_X;
        public static ModConfigSpec.DoubleValue COORDS_OVERLAY_Y;
        public static ModConfigSpec.DoubleValue COORDS_OVERLAY_SIZE;
        public static ModConfigSpec.IntValue COORDS_OVERLAY_TEXT_COLOR;

        public static ModConfigSpec.BooleanValue SHOW_PING_AS_TEXT;
        public static ModConfigSpec.IntValue PING_COLOR_GOOD;
        public static ModConfigSpec.IntValue PING_COLOR_MEDIUM;
        public static ModConfigSpec.IntValue PING_COLOR_BAD;

        public static ModConfigSpec.BooleanValue SHOW_EMOJIS;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Day Counter Overlay Settings");
            SHOW_DAY_OVERLAY = builder.comment("Enable/disable the day overlay on the client side.")
                    .define("showDayOverlay", true);
            SHOW_DAY_OVERLAY_ALWAYS = builder.comment("Should the day counter overlay always be visible?")
                    .define("showOverlayAlways", true);
            DAY_OVERLAY_X = builder.comment("Relative X position (0.0 = left, 1.0 = right).")
                    .defineInRange("dayOverlayX", 0.00625, 0.0, 1.0);
            DAY_OVERLAY_Y = builder.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                    .defineInRange("dayOverlayY", 0.015, 0.0, 1.0);
            DAY_OVERLAY_SIZE = builder.comment("Scale factor for the day counter text size.")
                    .defineInRange("dayOverlaySize", 1.0, 0.1, 5.0);
            DAY_OVERLAY_TEXT_COLOR = builder.comment("Color for the day overlay text.")
                    .defineInRange("dayOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Death Counter List Settings");
            SHOW_DEATH_LIST_OVERLAY = builder.comment("Enable/disable the death counter list overlay (shows all player deaths).")
                    .define("showDeathListOverlay", true);
            SHOW_DEATH_LIST_OVERLAY_ALWAYS = builder.comment("Should the death counter list overlay always be visible?")
                    .define("showListOverlayAlways", false);
            DEATH_LIST_X = builder.comment("Relative X position (0.0 = left, 1.0 = right) of the death list overlay.")
                    .defineInRange("deathListX", 0.0125, 0.0, 1.0);
            DEATH_LIST_Y = builder.comment("Relative Y position (0.0 = top, 1.0 = bottom) of the death list overlay.")
                    .defineInRange("deathListY", 0.16, 0.0, 1.0);
            DEATH_LIST_SIZE = builder.comment("Scale factor for the death list text size.")
                    .defineInRange("deathListSize", 1, 0.1, 5);
            DEATH_OVERLAY_STYLE = builder.comment("Which style to use for displaying the death list: CLASSIC, BOXED, or TABLE.")
                    .defineEnum("deathOverlayStyle", DeathListOverlayStyle.TABLE);
            DEATH_OVERLAY_WIDTH = builder.comment("Maximum width (in pixels) for the death counter list overlay.")
                    .defineInRange("deathOverlayWidth", 120, 0, 600);
            DEATH_LIST_TEXT_COLOR = builder.comment("Default text color for the death list overlay.")
                    .defineInRange("deathListTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            FIRST_PLACE_COLOR = builder.comment("Color for the first place in the death list.")
                    .defineInRange("firstPlaceColor", 0xFFD700, 0x000000, 0xFFFFFF);
            SECOND_PLACE_COLOR = builder.comment("Color for the second place in the death list.")
                    .defineInRange("secondPlaceColor", 0xC0C0C0, 0x000000, 0xFFFFFF);
            THIRD_PLACE_COLOR = builder.comment("Color for the third place in the death list.")
                    .defineInRange("thirdPlaceColor", 0xCD7F32, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Death Counter Self Settings");
            SHOW_DEATH_SELF_OVERLAY = builder.comment("Enable/disable the personal death counter overlay.")
                    .define("showDeathSelfOverlay", true);
            SHOW_DEATH_SELF_OVERLAY_ALWAYS = builder.comment("Should the personal death counter overlay always be visible?")
                    .define("showSelfOverlayAlways", false);
            DEATH_SELF_X = builder.comment("Relative X position for your personal death overlay.")
                    .defineInRange("deathSelfX", 0.00625, 0.0, 1.0);
            DEATH_SELF_Y = builder.comment("Relative Y position for your personal death overlay.")
                    .defineInRange("deathSelfY", 0.068, 0.0, 1.0);
            DEATH_SELF_SIZE = builder.comment("Scale factor for the personal death counter text size.")
                    .defineInRange("deathSelfSize", 1, 0.1, 5);
            DEATH_SELF_TEXT_COLOR = builder.comment("Color for your personal death counter text.")
                    .defineInRange("deathSelfTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Ingame Time Overlay Settings");
            SHOW_TIME_OVERLAY = builder.comment("Enable/disable the ingame time overlay.")
                    .define("showIngameTimeOverlay", false);
            SHOW_TIME_OVERLAY_ALWAYS = builder.comment("Should the ingame time overlay always be visible?")
                    .define("showIngameTimeOverlayAlways", true);
            TIME_OVERLAY_X = builder.comment("Relative X position (0.0 = left, 1.0 = right).")
                    .defineInRange("ingameTimeOverlayX", 0.00781, 0.0, 1.0);
            TIME_OVERLAY_Y = builder.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                    .defineInRange("ingameTimeOverlayY", 0.955, 0.0, 1.0);
            TIME_OVERLAY_SIZE = builder.comment("Scale factor for the ingame time overlay text size.")
                    .defineInRange("ingameTimeOverlaySize", 1.0, 0.1, 5.0);
            TIME_OVERLAY_TEXT_COLOR = builder.comment("Color for the ingame time overlay text.")
                    .defineInRange("ingameTimeOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Coordinates Overlay Settings");
            SHOW_COORDS_OVERLAY = builder.comment("Enable/disable the coordinates overlay.")
                    .define("showCoordsOverlay", false);
            SHOW_COORDS_OVERLAY_ALWAYS = builder.comment("Should the coordinates overlay always be visible?")
                    .define("showCoordsOverlayAlways", true);
            COORDS_OVERLAY_X = builder.comment("Relative X position (0.0 = left, 1.0 = right).")
                    .defineInRange("coordsOverlayX", 0.00781, 0.0, 1.0);
            COORDS_OVERLAY_Y = builder.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                    .defineInRange("coordsOverlayY", 0.905, 0.0, 1.0);
            COORDS_OVERLAY_SIZE = builder.comment("Scale factor for the coordinates overlay text size.")
                    .defineInRange("coordsOverlaySize", 1.0, 0.1, 5.0);
            COORDS_OVERLAY_TEXT_COLOR = builder.comment("Color for the coordinates overlay text.")
                    .defineInRange("coordsOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Ping Settings");
            SHOW_PING_AS_TEXT = builder.comment("Show the ping as text (e.g. '123ms') instead of the default bars in the tab list?")
                    .define("showPingAsText", true);
            PING_COLOR_GOOD = builder.comment("Color for low ping (<100ms).")
                    .defineInRange("pingColorGood", 0x00FF00, 0x000000, 0xFFFFFF);
            PING_COLOR_MEDIUM = builder.comment("Color for medium ping (100-249ms).")
                    .defineInRange("pingColorMedium", 0xFF9900, 0x000000, 0xFFFFFF);
            PING_COLOR_BAD = builder.comment("Color for high ping (>=250ms).")
                    .defineInRange("pingColorBad", 0xFF0000, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Emote Settings");
            SHOW_EMOJIS = builder.comment("Enable or disable emojis in overlays.")
                    .define("showEmojis", true);
            builder.pop();
        }

        @Override
        public boolean showDayOverlay() {
            return Client.SHOW_DAY_OVERLAY.get();
        }

        @Override
        public void setShowDayOverlay(boolean value) {
            Client.SHOW_DAY_OVERLAY.set(value);
        }

        @Override
        public boolean showDayOverlayAlways() {
            return Client.SHOW_DAY_OVERLAY_ALWAYS.get();
        }

        @Override
        public double dayOverlayX() {
            return Client.DAY_OVERLAY_X.get();
        }

        @Override
        public void setDayOverlayX(double value) {
            Client.DAY_OVERLAY_X.set(value);
        }

        @Override
        public double dayOverlayY() {
            return Client.DAY_OVERLAY_Y.get();
        }

        @Override
        public void setDayOverlayY(double value) {
            Client.DAY_OVERLAY_Y.set(value);
        }

        @Override
        public double dayOverlaySize() {
            return Client.DAY_OVERLAY_SIZE.get();
        }

        @Override
        public int dayOverlayTextColor() {
            return Client.DAY_OVERLAY_TEXT_COLOR.get();
        }

        @Override
        public boolean showDeathListOverlay() {
            return Client.SHOW_DEATH_LIST_OVERLAY.get();
        }

        @Override
        public void setShowDeathListOverlay(boolean value) {
            Client.SHOW_DEATH_LIST_OVERLAY.set(value);
        }

        @Override
        public boolean showDeathListOverlayAlways() {
            return Client.SHOW_DEATH_LIST_OVERLAY_ALWAYS.get();
        }

        @Override
        public double deathListOverlayX() {
            return Client.DEATH_LIST_X.get();
        }

        @Override
        public void setDeathListOverlayX(double value) {
            Client.DEATH_LIST_X.set(value);
        }

        @Override
        public double deathListOverlayY() {
            return Client.DEATH_LIST_Y.get();
        }

        @Override
        public void setDeathListOverlayY(double value) {
            Client.DEATH_LIST_Y.set(value);
        }

        @Override
        public double deathListOverlaySize() {
            return Client.DEATH_LIST_SIZE.get();
        }

        @Override
        public int deathListOverlayWidth() {
            return Client.DEATH_OVERLAY_WIDTH.get();
        }

        @Override
        public DeathListOverlayStyle deathListOverlayStyle() {
            return Client.DEATH_OVERLAY_STYLE.get();
        }

        @Override
        public int deathListTextColor() {
            return Client.DEATH_LIST_TEXT_COLOR.get();
        }

        @Override
        public int firstPlaceColor() {
            return Client.FIRST_PLACE_COLOR.get();
        }

        @Override
        public int secondPlaceColor() {
            return Client.SECOND_PLACE_COLOR.get();
        }

        @Override
        public int thirdPlaceColor() {
            return Client.THIRD_PLACE_COLOR.get();
        }

        @Override
        public boolean showDeathSelfOverlay() {
            return Client.SHOW_DEATH_SELF_OVERLAY.get();
        }

        @Override
        public void setShowDeathSelfOverlay(boolean value) {
            Client.SHOW_DEATH_SELF_OVERLAY.set(value);
        }

        @Override
        public boolean showDeathSelfOverlayAlways() {
            return Client.SHOW_DEATH_SELF_OVERLAY_ALWAYS.get();
        }

        @Override
        public double deathSelfOverlayX() {
            return Client.DEATH_SELF_X.get();
        }

        @Override
        public void setDeathSelfOverlayX(double value) {
            Client.DEATH_SELF_X.set(value);
        }

        @Override
        public double deathSelfOverlayY() {
            return Client.DEATH_SELF_Y.get();
        }

        @Override
        public void setDeathSelfOverlayY(double value) {
            Client.DEATH_SELF_Y.set(value);
        }

        @Override
        public double deathSelfSize() {
            return Client.DEATH_SELF_SIZE.get();
        }

        @Override
        public int deathSelfTextColor() {
            return Client.DEATH_SELF_TEXT_COLOR.get();
        }

        @Override
        public boolean showTimeOverlay() {
            return Client.SHOW_TIME_OVERLAY.get();
        }

        @Override
        public void setShowTimeOverlay(boolean value) {
            Client.SHOW_TIME_OVERLAY.set(value);
        }

        @Override
        public boolean showTimeOverlayAlways() {
            return Client.SHOW_TIME_OVERLAY_ALWAYS.get();
        }

        @Override
        public double timeOverlayX() {
            return Client.TIME_OVERLAY_X.get();
        }

        @Override
        public void setTimeOverlayX(double value) {
            Client.TIME_OVERLAY_X.set(value);
        }

        @Override
        public double timeOverlayY() {
            return Client.TIME_OVERLAY_Y.get();
        }

        @Override
        public void setTimeOverlayY(double value) {
            Client.TIME_OVERLAY_Y.set(value);
        }

        @Override
        public double timeOverlaySize() {
            return Client.TIME_OVERLAY_SIZE.get();
        }

        @Override
        public int timeOverlayTextColor() {
            return Client.TIME_OVERLAY_TEXT_COLOR.get();
        }

        @Override
        public boolean showCoordsOverlay() {
            return Client.SHOW_COORDS_OVERLAY.get();
        }

        @Override
        public void setShowCoordsOverlay(boolean value) {
            Client.SHOW_COORDS_OVERLAY.set(value);
        }

        @Override
        public boolean showCoordsOverlayAlways() {
            return Client.SHOW_COORDS_OVERLAY_ALWAYS.get();
        }

        @Override
        public double coordsOverlayX() {
            return Client.COORDS_OVERLAY_X.get();
        }

        @Override
        public void setCoordsOverlayX(double value) {
            Client.COORDS_OVERLAY_X.set(value);
        }

        @Override
        public double coordsOverlayY() {
            return Client.COORDS_OVERLAY_Y.get();
        }

        @Override
        public void setCoordsOverlayY(double value) {
            Client.COORDS_OVERLAY_Y.set(value);
        }

        @Override
        public double coordsOverlaySize() {
            return Client.COORDS_OVERLAY_SIZE.get();
        }

        @Override
        public int coordsOverlayTextColor() {
            return Client.COORDS_OVERLAY_TEXT_COLOR.get();
        }

        @Override
        public boolean showPingAsText() {
            return Client.SHOW_PING_AS_TEXT.get();
        }

        @Override
        public int pingColorGood() {
            return Client.PING_COLOR_GOOD.get();
        }

        @Override
        public int pingColorMedium() {
            return Client.PING_COLOR_MEDIUM.get();
        }

        @Override
        public int pingColorBad() {
            return Client.PING_COLOR_BAD.get();
        }

        @Override
        public boolean showEmojis() {
            return Client.SHOW_EMOJIS.get();
        }

        @Override
        public void save() {
            CLIENT_SPEC.save();
        }
    }

    public static class Server implements IServerConfig {
        public static ModConfigSpec.BooleanValue ENABLE_DAY_COUNTER;
        public static ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
        public static ModConfigSpec.BooleanValue ENABLE_DAY_MESSAGE;
        public static ModConfigSpec.BooleanValue SHOW_DAY_IN_CHAT;
        public static ModConfigSpec.IntValue DAY_CHATTEXT_COLOR;

        public static ModConfigSpec.BooleanValue ENABLE_DEATH_COUNTER;
        public static ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY;
        public static ModConfigSpec.IntValue MAX_PLAYERS_SHOWN;
        public static ModConfigSpec.EnumValue<IServerConfig.DeathOverlayMode> DEATH_OVERLAY_MODE;

        public static ModConfigSpec.BooleanValue ENABLE_DEATH_IN_CHAT;
        public static ModConfigSpec.EnumValue<IServerConfig.DeathInChatMode> DEATH_IN_CHAT_MODE;
        public static ModConfigSpec.EnumValue<IServerConfig.DeathChatMode> DEATH_CHAT_MODE;
        public static ModConfigSpec.BooleanValue SHOW_DEATH_LIST_ON_DEATH_GLOBAL;
        public static ModConfigSpec.IntValue DEATH_LIST_CHATTEXT_COLOR;
        public static ModConfigSpec.IntValue DEATH_SELF_CHATTEXT_COLOR;

        public static ModConfigSpec.BooleanValue ENABLE_TIME_Counter;
        public static ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
        public static ModConfigSpec.BooleanValue SHOW_COMBINED_DAY_TIME;
        public static ModConfigSpec.BooleanValue TIME_FORMAT_24H;

        public static ModConfigSpec.BooleanValue ENABLE_COORDS_COUNTER;
        public static ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY;

        public Server(ModConfigSpec.Builder builder) {
            builder.push("Day Counter Settings");
            ENABLE_DAY_COUNTER = builder.comment("If disabled, the day counter will not be tracked or displayed on the server.")
                    .define("enableDayCounter", true);
            ENABLE_DAY_MESSAGE = builder.comment("Send a chat message when a new Minecraft day starts?")
                    .define("enableDayMessage", true);
            SHOW_DAY_OVERLAY = builder.comment("Allow the day counter overlay to be displayed for players? (Server-side override)")
                    .define("showOverlay", true);
            SHOW_DAY_IN_CHAT = builder.comment("Show the current Minecraft day in chat when a player joins?")
                    .define("showDayInChat", true);
            DAY_CHATTEXT_COLOR = builder.comment("Text color for the day counter chat message.")
                    .defineInRange("dayChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Death Counter Settings");
            ENABLE_DEATH_COUNTER = builder.comment("If disabled, player deaths will not be tracked or displayed.")
                    .define("enableDeathCounter", true);
            SHOW_DEATH_OVERLAY = builder.comment("Allow players to see the death counter overlay? (Server-side override)")
                    .define("showOverlay", true);
            MAX_PLAYERS_SHOWN = builder.comment("Maximum number of players displayed in the death counter list.")
                    .defineInRange("maxPlayersShown", 5, 1, 20);
            DEATH_OVERLAY_MODE = builder.comment("Which death overlay types are allowed? ONLY_SELF = personal, LIST = global, BOTH = both.")
                    .defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
            builder.pop();

            builder.push("Death Counter Chat Settings");
            ENABLE_DEATH_IN_CHAT = builder.comment("Enable death counter messages in chat (on join or on death).")
                    .define("showDeathInChat", true);
            DEATH_IN_CHAT_MODE = builder.comment("Show the death counter in chat: ON_JOIN (when joining), ON_DEATH (when dying), or BOTH?")
                    .defineEnum("deathInChatMode", DeathInChatMode.ON_DEATH);
            DEATH_CHAT_MODE = builder.comment("Death counter chat mode: ONLY_SELF (personal), LIST (global ranking).")
                    .defineEnum("deathChatMode", DeathChatMode.ONLY_SELF);
            SHOW_DEATH_LIST_ON_DEATH_GLOBAL = builder.comment("Broadcast the death list to all players when someone dies?")
                    .define("showDeathListOnDeathGlobal", false);
            DEATH_LIST_CHATTEXT_COLOR = builder.comment("Text color for death counter messages in chat.")
                    .defineInRange("deathListChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            DEATH_SELF_CHATTEXT_COLOR = builder.comment("Text color for personal death messages in chat.")
                    .defineInRange("deathSelfChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
            builder.pop();

            builder.push("Time Counter Settings");
            ENABLE_TIME_Counter = builder.comment("If disabled, the time counter will not be tracked or displayed.")
                    .define("enableTimeCounter", true);
            SHOW_TIME_OVERLAY = builder.comment("Allow the time overlay to be displayed for players? (Server-side override)")
                    .define("showTimeOverlay", true);
            SHOW_COMBINED_DAY_TIME = builder.comment("Show the day count combined with the inGame time. Disables the standard Time Overlay.")
                    .define("showCombinedDayTime", false);
            TIME_FORMAT_24H = builder.comment("Use 24-hour format instead of 12-hour format.")
                    .define("timeFormat24h", true);
            builder.pop();

            SERVER_BUILDER.push("Coordinates Overlay Settings");
            ENABLE_COORDS_COUNTER = SERVER_BUILDER.comment("If disabled, the coordinates overlay will not be displayed.")
                    .define("enableCoordsCounter", true);
            SHOW_COORDS_OVERLAY = SERVER_BUILDER.comment("Allow the coordinates overlay to be displayed for players?")
                    .define("showCoordsOverlay", true);
            builder.pop();
        }

        @Override
        public boolean enabledDayCounter() {
            return Server.ENABLE_DAY_COUNTER.get();
        }

        @Override
        public boolean showDayOverlay() {
            return Server.SHOW_DAY_OVERLAY.get();
        }

        @Override
        public boolean enableDayMessage() {
            return Server.ENABLE_DAY_MESSAGE.get();
        }

        @Override
        public boolean showDayInChat() {
            return Server.SHOW_DAY_IN_CHAT.get();
        }

        @Override
        public int dayChatTextColor() {
            return Server.DAY_CHATTEXT_COLOR.get();
        }

        @Override
        public boolean enableDeathCounter() {
            return Server.ENABLE_DEATH_COUNTER.get();
        }

        @Override
        public boolean showDeathOverlay() {
            return Server.SHOW_DEATH_OVERLAY.get();
        }

        @Override
        public int maxPlayersShown() {
            return Server.MAX_PLAYERS_SHOWN.get();
        }

        @Override
        public DeathOverlayMode deathOverlayMode() {
            return Server.DEATH_OVERLAY_MODE.get();
        }

        @Override
        public boolean enableDeathInChat() {
            return Server.ENABLE_DEATH_IN_CHAT.get();
        }

        @Override
        public DeathInChatMode deathInChatMode() {
            return Server.DEATH_IN_CHAT_MODE.get();
        }

        @Override
        public DeathChatMode deathChatMode() {
            return Server.DEATH_CHAT_MODE.get();
        }

        @Override
        public boolean showDeathListOnDeathGlobal() {
            return Server.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get();
        }

        @Override
        public int deathListChatTextColor() {
            return Server.DEATH_LIST_CHATTEXT_COLOR.get();
        }

        @Override
        public int deathSelfChatTextColor() {
            return Server.DEATH_SELF_CHATTEXT_COLOR.get();
        }

        @Override
        public boolean enableTimeCounter() {
            return Server.ENABLE_TIME_Counter.get();
        }

        @Override
        public boolean showTimeOverlay() {
            return Server.SHOW_TIME_OVERLAY.get();
        }

        @Override
        public void setShowTimeOverlay(boolean value) {
            Server.SHOW_TIME_OVERLAY.set(value);
        }

        @Override
        public boolean showCombinedDayTime() {
            return Server.SHOW_COMBINED_DAY_TIME.get();
        }

        @Override
        public boolean timeFormat24h() {
            return Server.TIME_FORMAT_24H.get();
        }

        @Override
        public boolean enableCoordsCounter() {
            return Server.ENABLE_COORDS_COUNTER.get();
        }

        @Override
        public boolean showCoordsOverlay() {
            return Server.SHOW_COORDS_OVERLAY.get();
        }
    }
}
