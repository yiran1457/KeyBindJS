# KeyBindJS

[中文](README.md) | **English**

KeyBindJS is a KubeJS-based key binding extension mod for registering, modifying, and listening to Minecraft key bindings from KubeJS `client_scripts`.

> This branch is ported from KeyBindJS 26.1.2 to **Minecraft 1.21.1 / NeoForge 21.1.233** (mod version `2101-1.1.0`).

## Requirements

| Dependency | Minimum version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.233 |
| KubeJS | 2101.7.2-build.368 |
| Rhino | 2101.2.7-build.85 |

## Features

- Register keyboard/mouse key bindings from scripts
- Modify existing key bindings: key, modifier, category
- Hide or remove unwanted key bindings
- Listen to in-game key press, release, and first press
- Listen to GUI key/mouse press, release, and first press
- Listen to `consumeClick` events
- Provide script bindings such as `KeyBindUtil`, `GLFW`, and `KeyModifier`

## Usage

> These events are only available in `client_scripts`, and all events are under the `KeyBindJSEvents` event group.

### 1. Register key bindings

```js
KeyBindJSEvents.register(event => {
    // create(name, key code, category)
    event.create("test", GLFW.GLFW_KEY_X, "kjs")

    // Mouse button
    event.createMouse("test_mouse", GLFW.GLFW_MOUSE_BUTTON_LEFT, "kjs")

    // Specify whether consumeClick is needed
    event.create("test2", GLFW.GLFW_KEY_H, "kjs", false)
})
```

Use the default category (`keybindjs:keybindjs`, shown as "KeyBindJS" in the controls screen):

```js
KeyBindJSEvents.register(event => {
    // create(name, key code)
    event.create("test_default", GLFW.GLFW_KEY_Z)

    // Mouse button
    event.createMouse("test_mouse_default", GLFW.GLFW_MOUSE_BUTTON_RIGHT)
})
```

Use the builder to set a modifier:

```js
KeyBindJSEvents.register(event => {
    event.create("test", GLFW.GLFW_KEY_X, "kjs")
        .addModifier(KeyModifier.SHIFT)
})
```

### 2. Modify existing key bindings

```js
KeyBindJSEvents.modify(event => {
    // Hide a key binding (uses only the default key and ignores the options file)
    event.addHideKey("key.sprint")

    // Remove a key binding
    event.remove("key.jump")

    // Change the default key
    event.modifyKey("key.sprint", GLFW.GLFW_KEY_H)

    // Change the modifier
    event.modifyModifier("key.sprint", KeyModifier.CONTROL)

    // Change the category
    event.modifyCategory("key.sprint", "kjs")

    // Add the key binding back to the controls screen
    event.join("key.sprint")
})
```

Method arguments accept either a `KeyMapping` object or a key name string (e.g. `"key.sprint"`); the conversion is handled automatically by the script bindings.

### 3. Listen to in-game key events

```js
KeyBindJSEvents.firstKeyPress("test", event => {
    Client.tell("First press: " + event.keyMapping.getName())
})

KeyBindJSEvents.keyPress("test", event => {
    Client.tell("Held / repeated: " + event.keyMapping.getName())
})

KeyBindJSEvents.keyRelease("test", event => {
    Client.tell("Released: " + event.keyMapping.getName())
})
```

### 4. Listen to GUI key/mouse events

```js
KeyBindJSEvents.firstKeyPressInGui("test", event => {
    Client.tell("First press in GUI: " + event.keyMapping.getName())
})

KeyBindJSEvents.keyPressInGui("test", event => {
    Client.tell("Pressed / repeated in GUI: " + event.keyMapping.getName())
})

KeyBindJSEvents.keyReleaseInGui("test", event => {
    Client.tell("Released in GUI: " + event.keyMapping.getName())
})
```

### 5. Listen to consumeClick

```js
KeyBindJSEvents.consumeClick("test", event => {
    Client.tell("consumeClick: " + event.keyMapping.getName())
})
```

## Script Bindings

| Name | Description |
| --- | --- |
| `KeyBindUtil` | Utility for getting all key mappings, names, categories, and states |
| `GLFW` | LWJGL GLFW constants for specifying keys |
| `KeyModifier` | Modifier enum: `NONE`, `SHIFT`, `CONTROL`, `ALT` |

## Differences from version 26.1.2

- Category arguments are plain strings (Minecraft 1.21.1 has no `KeyMapping.Category` type)
- `KeyBindUtil.getAllKeyCategory()` returns a list of category strings
- Default category is `keybindjs:keybindjs` (language key `keybindjs:keybindjs` → "KeyBindJS")

## License

This project is licensed under the GPL-3.0-or-later license.
