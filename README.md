Below is an example of changing the default accessory menu key to ALT+X (moved under vanilla's Misc category) and removing the jump key binding:
```js
KeyBindEvents.modify(event => {
    event.modifyKey('key.curios.open.desc', GLFM.GLFM_KEY_X)
    event.modifyModifier('key.curios.open.desc', KeyModifier.ALT)
    event.modifyCategory('key.curios.open.desc', 'key.categories.misc')

    event.remove('key.jump')
})
```
Note: Do NOT install this mod on servers.

==============================================<br>
以下是将默认配件菜单键更改为 ALT+X（移至原版的 Misc 类别下）并删除跳转键绑定的示例：
```js
KeyBindEvents.modify(event => {
    event.modifyKey('key.curios.open.desc', GLFM.GLFM_KEY_X)
    event.modifyModifier('key.curios.open.desc', KeyModifier.ALT)
    event.modifyCategory('key.curios.open.desc', 'key.categories.misc')

    event.remove('key.jump')
})
```
注意：不要在服务器上安装此 mod。