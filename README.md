# Locker

Locker is paper plugin with additional api to allow developers an easier control
over player names and especially their possible disguises. Locker offers developers
the ability to easily change the whole appearance of a player to another player (group).
Thereby, the uuid, the name as well as the skin can be freely changed by modifying the
clientbound packets, which provides the most flexibility.

1. [Installation](#installation)

   1.1 [Adding to your server](#adding-to-your-server)

   1.2 [Adding to your plugin](#adding-to-your-plugin)
2. [Usage](#usage)

   2.1 [Explanation](#explanation)

   2.2 [Setup](#setup)

   2.3 [Update Look](#update-look)

   2.4 [Components](#components)

## Installation

### Adding to your server

Locker works as a standalone plugin and offers every plugin additionally an api.
Because of that, in order for Locker to work you must include the locker.jar
of the current release in your plugins directory.

### Adding to your plugin

If you want to add the locker `api` or the locker `components` to your plugin,
you must first add following repository to your build file:

#### Maven

```xml
<repository>
  <id>qetz-repository</id>
  <url>https://repo.qetz.de/artifactory/repo-public</url>
</repository>
```

#### Gradle

```groovy
maven {
  url 'https://repo.qetz.de/artifactory/repo-public'
  metadataSources {
    mavenPom()
    gradleMetadata()
    artifact()
  }
}
```

Afterwards you can add the `api` and if you wish the additional `components`:

#### Maven

```xml
<dependency>
  <groupId>qetz.locker</groupId>
  <artifactId>api</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>

<!-- Optionally components -->
<dependency>
  <groupId>qetz.locker</groupId>
  <artifactId>components</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### Gradle

```groovy
compileOnly 'qetz.locker:api:1.0.0'

// Optionally components
implementation 'qetz.locker:components:1.0.0'
```

## Usage

### Explanation

First, to make things a little clearer: Every player has a `Look` based upon
a set of outfits. An `Outfit` describes the actual name and skin other players
may see later on. However, the `Look` decides which player should see which `Outfit`.
For example, a staff member can have a Look based upon two Outfits: One `Outfit`
displaying the real staff member and one disguised `Outfit` for every normal player,
who shouldn't be able to see the actual staff member.

The `api` module provides the bare-minimum to build a plugin on Locker. However,
basic or repetitive components like a `SingleLook` (A `Look`, which only shows one `Outfit`)
already exists in the `components` module, so maybe you should take a look there
before you start coding your own version.

**Note:** The whole project depends on [Guice](https://github.com/google/guice).
So if you don't use or know that already, you should check out that before.

### Setup

You access `Locker` from Bukkit's ServiceManager:
```java
  var locker = Bukkit.getServicesManager().load(Locker.class);
```
Alternatively you can use the `LockerProvider` in the `components` module.

<br>

In order for Locker to work, you must register a `LookFactory`, which handles
the creation of a Look for every player. You can add a LookFactory via `Locker`:

```java
  var locker = injector.getInstance(Locker.class)
  var yourFactory = injector.getInstance(LookFactory.class);
  locker.registerFactory(yourFactory);
```
If you just want to use the original player's outfits, you can use the `OriginalLookFactory`
from the `components` module.

<br>

If you want to access the original Outfit of a player, you can use `Outfit#fromPlayer`.

### Update Look

If you want to update the Look of a player, you can also use the `Locker` object:

```java
  var locker = injector.getInstance(Locker.class);
  locker.updateLock(player.getUniqueId(), factory.createNewLook(player));
```

You can see a Look-update in the `examples` module in the context of
[nicks](https://github.com/Qetzing/locker/tree/main/examples/src/main/java/qetz/locker/example/nick).

### Components

Here you can see already existing components:

#### LockerProvider

A `Provider` for the `Locker` object. You can use it in your Guice module for example:
```java
  @Override
  protected void configure() {
    bind(Locker.class).toProvider(LockerProvider.create())
  }
```

#### NickedLook

This is a `Look` consisting of two Outfits, one original and one nicked. Furthermore,
you have a bypass permission to decide which player should see the original or the nicked
outfit:

```java
  private static final bypassPermission = "user.nick";

  var original = Outfit.fromPlayer(player);
  var nicked = factory.createNickedOutfit(player);
  var look = NickedLook.with(bypassPermission, original, nicked);
```

#### OriginalLookFactory

This `LookFactory` simply returns a [SingleLook](#single-look) with the player's original
outfit.

#### PermissionFilteredLook

The `PermissionFilteredLook` is a `Look` consisting of a Map with Outfits and their
according view-permission. This Look decides which Outfit a player should see based on
the receiver's permission. I.e., you pass a `LinkedHashMap<String, Outfit> outfits`
with a permission for each outfit.

On top of that, you pass an `UnknownPolicy`, which decides which Outfit should be selected,
if not other Outfit could be found. You can decide between three options:

 - Throw
 - Last
 - Explicit

Throw: When choosing `Throw`, an exception is thrown if no suitable Outfit could be found

Last: When choosing `Last`, the last Outfit in the `outfits` Map is used

Explicit: When choosing `Explicit`, you pass an additional, explicit `Outfit` as a
fallback outfit.

```java
  var look = PermissionFilteredLook.newBuilder()
    .addLook("vip", vipOutfit)
    .withUnknownPolicy(UnknownPolicy.Explicit)
    .withExplicit(defaultOutfit)
    .create();
```

#### Single Look

This is a simple `Look`, which only consists of one `Outfit`, which will be returned
everytime.