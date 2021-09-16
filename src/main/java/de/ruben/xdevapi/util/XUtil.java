package de.ruben.xdevapi.util;

import de.ruben.xdevapi.util.global.MathUtil;
import de.ruben.xdevapi.util.global.RandomUtil;
import de.ruben.xdevapi.util.global.TimeUtil;
import de.ruben.xdevapi.util.global.TypeUtil;

public class XUtil {

    private final BukkitInventoryUtil bukkitInventoryUtil;
    private final BukkitItemUtil bukkitItemUtil;
    private final BukkitPlayerUtil bukkitPlayerUtil;
    private final BukkitWorldUtil bukkitWorldUtil;
    private final VanillaUtil vanillaUtil;
    private final StringUtil stringUtil;

    private final Global global;

    public XUtil() {
        this.bukkitInventoryUtil = new BukkitInventoryUtil();
        this.bukkitItemUtil = new BukkitItemUtil();
        this.bukkitPlayerUtil = new BukkitPlayerUtil();
        this.bukkitWorldUtil = new BukkitWorldUtil();
        this.vanillaUtil = new VanillaUtil();
        this.stringUtil = new StringUtil();

        this.global = new Global();
    }

    public BukkitInventoryUtil getBukkitInventoryUtil() {
        return bukkitInventoryUtil;
    }

    public BukkitItemUtil getBukkitItemUtil() {
        return bukkitItemUtil;
    }

    public BukkitPlayerUtil getBukkitPlayerUtil() {
        return bukkitPlayerUtil;
    }

    public BukkitWorldUtil getBukkitWorldUtil() {
        return bukkitWorldUtil;
    }

    public VanillaUtil getVanillaUtil() {
        return vanillaUtil;
    }

    public Global getGlobal() {
        return global;
    }

    public StringUtil getStringUtil() {
        return stringUtil;
    }

    public class Global{
        private final MathUtil mathUtil;
        private final RandomUtil randomUtil;
        private final TimeUtil timeUtil;
        private final TypeUtil typeUtil;

        public Global(){
            this.mathUtil = new MathUtil();
            this.randomUtil = new RandomUtil();
            this.timeUtil = new TimeUtil();
            this. typeUtil = new TypeUtil();
        }

        public MathUtil getMathUtil() {
            return mathUtil;
        }

        public RandomUtil getRandomUtil() {
            return randomUtil;
        }

        public TimeUtil getTimeUtil() {
            return timeUtil;
        }

        public TypeUtil getTypeUtil() {
            return typeUtil;
        }
    }
}
