# Plugging

### Maven - with jitpack

With jitpack, you just need to put the repository and the dependency on your pom.


        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>

        <dependency>
            <groupId>com.github.gameszaum</groupId>
            <artifactId>gamesCore</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>

### Maven - without jitpack

Without jitpack you will need to download the library in [release page](https://github.com/gameszaum/gamesCore/releases) (could be a SNAPSHOT version), and put the 'games-core' library in your minecraft-server plugins.


        <dependency>
            <groupId>com.github.gameszaum</groupId>
            <artifactId>gamesCore</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>system</scope>
            <systemPath>local-of-your-jar.jar</systemPath>
        </dependency>
        
# Methods

### Commands
        Command.create(new CommandBase() {
            @Override
            public void handler(CommandSender commandSender, CommandHelper helper, String... args) {
                if (helper.isPlayer(commandSender)) {
                    Player player = helper.getPlayer(commandSender);

                    player.sendMessage("/test - player command.");
                } else {
                    commandSender.sendMessage("/test - console command.");
                }
            }
        }).runAsync().setCommand(GamesCore.getInstance(), "test");

        Command.create(new CommandBase() {
            @Override
            public void handler(CommandSender commandSender, CommandHelper helper, String... args) throws Exception {
                new Menu("Teste", 3, GamesCore.getInstance()).showMenu(helper.getPlayer(commandSender));
            }
        }).onlyPlayer().setCommand(GamesCore.getInstance(), "menu");
# Observation

This library runs in [spigot-1.8.8-R0.1-SNAPSHOT-latest.jar](https://getbukkit.org/get/hNiHm0tuqAg1Xg7w7zudk63uHr0xo48D). If some bugs appear, please contact me in my [discord server](https://discord.gg/uTCR3TK) and I will help you.
