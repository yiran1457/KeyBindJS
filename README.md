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


## 使用前的准备

在开始进行使用按键绑定前你需要注册按键绑定或者添加对已有按键绑定的监听
```js
//在startup
KeyBindEvents.register(event=>{
    //创建按键绑定 (第一个参数为标识，按键触发都会使用这个标识)
    event.create('create1','key.test.create.1',-1,'key.group.debug')

    event.create('create2','key.test.create.2',GLFW.GLFW_KEY_X,'key.group.debug')
    //添加修饰按键
    .addModifier(KeyModifier.ALT)
})
KeyBindEvents.modify(event=>{
    //添加对原版前进键的监听 (第一个参数为标识)
    //若不清楚有哪些按键可以使用 @key_name 查看
    event.addListener('forward',"key.forward")
})
```

## 开始使用

keybindjs也提供了部分方法来方便你来使用按键绑定
第一个参数为按键绑定的标识
这些方法都有InGui的分支,如`firstKeyPressInGui`
```js
//在client
//在按键刚按下时触发一次
KeyBindEvents.firstKeyPress('create1', e => {
    Client.tell('firstKeyPress')
})
//按键按下时持续触发
KeyBindEvents.keyPress('create2', e => {
    Client.tell('keyPress tick:')
})
//按键松开时触发一次
KeyBindEvents.keyRelease('forward', e => {
    Client.tell('keyRelease tick:')
})
```

## 额外的东西

修改或删除已有的按键绑定
```js
KeyBindEvents.modify(event=>{
    //修改默认按键
    event.modifyKey('key.curios.open.desc',GLFW.GLFW_KEY_X)
    //修改默认修饰符
    event.modifyModifier('key.curios.open.desc',KeyModifier.ALT)
    //修改按键分组，若不清楚有什么可以使用@category_name
    event.modifyCategory('key.curios.open.desc','key.categories.misc')

    //移除按键
    event.remove('key.saveToolbarActivator') //保存快捷栏
    event.remove('key.loadToolbarActivator') //加载快捷栏
    event.remove('key.jade.narrate') //语音复述
    event.remove('key.spectatorOutlines') //高亮玩家（旁观者）
    event.remove('key.socialInteractions') //社交屏幕
})
```

还提供了KeyBindUtil,里面有一些小功能
```js
//这两个方法用于获取按键绑定的名称和分类
//console.log去打印这两个会给出 攻击/摧毁 : key.attack 以便来更好的获取名称
KeyBindUtil.getAllKeyName()
KeyBindUtil.getAllKeyCategory()
//输入原版的key的langkey来获取他的keymapping
KeyBindUtil.findKeyMappingInAllKeyMapping("key.attack")
//输入标识用来获取自己注册的按键绑定
KeyBindUtil.getKeyMapping('create1')
//输入标识来判断按键是否按下,在gui中可能没有作用
KeyBindUtil.isDown('create1')
```