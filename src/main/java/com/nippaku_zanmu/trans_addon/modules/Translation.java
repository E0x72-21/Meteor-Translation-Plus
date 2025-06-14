package com.nippaku_zanmu.trans_addon.modules;


import com.nippaku_zanmu.trans_addon.MeteorTranslation;
import com.nippaku_zanmu.trans_addon.settings.StringSelectSetting;
import com.nippaku_zanmu.trans_addon.util.TransUtil;
import com.nippaku_zanmu.trans_addon.mixin.ModuleAccessor;
import com.nippaku_zanmu.trans_addon.mixin.SettingAccessor;
import com.nippaku_zanmu.trans_addon.mixin.SettingGroupAccessor;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class Translation extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    public final Setting<Boolean> bSetAutoTranslation = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-translation")
        .description("")
        .defaultValue(false)
        .build());
    public final Setting<String> sSetDumpPath = sgGeneral.add(new StringSetting.Builder()
        .name("DumpPath")
        .defaultValue("D:\\hack\\Misc\\meteor-translation-addon\\test\\en_us.json")
        .build());
    public final Setting<Boolean> bSetDumpFile = sgGeneral.add(new BoolSetting.Builder()
        .name("dump en_usJson")
        .defaultValue(false)
        .build());
    public final Setting<Set<String>> translationModules = sgGeneral.add(new StringSelectSetting.Builder().validValues(TransUtil.getAddonsName())
        .defaultValue(TransUtil.getAddonsName()).name("translation-modules").build());
    public static BufferedWriter dumpBW;

    public Translation() {
        super(MeteorTranslation.CATEGORY, "meteor-trans", "An example module that highlights the center of the world.");
    }

    private boolean isTranslation = false;

    @Override
    public void onActivate() {
        if (bSetAutoTranslation.get() && !isTranslation) {
            isTranslation = true;
            tran();
        }

        ChatUtils.warning("流星翻译插件是开源的项目且完全免费 作者不会以任何形式对此插件进行收费");
        ChatUtils.warning("如果你购买了此插件 则说明你被骗了");
    }

    //    @Override
//    public void onActivate() {
//        tran();
//    }
    @Override
    public WWidget getWidget(GuiTheme theme) {
        WVerticalList list = theme.verticalList();

        WHorizontalList b = list.add(theme.horizontalList()).expandX().widget();

        WButton start = b.add(theme.button("Translate")).expandX().widget();
        start.action = () -> {
            if (this.isActive()) {
                if (bSetDumpFile.get()) {
                    try {
                        File path = new File(sSetDumpPath.get());
                        if (!path.exists() & !(path.createNewFile())) {
                            ChatUtils.sendMsg(Text.of("DumpError Can't Create Dump File"));
                            return;
                        }
                        dumpBW = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(path, false), StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        ChatUtils.error(e.getMessage());
                        return;
                    }
                }
                isTranslation = true;
                tran();

                try {
                    if (dumpBW != null)
                        dumpBW.close();
                } catch (IOException e) {

                }

            } else {
                ChatUtils.warning("你首先要开启此模块");
            }
        };
        return list;
    }


    public void tran() {
        for (Module module : Modules.get().getList()) {
            String addonName = TransUtil.getAddonName(module);
            if (!translationModules.get().contains(addonName)) continue;
            //插件过滤

            String tranName = TransUtil.transModuleName(module);
            // 经过翻译的名称
            ((ModuleAccessor) module).setTitle(Utils.nameToTitle(tranName));
            //把标题设为翻译之后的名称

            String tranDescry = TransUtil.transModuleDescription(module);
            ((ModuleAccessor) module).setDescription(Utils.nameToTitle(tranDescry));
            //翻译简介

            for (SettingGroup group : module.settings.groups) {
                for (Setting<?> setting : ((SettingGroupAccessor) group).getSettings()) {
                    String tranSettName = TransUtil.transSettingName(module, group, setting);
                    ((SettingAccessor) setting).setTitle(Utils.nameToTitle(tranSettName));

                    String tranSettDesc = TransUtil.transSettingDes(module, group, setting);
                    ((SettingAccessor) setting).setDescription(Utils.nameToTitle(tranSettDesc));
                }
            }

        }
    }
}
