# KeyBindJS

[English](README_EN.md) | **中文**

KeyBindJS 是一个基于 KubeJS 的按键绑定扩展模组，用于在 KubeJS `client_scripts` 中注册、修改和监听 Minecraft 按键。

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

使用默认分组（`keybindjs:keybindjs`）：

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
    // 隐藏按键
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

## 许可证

本项目使用 [GPL-3.0-or-later](LICENSE.txt) 协议。
