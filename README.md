## Meteor Translation Addon

> ℹ️ 本插件以[Nippaku-Zanmu/meteor-translation-addon](https://github.com/Nippaku-Zanmu/meteor-translation-addon)插件为框架制作

### 它能做什么？
- 为Meteor功能提供一定汉化
- 修复Meteor非英文字符渲染

### 它与Nippaku-Zanmu的汉化插件的区别是什么？
- 所有译文均由E0x72-21一人翻译，以便有更好的统一性（不使用原插件的译文）
- **它仅汉化注释/说明部分** ~~问就是我觉得中文的Meteor UI挺别扭的~~

### 为什么启用汉化后无法正常显示中文？
因为Meteor内嵌的默认Comfortaa字体没有非英文字形所以无法正常显示中文字符
\
解决方法：将Meteor的Custom font切换为任意包含中文字形的字体

### 其它事项
未完成所有注释/说明的汉化，目前进度: 24.4%

因为翻译进度目前还没有任何发行版，如果非要体验请自行去Actions里最后一次commit触发的Auto Build中的Artifacts下载

![Auto Build](/README%20images/Auto%20Build.png)

将来可能会为一些有渲染效果的功能加入更详细的Custom font选项