# KeyBindJS

[English](README_EN.md) | **中文**

KeyBindJS 是一个基于 KubeJS 的按键绑定扩展模组，用于在 KubeJS `client_scripts` 中注册、修改和监听 Minecraft 按键。

> 本分支为从 KeyBindJS 26.1.2 移植到 **Minecraft 1.21.1 / NeoForge 21.1.233** 的版本（mod 版本 `2101-1.1.0`）。

## 环境要求

| 依赖 | 最低版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.233 |
| KubeJS | 2101.7.2-build.368 |
| Rhino | 2101.2.7-build.85 |

## 功能

- 在脚本中注册键盘/鼠标按键绑定
- 修改已有按键绑定的按键、修饰键、分组
- 隐藏或移除不需要的按键绑定
- 监听游戏内按键按下、释放、首次按下
- 监听 GUI 内按键/鼠标按下、释放、首次按下
- 监听 `consumeClick` 消耗点击事件
- 提供 `KeyBindUtil`、`GLFW`、`KeyModifier` 等脚本绑定

## 使用

> 本模组事件仅在 `client_scripts` 中可用，所有事件都位于 `KeyBindJSEvents` 事件组中。

### 1. 注册按键

```js
KeyBindJSEvents.register(event => {
    // create(名称, 键位, 分组)
    event.create("test", GLFW.GLFW_KEY_X, "kjs")

    // 鼠标按键
    event.createMouse("test_mouse", GLFW.GLFW_MOUSE_BUTTON_LEFT, "kjs")

    // 指定是否需要 consumeClick
    event.create("test2", GLFW.GLFW_KEY_H, "kjs", false)
})
```

使用默认分组（`keybindjs:keybindjs`，在控制选项中显示为 "KeyBindJS"）：

```js
KeyBindJSEvents.register(event => {
    // create(名称, 键位)
    event.create("test_default", GLFW.GLFW_KEY_Z)

    // 鼠标按键
    event.createMouse("test_mouse_default", GLFW.GLFW_MOUSE_BUTTON_RIGHT)
})
```

使用构建器设置修饰键：

```js
KeyBindJSEvents.register(event => {
    event.create("test", GLFW.GLFW_KEY_X, "kjs")
        .addModifier(KeyModifier.SHIFT)
})
```

### 2. 修改已有按键

```js
KeyBindJSEvents.modify(event => {
    // 隐藏按键（隐藏后将只使用默认按键而不读取 options 文件）
    event.addHideKey("key.sprint")

    // 移除按键
    event.remove("key.jump")

    // 修改默认键位
    event.modifyKey("key.sprint", GLFW.GLFW_KEY_H)

    // 修改修饰键
    event.modifyModifier("key.sprint", KeyModifier.CONTROL)

    // 修改分组
    event.modifyCategory("key.sprint", "kjs")

    // 将按键加入配置屏幕
    event.join("key.sprint")
})
```

方法参数既可以直接传 `KeyMapping` 对象，也可以传按键名称字符串（如 `"key.sprint"`），脚本会自动转换。

### 3. 监听游戏内按键事件

```js
KeyBindJSEvents.firstKeyPress("test", event => {
    Client.tell("首次按下：" + event.keyMapping.getName())
})

KeyBindJSEvents.keyPress("test", event => {
    Client.tell("按住/重复：" + event.keyMapping.getName())
})

KeyBindJSEvents.keyRelease("test", event => {
    Client.tell("释放：" + event.keyMapping.getName())
})
```

### 4. 监听 GUI 内按键/鼠标事件

```js
KeyBindJSEvents.firstKeyPressInGui("test", event => {
    Client.tell("GUI 内首次按下：" + event.keyMapping.getName())
})

KeyBindJSEvents.keyPressInGui("test", event => {
    Client.tell("GUI 内按下/重复：" + event.keyMapping.getName())
})

KeyBindJSEvents.keyReleaseInGui("test", event => {
    Client.tell("GUI 内释放：" + event.keyMapping.getName())
})
```

### 5. 监听 consumeClick

```js
KeyBindJSEvents.consumeClick("test", event => {
    Client.tell("consumeClick：" + event.keyMapping.getName())
})
```

## 脚本绑定

| 名称 | 说明 |
| --- | --- |
| `KeyBindUtil` | 按键工具类，可获取全部按键、按键名、分类、按键状态等 |
| `GLFW` | LWJGL GLFW 常量，用于指定键位 |
| `KeyModifier` | 修饰键枚举：`NONE`、`SHIFT`、`CONTROL`、`ALT` |

## 与 26.1.2 版本的差异

- 分组参数为普通字符串（Minecraft 1.21.1 没有 `KeyMapping.Category` 类型）
- `KeyBindUtil.getAllKeyCategory()` 返回分组字符串列表
- 默认分组为 `keybindjs:keybindjs`（语言键 `keybindjs:keybindjs` → "KeyBindJS"）

## 许可证

本项目使用 GPL-3.0-or-later 协议。
