# JStatsAPI
A simple API for creating custom statistics in Minecraft.

### Features
- Create custom statistics
- Database and file storage
- Easily implementable into any plugin

## Usage
Using JStatsAPI was designed to be as simple as possible. There are only a few steps to creating a statistic.

### Creating a Provider
This is a required step as many functions in the API require a provider to be passed in.
The first argument is the plugin instance, and the second is whether other plugins can change the value of
the statistics provided by this provider. 9 times out of 10, you will want to set this to false.
```java
public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        JStatProvider provider = new JStatProvider(this, false);
    }
}
```
### Creating a Statistic
The first argument is the name of the statistic, the second is the provider, the third is the ID of the statistic,
and the fourth is the default value of the statistic. JStat accepts any class that extends `Number` (e.g. `Integer`, `Double`, `Float`, etc.)
```java
public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        JStatProvider provider = new JStatProvider(this);
        
        JStatsAPI.getInstance().registerStat(
                new JStat<>(
                        "Test Stat",
                        provider,
                        "test-stat",
                        0
                )
        );
    }
}
```

### Getting a Statistic
In order to get a statistic, you must first create a `StatRequest` object. This object takes two arguments: the provider, and the ID of the statistic.
This then gets passed into the JStatsAPI.makeRequest() method, which returns a `JStat` object. If the statistic does not exist, `null` will be returned.

In order to change the value, you must pass the UUID of the player you want to change the value for.

```java
import java.util.UUID;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        JStatProvider provider = new JStatProvider(this);

        JStat<Integer> testStat = new JStat<>(
                "Test Stat",
                provider,
                "test-stat",
                0
        );

        JStatsAPI.getInstance().registerStat(testStat);

        incrementTestStat(UUID.randomUUID());
    }

    public void incrementTestStat(UUID uuid) {
        JStat<Integer> stat = JStatsAPI.getInstance().makeRequest(
                StatRequest(
                        provider,
                        "test-stat"
                )
        );

        if (stat != null) {
            stat.addValueFor(uuid, 1);
        }
    }
}
```
