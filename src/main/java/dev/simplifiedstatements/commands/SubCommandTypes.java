package dev.simplifiedstatements.commands;

import dev.simplifiedstatements.commands.types.AddGold;
import dev.simplifiedstatements.commands.types.GetGold;
import dev.simplifiedstatements.commands.types.RemoveGold;
import dev.simplifiedstatements.commands.types.SetGold;

public enum SubCommandTypes {

    SET_GOLD("set", new SetGold()),
    ADD_GOLD("add", new AddGold()),
    REMOVE_GOLD("remove", new RemoveGold()),
    GET_GOLD("get", new GetGold());
    private final String name;
    private final SubCommand subCommand;
    public static final SubCommandTypes[] CACHE = values();

    SubCommandTypes(String name, SubCommand subCommand) {
        this.name = name;
        this.subCommand = subCommand;
    }

    public static SubCommandTypes fromName(String name) {
        for (SubCommandTypes types : CACHE) {
            if (types.getName().equalsIgnoreCase(name)) {
                return types;
            }
        }
        return null;
    }

    public <T extends SubCommand> T getSubCommand(Class<T> clazz) {
        if (!clazz.isInstance(this.subCommand)) {
            try {
                throw new Exception(name() + " is not instance of " + clazz.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clazz.cast(this.subCommand);
    }

    public String getName() {
        return name;
    }

    public SubCommand getSubCommand() {
        return subCommand;
    }
}

