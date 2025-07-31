package com.common.keybindjs.compat.probejs;

import net.minecraft.client.KeyMapping;
import zzzank.probejs.ProbeJS;
import zzzank.probejs.docs.assignments.SpecialTypes;
import zzzank.probejs.lang.snippet.SnippetDump;
import zzzank.probejs.lang.typescript.ScriptDump;
import zzzank.probejs.lang.typescript.code.member.TypeDecl;
import zzzank.probejs.lang.typescript.code.ts.Wrapped;
import zzzank.probejs.lang.typescript.code.type.BaseType;
import zzzank.probejs.lang.typescript.code.type.Types;
import zzzank.probejs.plugin.ProbeJSPlugin;

import java.util.stream.Collectors;

public class KeyBindProbePlugin implements ProbeJSPlugin {
    @Override
    public void assignType(ScriptDump scriptDump) {
        scriptDump.assignType(KeyMapping.class, Types.primitive("Special.KeyMapping"));
    }

    @Override
    public void addGlobals(ScriptDump scriptDump) {
        var special = new Wrapped.Namespace(SpecialTypes.NAMESPACE);
        var types = KeyMapping.ALL.keySet().stream().sorted().map(Types::literal).toArray(BaseType[]::new);
        var declaration = new TypeDecl("KeyMapping", Types.or(types));
        special.addCode(declaration);
        scriptDump.addGlobal("keybinding_types", special);
    }

    @Override
    public void addVSCodeSnippets(SnippetDump dump) {
        dump.snippet("keymapping")
                .prefix("@keymapping")
                .choices(
                        KeyMapping.ALL.keySet()
                                .stream()
                                .map(ProbeJS.GSON::toJson)
                                .sorted()
                                .toList()
                );
        dump.snippet("keyCategory")
                .prefix("@keyCategory")
                .choices(
                        KeyMapping.ALL.values()
                                .stream()
                                .map(KeyMapping::getCategory)
                                .collect(Collectors.toSet())
                                .stream()
                                .map(ProbeJS.GSON::toJson)
                                .sorted()
                                .toList()
                );
    }
}
