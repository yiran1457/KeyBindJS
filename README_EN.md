# KeyBindJS

[中文](README.md) | **English**

KeyBindJS is a KubeJS-based key binding extension mod for registering, modifying, and listening to Minecraft key bindings from KubeJS `client_scripts`.

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

Use the default category (`keybindjs:keybindjs`):

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
    // Hide a key binding
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

## License

This project is licensed under the [GPL-3.0-or-later](LICENSE.txt) license.
